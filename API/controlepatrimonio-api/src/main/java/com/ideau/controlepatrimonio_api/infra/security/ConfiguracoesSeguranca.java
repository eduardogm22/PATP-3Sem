package com.ideau.controlepatrimonio_api.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ConfiguracoesSeguranca {

    // private static final String ROTA_USUARIOS = "/users/**";

    // final FiltroDeSeguranca filtroDeSeguranca;

    // ConfiguracoesSeguranca(FiltroDeSeguranca filtroDeSeguranca) {
    //     this.filtroDeSeguranca = filtroDeSeguranca;
    // }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Permite tudo temporariamente
            )
            // .addFilterBefore(filtroDeSeguranca, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
        AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

//aqui está o código original, com autenticacao e tudo mais. acima foi o chat que criou
//sem autenticacao
// package com.ideau.controlepatrimonio_api.infra.security;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpMethod;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// @Configuration
// @EnableWebSecurity
// public class ConfiguracoesSeguranca {

//     private static final String ROTA_USUARIOS = "/users/**";

//     final FiltroDeSeguranca filtroDeSeguranca;

//     ConfiguracoesSeguranca(FiltroDeSeguranca filtroDeSeguranca) {
//         this.filtroDeSeguranca = filtroDeSeguranca;
//     }
    
//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//     return httpSecurity
//         .csrf(csrf -> csrf.disable())
//         .authorizeHttpRequests(auth -> auth
//         .requestMatchers("/", "/login/").permitAll()
//         .requestMatchers(HttpMethod.POST, ROTA_USUARIOS).permitAll()//hasRole("ADMIN")
//         .requestMatchers(HttpMethod.GET, ROTA_USUARIOS).permitAll()//.hasRole("ADMIN")                       
//         .requestMatchers(HttpMethod.PUT, ROTA_USUARIOS).permitAll()//.hasRole("VISUALIZADOR")
//         .requestMatchers(HttpMethod.DELETE, ROTA_USUARIOS).permitAll()//.hasRole("ADMIN")                       
//         //.anyRequest().permitAll()
//         )
//         .addFilterBefore(filtroDeSeguranca, UsernamePasswordAuthenticationFilter.class)
//         .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//         .build();
//     }

//     @Bean
//     public AuthenticationManager authenticationManager(
//         AuthenticationConfiguration authenticationConfiguration) throws Exception {
//         return authenticationConfiguration.getAuthenticationManager();
//     }
    
//     @Bean
//     public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}
// }

