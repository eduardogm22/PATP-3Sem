package com.ideau.controlepatrimonio_api.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ideau.controlepatrimonio_api.repositories.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FiltroDeSeguranca extends OncePerRequestFilter {

    @Autowired
    private TokensService tokensService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var token = this.dadosToken(request);
        if (token != null) {
            var tokenUsername = tokensService.validaToken(token);
            UserDetails usuario = userRepository.findByUsername(tokenUsername);
            var autenticacao = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            
        }
        filterChain.doFilter(request, response);
    }
            
    private String dadosToken(HttpServletRequest request) {
        var authHeader = request.getHeader("authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
