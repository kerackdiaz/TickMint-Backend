package com.tickmint.GatewayService.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    //Inyecta el filtro de autenticación
    @Autowired
    private AutenticationFilter filter;

    //Define la configuracion de las rutas
    @Bean
    public RouteLocator customRouterLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Configura una ruta para el servicio de autenticación
                .route("AuthService", r -> r.path("/auth/**")
                        // Aplica el filtro de autenticación a esta ruta
                        .filters(f -> f.filter(filter))
                        .uri("lb://task-AuthService"))
                .build();
    }

}
