package org.example.network;

import com.sun.net.httpserver.*;
import io.jsonwebtoken.Claims;
import org.example.controller.GroupController;
import org.example.controller.ProductController;
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

    public Server(int port) throws SQLException, ClassNotFoundException, IOException {
        Storage storage = new Storage();
        this.productController = storage.getProductController();
        this.groupController = storage.getGroupController();
        this.userRepository = storage.getUserRepository();

        HttpServer server = HttpServer.create();
        server.bind(new InetSocketAddress(port), 0);

        server.createContext("/", new RootHandler())
                .setAuthenticator(new ServerAuthenticator());

        server.createContext("/login/", new LoginHandler());

        server.createContext("/api/good/", new ProductHandler())
                .setAuthenticator(new ServerAuthenticator());

        server.createContext("/api/group/", new GroupHandler())
                .setAuthenticator(new ServerAuthenticator());

        server.setExecutor(null);
        server.start();
    }

    private static class RootHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            StringBuilder builder = new StringBuilder();
            builder.append("<h1>URI: ").append(exchange.getRequestURI()).append("</h1>");
            Headers headers = exchange.getRequestHeaders();
            for (String header : headers.keySet()) {
                builder.append("<p>").append(header).append("=")
                        .append(headers.getFirst(header)).append("</p>");
            }
            byte[] bytes = builder.toString().getBytes();
            exchange.sendResponseHeaders(200, bytes.length);

            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        }
    }

    private class LoginHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) {
            String username = exchange.getRequestHeaders().getFirst("Username");
            String password = exchange.getRequestHeaders().getFirst("Password");
            System.out.println(username+" : "+password);
            if (username == null || password == null)
                sendResponse(exchange, 400, LOGIN_INCORRECT);
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
        public void handle(HttpExchange exchange) throws IOException {
            switch (exchange.getRequestMethod()) {
                case "GET": {
                    productController.findId(exchange);
                    break;
                }
                case "POST": {
                    productController.create(exchange);
                    break;
                }
                case "PUT": {
                    productController.update(exchange);
                    break;
                }
                case "DELETE": {
                    productController.delete(exchange);
                    break;
                }
                default:
                    System.out.println(exchange.getRequestMethod());
            }
            exchange.getResponseHeaders().add("content-type", "application/json");
        }
    }

    private class GroupHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            switch (exchange.getRequestMethod()) {
                case "GET": {
                    groupController.findId(exchange);
                    break;
                }
                case "POST": {
                    groupController.create(exchange);
                    break;
                }
                case "PUT": {
                    groupController.update(exchange);
                    break;
                }
                case "DELETE": {
                    groupController.delete(exchange);
                    break;
                }
                default:
                    System.out.println(exchange.getRequestMethod());
            }
            exchange.getResponseHeaders().add("content-type", "application/json");
        }
    }

    private class ServerAuthenticator extends Authenticator {
        @Override
        public Result authenticate(HttpExchange httpExchange) {
            String token = httpExchange.getRequestHeaders().getFirst("Authorization");
            if (token != null) {
                Claims claims = JwtAuthorization.verifyToken(token);
                String username = claims.getSubject();
                String password = (String) claims.get("password");
                System.out.println("In token: "+username+" : "+password);

                User user = userRepository.find_by_name(username);
                if (!user.getPassword().equals(password))
                    return new Failure(401);
                return new Success(new HttpPrincipal(username, user.getRole()));
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
}