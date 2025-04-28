package com.ideau.controlepatrimonio_api.model.Usuario.dto;

public record UsuarioAlteraveisDTO(
    String username,
    String senha,
    String nomeCompleto,
    String email,
    Integer idCargo,
    Integer ativo
) {}
