package com.tickmint.GatewayService.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtUtils {

    //Variable para almacenar la secret key
    private String secret;

    //Clave secreta estatica para firmar y verificar el token
    private static SecretKey SECRET_KEY;

    //Metodo que se ejecuta despues de la creacion del bean
    @PostConstruct
    public void init() {
        //Obtenemos la secret key del entorno
        String envSecret = System.getenv("jwt.secret");
        //Si la secret key del entorno no es nula y no esta vacia
        if (envSecret != null && !envSecret.isEmpty()) {
            //Asignamos la secret key del entorno a la variable secret
            secret = envSecret;
        }
        //Si la secret key es nula o esta vacia
        if (secret == null || secret.isEmpty()) {
            //Lanzamos una excepcion
            throw new IllegalStateException("jwt.secret is missing in both environment variables and application.properties");
        }
        //Asignamos la secret key a la variable SECRET_KEY
        SECRET_KEY = Keys.hmacShaKeyFor(secret.getBytes());
    }

    //Metodo para obtener todas las claims del token
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    //Metodo para verificar si el token ha expirado
    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}
