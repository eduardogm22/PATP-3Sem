package com.ideau.controlepatrimonio_api.dto;

import java.time.LocalDateTime;

import com.ideau.controlepatrimonio_api.model.Usuario.UsuarioCargo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
public class UsuarioPublicoDTO {
    private String username;
    private String nomeCompleto;
    private String email;
    private UsuarioCargo cargo;
    private LocalDateTime dataCriacao;
}
