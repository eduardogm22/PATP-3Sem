package com.ideau.controlepatrimonio_api.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ideau.controlepatrimonio_api.infra.exceptions.HTTPException;
import com.ideau.controlepatrimonio_api.model.Usuario.Usuario;
import com.ideau.controlepatrimonio_api.model.Usuario.dto.UsuarioAlteraveisDTO;
import com.ideau.controlepatrimonio_api.model.Usuario.dto.UsuarioPublicoDTO;
import com.ideau.controlepatrimonio_api.repositories.AuditoriaRepository;
import com.ideau.controlepatrimonio_api.repositories.CargoRepository;
import com.ideau.controlepatrimonio_api.repositories.UserRepository;
import com.ideau.controlepatrimonio_api.utils.RetornaIDpeloUsername;
import com.ideau.controlepatrimonio_api.utils.Utils;

@Service
public class UserServices {
    Logger logger = LoggerFactory.getLogger(UserServices.class);
    private final UserRepository userRepository;
    private final CargoRepository cargoRepository;
    private final AuditoriaRepository auditoriaRepository;
    private final RetornaIDpeloUsername retornaIDpeloUsername;

    UserServices(
        UserRepository userRepository, 
        CargoRepository cargoRepository,
        AuditoriaRepository auditoriaRepository) {
        this.cargoRepository = cargoRepository;
        this.userRepository = userRepository;
        this.auditoriaRepository = auditoriaRepository;
        this.retornaIDpeloUsername = new RetornaIDpeloUsername(userRepository);
    }
    
    public UsuarioPublicoDTO postUserService(Usuario objUsuario) {
        try {
            if (userRepository.findByUsername(objUsuario.getUsername()).isPresent()) {
                throw new HTTPException(HttpStatus.CONFLICT, "Nome de usuário já em uso!");
            }
            String senhaCriptografada = new BCryptPasswordEncoder().encode(objUsuario.getSenha());
            objUsuario.setSenha(senhaCriptografada);
            if (objUsuario.getAtivo() == null) {
                objUsuario.setAtivo(1);
            } //Caso não especifique, presupõe-se que é ativo

            Usuario novoUsuario = userRepository.save(objUsuario);
            return new UsuarioPublicoDTO(
                novoUsuario.getIdUsuario(),
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

    public List<UsuarioPublicoDTO> getAllUsersService(Integer ativo) {
        List<Usuario> usuarios;
        if (ativo == null) ativo = 2;
        if (ativo == 2) {
            logger.info("Buscando todos os usuários...");
            usuarios = userRepository.findAll();
        } else {
            logger.info("Buscando usuários, filtrando por ativos e inativos...");
            usuarios = userRepository.findAllByAtivo(ativo);
        }
        logger.info("usuarios.stream");
        return usuarios.stream()
        .map(usuario -> new UsuarioPublicoDTO(
            usuario.getIdUsuario(),
            usuario.getUsername(),
            usuario.getNomeCompleto(),
            usuario.getEmail(),
            cargoRepository.findNomeById(usuario.getIdCargo()),
            usuario.getDataCriacao(),
            Utils.formataAtivo(usuario.getAtivo())
        ))
        .toList();
    }

    public UsuarioPublicoDTO putUserService(UsuarioAlteraveisDTO objUsuarioDTO, String idUsuario) {
        try {
            Usuario usuarioNoBd = userRepository.findByIdUsuario(idUsuario);
            if (usuarioNoBd == null) {
                throw new HTTPException(HttpStatus.NOT_FOUND, "Usuário não encontrado!");
            }
            if (!Utils.isNullOrEmpty(objUsuarioDTO.username())) usuarioNoBd.setUsername(objUsuarioDTO.username());      
            if (!Utils.isNullOrEmpty(objUsuarioDTO.senha())) {                
                usuarioNoBd.setSenha(new BCryptPasswordEncoder().encode(objUsuarioDTO.senha()));
            }      
            if (!Utils.isNullOrEmpty(objUsuarioDTO.nomeCompleto())) usuarioNoBd.setNomeCompleto(objUsuarioDTO.nomeCompleto());      
            if (!Utils.isNullOrEmpty(objUsuarioDTO.email())) usuarioNoBd.setEmail(objUsuarioDTO.email());
            if (!Utils.isNullOrEmpty(objUsuarioDTO.ativo())) usuarioNoBd.setAtivo(objUsuarioDTO.ativo());     
            userRepository.save(usuarioNoBd);
            return new UsuarioPublicoDTO(
                usuarioNoBd.getIdUsuario(),
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