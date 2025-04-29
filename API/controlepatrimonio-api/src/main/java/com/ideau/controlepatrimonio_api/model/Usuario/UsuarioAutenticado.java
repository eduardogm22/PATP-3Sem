package com.ideau.controlepatrimonio_api.model.Usuario;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UsuarioAutenticado implements UserDetails {
    Logger logger = LoggerFactory.getLogger(UsuarioAutenticado.class);
    private final Usuario usuario;
    private final String nomeCargo;

    public UsuarioAutenticado(Usuario usuario, String nomeCargo) {
        this.usuario = usuario;
        this.nomeCargo = nomeCargo;
    }

       @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        logger.info("getAuthorities: {}", nomeCargo);

        return switch (nomeCargo) {
            case "DEV" -> List.of (                
                    new SimpleGrantedAuthority("ROLE_DEV"),
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_REGISTRADOR"),
                    new SimpleGrantedAuthority("ROLE_VISUALIZADOR")                
                );

            case "ADMIN" -> List.of (
                new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("ROLE_REGISTRADOR"),
                new SimpleGrantedAuthority("ROLE_VISUALIZADOR")             
                );
            case "REGISTRADOR" -> List.of (
                    new SimpleGrantedAuthority("ROLE_REGISTRADOR"),
                    new SimpleGrantedAuthority("ROLE_VISUALIZADOR")
                    );
            default -> List.of(new SimpleGrantedAuthority("ROLE_VISUALIZADOR"));            
        };
    }
    @Override
    public String getPassword() {
        return usuario.getSenha();
    }
    @Override
    public String getUsername() {
        return usuario.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; 
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
