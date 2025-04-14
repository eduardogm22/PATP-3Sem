package com.ideau.controlepatrimonio_api.dto;

public record UsuarioAlteraveisDTO(
    String usuario,
    String senha,
    String nomeCompleto,
    String email,
    Integer idCargo
) {}
