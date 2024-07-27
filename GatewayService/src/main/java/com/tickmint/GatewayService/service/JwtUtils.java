package com.tickmint.GatewayService.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtUtils {

    // Variable para almacenar la clave secreta
    private String secret;

    // Clave secreta estática para firmar y verificar JWT
    private static SecretKey SECRET_KEY;

    // Método que se ejecuta después de la construcción del bean
    @PostConstruct
    public void init() {
        // Obtiene la clave secreta de las variables de entorno
        String envSecret = System.getenv("jwt.secret");
        if (envSecret != null && !envSecret.isEmpty()) {
            secret = envSecret;
        }

        // Si la clave secreta no está presente en las variables de entorno, lanza una excepción
        if (secret == null || secret.isEmpty()) {
            throw new IllegalStateException("jwt.secret is missing in both environment variables and application.properties");
        }

        // Genera la clave secreta a partir de la cadena de texto
        SECRET_KEY = Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Método para extraer todos los claims de un token JWT
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Método para verificar si un token JWT ha expirado
    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}