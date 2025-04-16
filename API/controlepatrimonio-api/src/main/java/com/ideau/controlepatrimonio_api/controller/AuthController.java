package com.ideau.controlepatrimonio_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import com.ideau.controlepatrimonio_api.dto.AutenticacaoDTO;
import com.ideau.controlepatrimonio_api.infra.security.TokensService;
import com.ideau.controlepatrimonio_api.model.Usuario.Usuario;

@RestController
public class AuthController {
    
        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired 
        TokensService tokensService;
        @PostMapping("/login/")
        public ResponseEntity<String> login(@RequestBody @Valid AutenticacaoDTO objAuthDTO) {
            var usernamePassword = new UsernamePasswordAuthenticationToken(objAuthDTO.username(), objAuthDTO.password());
            var autenticacao = this.authenticationManager.authenticate(usernamePassword);
            var token = tokensService.geraToken((Usuario) autenticacao.getPrincipal());
            return ResponseEntity.ok(token);
        }
        
}
