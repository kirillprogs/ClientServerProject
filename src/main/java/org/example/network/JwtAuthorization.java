package org.example.network;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.example.entity.User;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

public class JwtAuthorization {

    private static String secretKey;

    public static String generateToken(User user) {
        String keys = UUID.randomUUID().toString();
        System.out.println(keys);
        secretKey = keys;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(keys);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());

        long now = System.currentTimeMillis();
        int expiration_time = 86400000;
        return Jwts.builder()
                .setSubject(user.getName())
                .claim("password", user.getPassword())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now+expiration_time))
                .signWith(SignatureAlgorithm.HS256, signingKey)
                .compact();
    }

    public static Claims verifyToken(String token) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .parseClaimsJws(token).getBody();
    }
}