package com.ideau.controlepatrimonio_api.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ideau.controlepatrimonio_api.infra.exceptions.HTTPException;
import com.ideau.controlepatrimonio_api.model.Usuario.Usuario;
import com.ideau.controlepatrimonio_api.model.Usuario.dto.UsuarioAlteraveisDTO;
import com.ideau.controlepatrimonio_api.model.Usuario.dto.UsuarioPublicoDTO;
import com.ideau.controlepatrimonio_api.repositories.CargoRepository;
import com.ideau.controlepatrimonio_api.repositories.UserRepository;
import com.ideau.controlepatrimonio_api.utils.Utils;

@Service
public class UserServices {

    private final UserRepository userRepository;
    private final CargoRepository cargoRepository;

    UserServices(UserRepository userRepository, CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
        this.userRepository = userRepository;
    }
    
    public UsuarioPublicoDTO postUserService(Usuario objUsuario, UserRepository userRepository) {
        try {
            if (userRepository.findByUsername(objUsuario.getUsername()).isPresent()) {
                throw new HTTPException(HttpStatus.CONFLICT, "Nome de usuário já em uso!");
            }
            String senhaCriptografada = new BCryptPasswordEncoder().encode(objUsuario.getSenha());
            objUsuario.setSenha(senhaCriptografada);
            Usuario novoUsuario = userRepository.save(objUsuario);
            return new UsuarioPublicoDTO(
                novoUsuario.getUsername(),
                novoUsuario.getNomeCompleto(),
                novoUsuario.getEmail(),
                cargoRepository.findNomeById(novoUsuario.getIdCargo()),
                novoUsuario.getDataCriacao(),
                Utils.formataAtivo(novoUsuario.getAtivo())
            );        
        } catch (Exception e) {
            throw new HTTPException(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                "Erro ao cadastrar novo usuário: " + e.getMessage());
        }
    }

    public List<UsuarioPublicoDTO> getAllUsersService(String retornaInativo) {
        
    }

    public UsuarioPublicoDTO putUserService(UsuarioAlteraveisDTO objUsuarioDTO, UserRepository userRepository, String idUsuario) {
        try {
            Usuario usuarioNoBd = userRepository.findByIdUsuario(idUsuario);
            if (usuarioNoBd == null) {
                throw new HTTPException(HttpStatus.NOT_FOUND, "Usuário não encontrado!");
            }
            if (!Utils.isNullOrEmpty(objUsuarioDTO.username())) usuarioNoBd.setUsername(objUsuarioDTO.username());      
            if (!Utils.isNullOrEmpty(objUsuarioDTO.senha())) usuarioNoBd.setSenha(objUsuarioDTO.senha());      
            if (!Utils.isNullOrEmpty(objUsuarioDTO.nomeCompleto())) usuarioNoBd.setNomeCompleto(objUsuarioDTO.nomeCompleto());      
            if (!Utils.isNullOrEmpty(objUsuarioDTO.email())) usuarioNoBd.setEmail(objUsuarioDTO.email());
            if (!Utils.isNullOrEmpty(objUsuarioDTO.ativo())) usuarioNoBd.setAtivo(objUsuarioDTO.ativo());     
            userRepository.save(usuarioNoBd);
            return new UsuarioPublicoDTO(
                usuarioNoBd.getUsername(),
                usuarioNoBd.getNomeCompleto(),
                usuarioNoBd.getEmail(),
                cargoRepository.findNomeById(usuarioNoBd.getIdCargo()),
                usuarioNoBd.getDataCriacao(),
                Utils.formataAtivo(usuarioNoBd.getAtivo())
            );
        } catch (Exception e) {
            throw new HTTPException(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                "Erro ao atualizar dados do usuário: " + e.getMessage());
        }
    }
}