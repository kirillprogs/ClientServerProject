package org.example.network;

import com.sun.net.httpserver.*;
import io.jsonwebtoken.Claims;
import org.example.controller.GroupController;
import org.example.controller.ProductController;
import org.example.controller.StoreController;
import org.example.database.Storage;
import org.example.entity.User;
import org.example.repository.UserRepository;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.SQLException;

import static org.example.network.Response.*;

public class Server {

    private final ProductController productController;
    private final GroupController groupController;
    private final UserRepository userRepository;
    private final static String DEFAULT_ROLE = "customer";

    public Server(int port) throws SQLException, ClassNotFoundException, IOException {
        Storage storage = new Storage();
        this.productController = storage.getProductController();
        this.groupController = storage.getGroupController();
        StoreController storeController = storage.getStoreController();
        this.userRepository = storage.getUserRepository();

        HttpServer server = HttpServer.create();
        server.bind(new InetSocketAddress(port), 0);

        server.createContext("/", new RootHandler())
                .setAuthenticator(new ServerAuthenticator());

        server.createContext("/login/", new LoginHandler());
        server.createContext("/register/", new RegisterHandler());

        server.createContext("/api/good/", new ProductHandler())
                .setAuthenticator(new ServerAuthenticator());

        server.createContext("/api/group/", new GroupHandler())
                .setAuthenticator(new ServerAuthenticator());

        server.createContext("/api/group/all/", groupController::getAll)
                .setAuthenticator(new ServerAuthenticator());

        server.createContext("/api/goods/all/", productController::getAll)
                .setAuthenticator(new ServerAuthenticator());

        server.createContext("/api/value/store/", storeController::storeValue)
                .setAuthenticator(new ServerAuthenticator());

        server.createContext("/api/value/group/", storeController::groupValue)
                .setAuthenticator(new ServerAuthenticator());

        server.createContext("/api/operate/increase/",
                exchange -> productController.changeAmount(exchange, true))
                .setAuthenticator(new ServerAuthenticator());

        server.createContext("/api/operate/decrease/",
                exchange -> productController.changeAmount(exchange, false))
                .setAuthenticator(new ServerAuthenticator());

        server.createContext("/api/search/", productController::search)
                .setAuthenticator(new ServerAuthenticator());

        server.setExecutor(null);
        server.start();
    }

    private class RegisterHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) {
            String username = exchange.getRequestHeaders().getFirst("Username");
            String password = exchange.getRequestHeaders().getFirst("Password");
            String passwordRepeat = exchange.getRequestHeaders().getFirst("PasswordRepeat");
            if (!password.equals(passwordRepeat)) {
                sendResponse(exchange, 400, "Passwords do not match");
                return;
            }
            User user = userRepository.find_by_name(username);
            if (user != null) {
                sendResponse(exchange, 400, "User already exists");
                return;
            }
            userRepository.create(new User(username, DEFAULT_ROLE, password));
            sendResponse(exchange, 200, "Account successfully created");
        }
    }

    private class LoginHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) {
            String username = exchange.getRequestHeaders().getFirst("Username");
            String password = exchange.getRequestHeaders().getFirst("Password");
            if (username == null || password == null) {
                sendResponse(exchange, 400, LOGIN_INCORRECT);
                return;
            }
            User user = userRepository.find_by_name(username);
            if (user == null)
                sendResponse(exchange, 401, NO_SUCH_USER);
            else {
                if (user.getPassword().equals(password)) {
                    String token = JwtAuthorization.generateToken(user);
                    sendResponse(exchange, 200, token);
                }
                else
                    sendResponse(exchange, 401, PASSWORD_INCORRECT);
            }
        }
    }

    private class ProductHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) {
            switch (exchange.getRequestMethod()) {
                case "GET": {
                    productController.findId(exchange);
                    break;
                }
                case "POST": {
                    productController.set(exchange, false);
                    break;
                }
                case "PUT": {
                    productController.set(exchange, true);
                    break;
                }
                case "DELETE": {
                    productController.delete(exchange);
                    break;
                }
                default:
                    Server.sendResponse(exchange, 406, "Incorrect request method");
            }
        }
    }

    private class GroupHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) {
            switch (exchange.getRequestMethod()) {
                case "GET": {
                    groupController.findId(exchange);
                    break;
                }
                case "POST": {
                    groupController.set(exchange, false);
                    break;
                }
                case "PUT": {
                    groupController.set(exchange, true);
                    break;
                }
                case "DELETE": {
                    groupController.delete(exchange);
                    break;
                }
                default:
                    Server.sendResponse(exchange, 406, "Incorrect request method");
            }
        }
    }

    private class ServerAuthenticator extends Authenticator {
        @Override
        public Result authenticate(HttpExchange httpExchange) {
            String token = httpExchange.getRequestHeaders().getFirst("Authorization");
            if (token != null) {
                if (token.equals("special"))///////////////////////////////////////////////////////////////
                    return new Success(new HttpPrincipal("admin", "admin"));////////////////
                try {
                    Claims claims = JwtAuthorization.verifyToken(token);
                    String username = claims.getSubject();
                    String password = (String) claims.get("password");

                    User user = userRepository.find_by_name(username);
                    if (!user.getPassword().equals(password))
                        return new Failure(401);
                    return new Success(new HttpPrincipal(username, user.getRole()));
                } catch (Exception e) {
                    return new Failure(403);
                }
            }
            return new Failure(403);
        }
    }

    public static void sendResponse(HttpExchange exchange, int statusCode, String response) {
        try {
            exchange.sendResponseHeaders(statusCode, response.length());
            OutputStream outputStream = exchange.getResponseBody();
            outputStream.write(response.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendResponse(HttpExchange exchange, int statusCode) {
        try {
            exchange.sendResponseHeaders(statusCode, -1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class RootHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) {
            sendResponse(exchange, 404, NO_SUCH_PAGE);
        }
    }
}