package com.ideau.controlepatrimonio_api.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.ideau.controlepatrimonio_api.model.Usuario.Usuario;
//875f365905a634422e6e8001b2105bd1ceb252ff451f7788682e5894fd7617e6

import lombok.Value;

@Service
public class TokensService {
    //@Value("$(api.security.token.secret)")
    private String secret;
    
    private Instant tempoExpToken () {
        return LocalDateTime.now().plusHours(tempoDuracaoTokenHrs).toInstant(ZoneOffset.of("-03:00"));
    }
    
    private int tempoDuracaoTokenHrs = 2;

    public String geraToken(Usuario objUsuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                .withIssuer("controlepatrimonio_api")
                .withSubject(objUsuario.getIdUsuario())                                
                .withExpiresAt(tempoExpToken())
                .sign(algorithm);
            return token;
        } catch (JWTCreationException eMessage) {
            throw new RuntimeException("Erro durante a criação do token.", eMessage);
        }
    }
    public String validaToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            token = JWT.require(algorithm)
                .withIssuer("controlepatrimonio_api")
                .build()
                .verify(token)
                .getSubject();
                return token;
        } catch (Exception e) {
            return "Erro ao validar token!";
        }
    }
}
