package com.ideau.controlepatrimonio_api.model.Usuario;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "idUsuario")
@Getter
@Setter
@Entity(name = "Usuarios")
@Table(name  = "Usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idUsuario;
    private String username;
    private String senha;
    private String nomeCompleto;
    private String email;
    private Integer idCargo;    
    private LocalDateTime dataCriacao;
    private Integer ativo;
}