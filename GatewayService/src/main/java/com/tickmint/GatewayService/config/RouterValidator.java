package com.tickmint.GatewayService.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
public class RouterValidator {

    // Lista de endpoints que no requieren autenticación
    public static final List<String> openApiEndpoints = List.of(
            "/auth/register",
            "/auth/login",
            "/Actuator/health",
            "/api/events/allevents",
            "/swagger-ui/**",
            "/v3/api-docs/**");

// Predicado para verificar si una ruta está protegida
public Predicate<ServerHttpRequest> isSecured =
        request -> openApiEndpoints.stream()
                // Verifica si la ruta de la solicitud no coincide con ninguna de las rutas abiertas
                .noneMatch(uri -> request.getURI()
                        .getPath().matches(uri.replace("**", ".*")));
}