package com.ideau.controlepatrimonio_api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.ideau.controlepatrimonio_api.infra.security.JWTService;
import com.ideau.controlepatrimonio_api.model.Usuario.UsuarioAutenticado;
import com.ideau.controlepatrimonio_api.model.Usuario.dto.AutenticacaoDTO;

@RestController
public class AuthController {
        private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
        private final AuthenticationManager authenticationManager;

        final JWTService jwtService;

        AuthController(AuthenticationManager authenticationManager, JWTService jwtService) {
            this.authenticationManager = authenticationManager;
            this.jwtService = jwtService;
    }
        @PostMapping("/login/")
        public ResponseEntity<String> login(@RequestBody @Valid AutenticacaoDTO objAuthDTO) {
            logger.info("usuario: {}", objAuthDTO.username());
            logger.info("senha: {}", objAuthDTO.password());

            var usernamePassword = new UsernamePasswordAuthenticationToken(objAuthDTO.username(), objAuthDTO.password());
            logger.info("autenticando: {}", usernamePassword);
            var autenticacao = this.authenticationManager.authenticate(usernamePassword);
            var token = jwtService.geraToken((UsuarioAutenticado) autenticacao.getPrincipal());
            return ResponseEntity.ok(token);
        }
        
}
