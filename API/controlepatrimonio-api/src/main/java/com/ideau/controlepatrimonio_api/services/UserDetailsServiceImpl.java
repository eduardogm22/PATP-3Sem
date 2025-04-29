package com.ideau.controlepatrimonio_api.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ideau.controlepatrimonio_api.infra.exceptions.HTTPException;
import com.ideau.controlepatrimonio_api.model.Usuario.Usuario;
import com.ideau.controlepatrimonio_api.model.Usuario.UsuarioAutenticado;
import com.ideau.controlepatrimonio_api.repositories.CargoRepository;
import com.ideau.controlepatrimonio_api.repositories.UserRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private final UserRepository userRepository;
    private final CargoRepository cargoRepository;
    
    UserDetailsServiceImpl(UserRepository userRepository, CargoRepository cargoRepository) {
        this.userRepository = userRepository;
        this.cargoRepository = cargoRepository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Usuario usuario = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));
    
            logger.info("Usuário encontrado: {}", usuario.getUsername());
    
            String nomeCargo = cargoRepository.findNomeById(usuario.getIdCargo());
            logger.info("Cargo encontrado: {}", nomeCargo);
    
            return new UsuarioAutenticado(usuario, nomeCargo);
    
        } catch (Exception e) {
            throw new HTTPException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
