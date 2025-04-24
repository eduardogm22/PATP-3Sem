package com.ideau.controlepatrimonio_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.ideau.controlepatrimonio_api.infra.security.JWTService;
import com.ideau.controlepatrimonio_api.model.Usuario.Usuario;
import com.ideau.controlepatrimonio_api.model.Usuario.UsuarioAutenticado;
import com.ideau.controlepatrimonio_api.model.Usuario.dto.AutenticacaoDTO;

@RestController
public class AuthController {
    
        private final AuthenticationManager authenticationManager;

        final JWTService jwtService;

        AuthController(AuthenticationManager authenticationManager, JWTService jwtService) {
            this.authenticationManager = authenticationManager;
            this.jwtService = jwtService;
    }
        @PostMapping("/login/")
        public ResponseEntity<String> login(@RequestBody @Valid AutenticacaoDTO objAuthDTO) {
            System.out.println("usuario " + objAuthDTO.username());
            System.out.println("senha: " + objAuthDTO.password());

            var usernamePassword = new UsernamePasswordAuthenticationToken(objAuthDTO.username(), objAuthDTO.password());
            System.out.println("autenticando: " + usernamePassword);
            var autenticacao = this.authenticationManager.authenticate(usernamePassword);
            var token = jwtService.geraToken((UsuarioAutenticado) autenticacao.getPrincipal());
            return ResponseEntity.ok(token);
        }
        
}
