package com.ideau.controlepatrimonio_api.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.ideau.controlepatrimonio_api.model.Usuario.Usuario;

import io.github.cdimascio.dotenv.Dotenv;



@Service
public class TokensService {

    private final Dotenv dotenv = Dotenv.load();
    private final String secret = dotenv.get("SECRET_KEY");
    
    private Instant tempoExpToken () {
        return LocalDateTime.now().plusHours(tempoDuracaoTokenHrs).toInstant(ZoneOffset.of("-03:00"));
    }
    
    private int tempoDuracaoTokenHrs = 2;

    public String geraToken(Usuario objUsuario) {
        try {
            return JWT.create()
                .withIssuer("controlepatrimonio_api")
                .withSubject(objUsuario.getIdUsuario())                                
                .withExpiresAt(tempoExpToken())
                .sign(Algorithm.HMAC256(secret));
            
        } catch (JWTCreationException eMessage) {
            throw new RuntimeException("Erro durante a criação do token.", eMessage);
        }
    }
    public String validaToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("controlepatrimonio_api")
                .build()
                .verify(token)
                .getSubject();

        } catch (Exception e) {
            return "Erro ao validar token!";
        }
    }
}
