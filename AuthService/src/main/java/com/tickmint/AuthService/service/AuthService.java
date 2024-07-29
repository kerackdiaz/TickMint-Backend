package com.tickmint.AuthService.service;

import com.tickmint.AuthService.dtos.SignInDTO;
import com.tickmint.AuthService.dtos.SignUpDTO;
import com.tickmint.AuthService.dtos.UserDTO;
import com.tickmint.AuthService.models.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    private final RestTemplate restTemplate;

    private final JwtUtil jwtUtil;


    @Autowired
    public AuthService(RestTemplate restTemplate, JwtUtil jwtUtil) {
        this.restTemplate = restTemplate;
        this.jwtUtil = jwtUtil;

    }

    // Método para registrar un nuevo usuario
    public ResponseEntity<?> register(SignUpDTO request) {
        try {
            // Hash de la contraseña del usuario utilizando BCrypt
            request.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
            // Enviar una solicitud POST al servicio de usuario para registrar al usuario
            ResponseEntity<?> response = restTemplate.postForEntity("http://localhost:8081/api/v1/users/register", request, String.class);
            // Devolver un mensaje de éxito si el registro se realiza correctamente
            return ResponseEntity.ok("User successfully registered");
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Devuelve el mensaje de error si hay un error del cliente o del servidor
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (RestClientException e) {
            // Devolver un mensaje de error genérico para otras excepciones
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    // Método para iniciar la sesión de un usuario
    public AuthResponse login(SignInDTO request) throws Exception {
        ResponseEntity<UserDTO> response;
        if (request.getEmail() != null) {
            response = restTemplate.postForEntity("http://localhost:8081/api/v1/users/loginByEmail", request, UserDTO.class);
        } else if (request.getPhone() != null) {
            response = restTemplate.postForEntity("http://localhost:8081/api/v1/users/loginByPhone", request, UserDTO.class);
        } else {
            throw new Exception("Invalid login request");
        }
        UserDTO user = response.getBody();
        String accessToken;
        if (user != null) {
            accessToken = jwtUtil.generateToken(String.valueOf(user.getId()), user.getRole(), user.getEmail());
        } else {
            throw new Exception("Failed to login user");
        }
        return new AuthResponse(accessToken);
    }
}