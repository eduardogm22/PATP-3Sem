package com.ideau.controlepatrimonio_api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ideau.controlepatrimonio_api.model.Usuario.Usuario;
import com.ideau.controlepatrimonio_api.model.Usuario.dto.AutenticacaoDTO;
import com.ideau.controlepatrimonio_api.model.Usuario.dto.UsuarioAlteraveisDTO;
import com.ideau.controlepatrimonio_api.model.Usuario.dto.UsuarioPublicoDTO;
import com.ideau.controlepatrimonio_api.repositories.CargoRepository;
import com.ideau.controlepatrimonio_api.repositories.UserRepository;
import com.ideau.controlepatrimonio_api.services.UserServices;
import com.ideau.controlepatrimonio_api.utils.Utils;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class UserController {
    
    private final UserRepository userRepository;
    private final UserServices services;
    private final CargoRepository cargoRepository;

    UserController(
        UserRepository userRepository, 
        UserServices services, 
        CargoRepository cargoRepository) {
        
        this.userRepository = userRepository;
        this.services = services;
        this.cargoRepository = cargoRepository;
    }

    @PostMapping("/login/")
    public ResponseEntity<String> postLogin(@RequestBody AutenticacaoDTO objAuth) {
        if (services.postLoginService(objAuth)) {
            return ResponseEntity.ok("Usuário autenticado!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erro na autenticação");
        }
    }
    
    @PostMapping("/users/")
    public Usuario postUser(@RequestBody Usuario objUsuario) {
        return services.postUserService(objUsuario);
    }

    @GetMapping("/users")
    public List<Usuario> getAllUsers(@RequestParam(required = false) Integer ativo) {
        return services.getAllUsersService(ativo);
    }
    
    @GetMapping("/users/{idUsuario}")
    public Usuario getUserById(@PathVariable Long idUsuario) {
        return userRepository.findByIdUsuario(idUsuario);
    }
    
    @PutMapping("/users/{idUsuario}")
    public Usuario putUser(
        @PathVariable Long idUsuario, 
        @RequestBody UsuarioAlteraveisDTO objUsuarioDTO) {
        return services.putUserService(objUsuarioDTO, idUsuario);
    }

    @DeleteMapping("/users/{idUsuario}")
    public ResponseEntity<String> deleteUser(@PathVariable Long idUsuario) {
        userRepository.deleteById(idUsuario);
        return ResponseEntity
               .status(HttpStatus.OK)
               .body("Usuário excluído!");
    }
}
