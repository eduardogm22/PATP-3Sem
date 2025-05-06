package com.ideau.controlepatrimonio_api.utils;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import com.ideau.controlepatrimonio_api.repositories.UserRepository;

import com.ideau.controlepatrimonio_api.model.Usuario.Usuario;

@AllArgsConstructor
public class RetornaIDpeloUsername {

    private final UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public RetornaIDpeloUsername(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    Logger logger = LoggerFactory.getLogger(RetornaIDpeloUsername.class);
    public String retornaIDpeloUsername () {
        String usernameLogado = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Usuario> usuarioNoBd = userRepository.findByUsername(usernameLogado);
        if (usuarioNoBd.isPresent()) {
            return usuarioNoBd.get().getIdUsuario();
        }
        logger.info("Não foi possível encontrar o idUsuario para setar a variável de ambiente!");
        return "Não localizado";
    }
}
