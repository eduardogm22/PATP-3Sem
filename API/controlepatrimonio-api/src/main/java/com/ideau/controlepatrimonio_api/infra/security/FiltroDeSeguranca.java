package com.ideau.controlepatrimonio_api.infra.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;

import com.ideau.controlepatrimonio_api.services.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FiltroDeSeguranca extends OncePerRequestFilter {

    private final JWTService jwtService;

    private final UserDetailsServiceImpl userDetailsService;

    FiltroDeSeguranca(
        JWTService jwtService, 
        UserDetailsServiceImpl userDetailsService) {
        this.jwtService = jwtService;    
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response, 
        @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        var token = this.dadosToken(request);
        if (token != null) {
            var tokenUsername = jwtService.validaToken(token);
            UserDetails usuario = userDetailsService.loadUserByUsername(tokenUsername);
            var autenticacao = new UsernamePasswordAuthenticationToken(
                usuario, null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(autenticacao);
        }
        filterChain.doFilter(request, response);
    }
            
    private String dadosToken(HttpServletRequest request) {
        var authHeader = request.getHeader("authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
