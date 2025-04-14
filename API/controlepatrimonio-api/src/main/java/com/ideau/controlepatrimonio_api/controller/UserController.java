package com.ideau.controlepatrimonio_api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ideau.controlepatrimonio_api.dto.UsuarioAlteraveisDTO;
import com.ideau.controlepatrimonio_api.model.Usuario.Usuario;
import com.ideau.controlepatrimonio_api.repositories.UserRepository;
import com.ideau.controlepatrimonio_api.services.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    
    @Autowired   
    private UserRepository userRepository;
    private Services services;

    @PostMapping("/users/")
    public Usuario postUser(@RequestBody Usuario objUsuario) {

    return userRepository.save(objUsuario);
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
        
        Usuario usuarioPut = services.trataPutUsuarios(objUsuarioDTO, userRepository, idUsuario);
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
