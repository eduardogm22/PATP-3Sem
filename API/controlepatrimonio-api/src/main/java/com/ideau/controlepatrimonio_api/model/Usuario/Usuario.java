package com.ideau.controlepatrimonio_api.model.Usuario;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Usuarios")
@Table(name  = "Usuarios")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idUsuario")
@Getter
@Setter
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idUsuario;
    private String username;
    private String senha;
    private String nomeCompleto;
    private String email;
    private UsuarioCargo cargo;
    private LocalDateTime dataCriacao;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.cargo == UsuarioCargo.DEV) {
            return List.of(
                new SimpleGrantedAuthority("ROLE_DEV"),
                new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("ROLE_REGISTRADOR"),
                new SimpleGrantedAuthority("ROLE_VISUALIZADOR")                
                );
        } else if (this.cargo == UsuarioCargo.ADMIN) {
            return List.of(
                new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("ROLE_REGISTRADOR"),
                new SimpleGrantedAuthority("ROLE_VISUALIZADOR")             
                );
        } else if (this.cargo == UsuarioCargo.REGISTRADOR) {
            return List.of(
                new SimpleGrantedAuthority("ROLE_REGISTRADOR"),
                new SimpleGrantedAuthority("ROLE_VISUALIZADOR")
                );
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_VISUALIZADOR"));
        }
    }
    @Override
    public String getPassword() {
        throw new UnsupportedOperationException("Unimplemented method 'getPassword'");
    }
}
