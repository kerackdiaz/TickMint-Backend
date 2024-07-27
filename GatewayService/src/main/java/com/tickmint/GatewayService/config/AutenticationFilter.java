package com.tickmint.GatewayService.config;

import com.tickmint.GatewayService.service.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RefreshScope
@Component
public class AutenticationFilter implements GatewayFilter {

//Inyecta el validador de rutas y el servicio de JWT
    @Autowired
    private RouterValidator validator;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Obtiene la solicitud HTTP del intercambio
        ServerHttpRequest request = exchange.getRequest();

        // Verifica si la ruta está protegida
        if (validator.isSecured.test(request)) {
            // Si falta el token de autenticación, retorna un error
            if (authMissing(request)) {
                return onError(exchange);
            }
            // Obtiene el token de la cabecera Authorization
            final String token = request.getHeaders().getOrEmpty("Authorization").get(0).substring(7);;


            // Verifica si el token ha expirado
            if (jwtUtils.isTokenExpired(token)) {
                return onError(exchange);
            }
        }
        // Continúa con el siguiente filtro en la cadena
        return chain.filter(exchange);
    }

// Maneja el error cuando falta el token de autenticación o es inválido
private Mono<Void> onError(ServerWebExchange exchange) {
    ServerHttpResponse response = exchange.getResponse();
    // Configura el código de estado HTTP a 401 Unauthorized
    response.setStatusCode(HttpStatus.UNAUTHORIZED);
    // Se genera el mensaje de error y se envía en la respuesta
    DataBuffer buffer = response.bufferFactory().wrap("Unauthorized".getBytes());
    return response.writeWith(Mono.just(buffer));
}

    // Verifica si la cabecera Authorization está presente
    private boolean authMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }
}