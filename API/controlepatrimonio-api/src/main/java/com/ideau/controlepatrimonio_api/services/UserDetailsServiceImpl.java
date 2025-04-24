package com.ideau.controlepatrimonio_api.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ideau.controlepatrimonio_api.model.Usuario.Usuario;
import com.ideau.controlepatrimonio_api.model.Usuario.UsuarioAutenticado;
import com.ideau.controlepatrimonio_api.repositories.CargoRepository;
import com.ideau.controlepatrimonio_api.repositories.UserRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
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
    
            System.out.println("Usuário encontrado: " + usuario.getUsername());
    
            String nomeCargo = cargoRepository.findNomeById(usuario.getIdCargo());
            System.out.println("Cargo encontrado: " + nomeCargo);
    
            return new UsuarioAutenticado(usuario, nomeCargo);
    
        } catch (Exception e) {
            System.out.println("ERRO NA AUTENTICAÇÃO:");
            e.printStackTrace(); // isso vai te mostrar o stack trace no terminal
            throw e; // relança para o Spring capturar
        }
    }
}
