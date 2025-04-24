package com.ideau.controlepatrimonio_api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ideau.controlepatrimonio_api.model.Usuario.Usuario;
import com.ideau.controlepatrimonio_api.model.Usuario.dto.UsuarioAlteraveisDTO;
import com.ideau.controlepatrimonio_api.model.Usuario.dto.UsuarioPublicoDTO;
import com.ideau.controlepatrimonio_api.repositories.UserRepository;
import com.ideau.controlepatrimonio_api.services.UserServices;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class UserController {
    
    private final UserRepository userRepository;
    private final UserServices services;

    UserController(UserRepository userRepository, UserServices services) {
        this.userRepository = userRepository;
        this.services = services;
    }

    @PostMapping("/users/")
    public UsuarioPublicoDTO postUser(@RequestBody Usuario objUsuario) {
        return services.CadastraUsuario(objUsuario, userRepository);
    }

    @GetMapping("/users/")
    public List<Usuario> getAllUsers() {
        return userRepository.findAll();
    }
    
    @GetMapping("/users/{idUsuario}")
    public Usuario getUserById(@PathVariable String idUsuario) {
        return userRepository.findByIdUsuario(idUsuario);
    }
    
    @PutMapping("/users/{idUsuario}")
    public ResponseEntity<String> putUser(
        @PathVariable String idUsuario, 
        @RequestBody UsuarioAlteraveisDTO objUsuarioDTO) {
        
        Usuario usuarioPut = services.AtualizaUsuario(objUsuarioDTO, userRepository, idUsuario);
        if (usuarioPut == null) return ResponseEntity
                                     .status(HttpStatus.NOT_FOUND)
                                     .body("Usuário não encontrado!");

        return ResponseEntity.ok("Usuário atualizado com sucesso!");
    }

    @DeleteMapping("/users/{idUsuario}")
    public ResponseEntity<String> deleteUser(@PathVariable String idUsuario) {
        userRepository.deleteById(idUsuario);
        return ResponseEntity
               .status(HttpStatus.OK)
               .body("Usuário excluído!");
    }
}
