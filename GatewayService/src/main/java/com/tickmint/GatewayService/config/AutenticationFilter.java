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

    //Inyecta el servicio de JWT
    @Autowired
    private JwtUtils jwtUtils;

    //Método que se ejecuta en cada petición
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain){
        //Obtiene la solicitud HTTP
        ServerHttpRequest request = exchange.getRequest();

        //Verifica si la ruta esta protegida
        if (validator.isSecured.test(request)){
            if(authMissing(request)){
                return onError(exchange);
                //Si no hay token, retorna un error
            }
            final String token = request.getHeaders().getOrEmpty("Authorization").get(0).substring(7);

            //Si el token ha expirado, retorna un error
            if (jwtUtils.isTokenExpired(token)){
                return onError(exchange);
            }
        }
        //Continua con el siguiente filtro en la cadena
        return chain.filter(exchange);
    }

    private boolean authMissing(ServerHttpRequest request) {
        //Verifica si la solicitud no contiene el encabezado de autorización
        return !request.getHeaders().containsKey("Authorization");
    }

    private Mono<Void> onError(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        //Setea el código de estado de la respuesta
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        //Genera una respuesta completa
        DataBuffer buffer = response.bufferFactory().wrap("Unauthorized".getBytes());
        return response.writeWith(Mono.just(buffer));
    }

}
