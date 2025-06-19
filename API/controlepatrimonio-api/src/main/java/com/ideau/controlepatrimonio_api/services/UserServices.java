package com.ideau.controlepatrimonio_api.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ideau.controlepatrimonio_api.infra.exceptions.HTTPException;
import com.ideau.controlepatrimonio_api.model.Usuario.Usuario;
import com.ideau.controlepatrimonio_api.model.Usuario.dto.AutenticacaoDTO;
import com.ideau.controlepatrimonio_api.model.Usuario.dto.UsuarioAlteraveisDTO;
import com.ideau.controlepatrimonio_api.model.Usuario.dto.UsuarioPublicoDTO;
import com.ideau.controlepatrimonio_api.repositories.AuditoriaRepository;
import com.ideau.controlepatrimonio_api.repositories.CargoRepository;
import com.ideau.controlepatrimonio_api.repositories.UserRepository;
import com.ideau.controlepatrimonio_api.utils.Utils;

@Service
public class UserServices {
    Logger logger = LoggerFactory.getLogger(UserServices.class);
    private final UserRepository userRepository;
    private final CargoRepository cargoRepository;

    UserServices(
        UserRepository userRepository, 
        CargoRepository cargoRepository,
        AuditoriaRepository auditoriaRepository) {
        this.cargoRepository = cargoRepository;
        this.userRepository = userRepository;
    }
    
    public Boolean postLoginService(AutenticacaoDTO objAuth) {
        Usuario userNoBd = userRepository.findByUsername(objAuth.login());
        if (userNoBd == null) return false;
        if ((userNoBd.getUsername() == objAuth.login()) && 
            (userNoBd.getSenha() == objAuth.senha())) {
          return true;
        } else {
            return false;
        }
    }

    public Usuario postUserService(Usuario objUsuario) {
        try {
            Usuario userNoBd = userRepository.findByUsername(objUsuario.getUsername()); 
            if (userNoBd.getUsername() == objUsuario.getUsername()) {
                throw new HTTPException(HttpStatus.CONFLICT, "Nome de usuário já em uso!");
            }
            if (objUsuario.getAtivo() == null) {
                objUsuario.setAtivo(1);
            } //Caso não especifique, presupõe-se que é ativo

            Usuario novoUsuario = userRepository.save(objUsuario);
            return novoUsuario;
        } catch (Exception e) {
            throw new HTTPException(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                "Erro ao cadastrar novo usuário: " + e.getMessage());
        }
    }

    public List<Usuario> getAllUsersService(Integer ativo) {
        if (ativo == null) ativo = 2;
        if (ativo == 2) {
            logger.info("Buscando todos os usuários...");
            return userRepository.findAll();
        } else {
            logger.info("Buscando usuários, filtrando por ativos e inativos...");
            return userRepository.findAllByAtivo(ativo);
        }
    }

    public Usuario putUserService(UsuarioAlteraveisDTO objUsuarioDTO, Long idUsuario) {
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
            return usuarioNoBd;
        } catch (Exception e) {
            throw new HTTPException(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                "Erro ao atualizar dados do usuário: " + e.getMessage());
        }
    }
}