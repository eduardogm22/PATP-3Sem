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

@RestController
public class AuthController {
    
        @Autowired
        private AuthenticationManager authenticationManager;

        @PostMapping("/login/")
        public ResponseEntity login(@RequestBody @Valid AutenticacaoDTO objAuthDTO) {
            var usernamePassword = new UsernamePasswordAuthenticationToken(objAuthDTO.username(), objAuthDTO.password());
            var autenticacao = this.authenticationManager.authenticate(usernamePassword);

            return ResponseEntity.ok().build();
        }
        
}
