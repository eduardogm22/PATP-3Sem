package com.ideau.controlepatrimonio_api.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ideau.controlepatrimonio_api.model.Usuario.Usuario;
import com.ideau.controlepatrimonio_api.model.Usuario.dto.UsuarioAlteraveisDTO;
import com.ideau.controlepatrimonio_api.model.Usuario.dto.UsuarioPublicoDTO;
import com.ideau.controlepatrimonio_api.repositories.CargoRepository;
import com.ideau.controlepatrimonio_api.repositories.UserRepository;
import com.ideau.controlepatrimonio_api.utils.Utils;

@Service
public class UserServices {

    private final CargoRepository cargoRepository;
    Utils utils = new Utils();

    UserServices( CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }
    
    public UsuarioPublicoDTO CadastraUsuario(Usuario objUsuario, UserRepository userRepository) {
        String senhaCriptografada = new BCryptPasswordEncoder().encode(objUsuario.getSenha());
        objUsuario.setSenha(senhaCriptografada);
        Usuario novoUsuario = userRepository.save(objUsuario);
        return new UsuarioPublicoDTO(
            novoUsuario.getUsername(),
            novoUsuario.getNomeCompleto(),
            novoUsuario.getEmail(),
            cargoRepository.findNomeById(novoUsuario.getIdCargo()),
            novoUsuario.getDataCriacao()
        );        
    }

    public Usuario AtualizaUsuario(UsuarioAlteraveisDTO objUsuarioDTO, UserRepository userRepository, String idUsuario/*, payload payload*/) {
        if (objUsuarioDTO == null) return null;
        Usuario usuarioNoBd = userRepository.findByIdUsuario(idUsuario);

        if (utils.isNullOrEmpty(objUsuarioDTO.username())) usuarioNoBd.setUsername(objUsuarioDTO.username());      
        if (utils.isNullOrEmpty(objUsuarioDTO.senha())) usuarioNoBd.setSenha(objUsuarioDTO.senha());      
        if (utils.isNullOrEmpty(objUsuarioDTO.nomeCompleto())) usuarioNoBd.setNomeCompleto(objUsuarioDTO.nomeCompleto());      
        if (utils.isNullOrEmpty(objUsuarioDTO.email())) usuarioNoBd.setEmail(objUsuarioDTO.email());      
        // if (!utils.isNullOrEmpty(objUsuarioDTO.getIdCargo()) {
        //     usuarioNoBd.setIdCargo(objUsuarioDTO.getIdCargo());))
        // }
        return usuarioNoBd;
    }
}