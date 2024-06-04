package com.K1dou.Loja.virtual.config;

import com.K1dou.Loja.virtual.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;


    public String generateToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withSubject(usuario.getLogin())
                    .withIssuer("auth-api")
                    .withExpiresAt(expire())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return "validateToken NULL";
        }
    }


    private Instant expire() {
        return LocalDateTime.now().plusHours(8).toInstant(ZoneOffset.of("-03:00"));
    }


    /*Fazendo liberação contra erro de COrs no navegador*/
//    private void liberacaoCors(HttpServletResponse response) {
//
//        if (response.getHeader("Access-Control-Allow-Origin") == null) {
//            response.addHeader("Access-Control-Allow-Origin", "*");
//        }
//
//
//        if (response.getHeader("Access-Control-Allow-Headers") == null) {
//            response.addHeader("Access-Control-Allow-Headers", "*");
//        }
//
//
//        if (response.getHeader("Access-Control-Request-Headers") == null) {
//            response.addHeader("Access-Control-Request-Headers", "*");
//        }
//
//        if (response.getHeader("Access-Control-Allow-Methods") == null) {
//            response.addHeader("Access-Control-Allow-Methods", "*");
//        }
//
//    }


}
