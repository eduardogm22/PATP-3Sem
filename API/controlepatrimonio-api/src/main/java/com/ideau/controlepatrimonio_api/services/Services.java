package com.ideau.controlepatrimonio_api.services;

import org.springframework.stereotype.Service;

import com.ideau.controlepatrimonio_api.dto.UsuarioAlteraveisDTO;
import com.ideau.controlepatrimonio_api.model.Usuario.Usuario;
import com.ideau.controlepatrimonio_api.repositories.UserRepository;
import com.ideau.controlepatrimonio_api.utils.Utils;


@Service
public class Services {
    Utils utils = new Utils();
    
    public Usuario trataPutUsuarios(UsuarioAlteraveisDTO objUsuarioDTO, UserRepository userRepository, String idUsuario/*, payload payload*/) {
        if (objUsuarioDTO == null) return null;
        Usuario usuarioNoBd = userRepository.findByIdUsuario(idUsuario);

        if (utils.isNullOrEmpty(objUsuarioDTO.usuario())) usuarioNoBd.setUsername(objUsuarioDTO.usuario());      
        if (utils.isNullOrEmpty(objUsuarioDTO.senha())) usuarioNoBd.setSenha(objUsuarioDTO.senha());      
        if (utils.isNullOrEmpty(objUsuarioDTO.nomeCompleto())) usuarioNoBd.setNomeCompleto(objUsuarioDTO.nomeCompleto());      
        if (utils.isNullOrEmpty(objUsuarioDTO.email())) usuarioNoBd.setEmail(objUsuarioDTO.email());      
        // if (!utils.isNullOrEmpty(objUsuarioDTO.getIdCargo()) && ((payload.idCargo == 1 /*ADMIN*/) {
        //     usuarioNoBd.setIdCargo(objUsuarioDTO.getIdCargo());))
        // }
        return usuarioNoBd;
    }
}