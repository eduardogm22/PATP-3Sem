package com.ideau.controlepatrimonio_api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ideau.controlepatrimonio_api.model.Usuario.Usuario;
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

    @PostMapping("/users/")
    public UsuarioPublicoDTO postUser(@RequestBody Usuario objUsuario) {
        return services.postUserService(objUsuario);
    }

    @GetMapping("/users")
    public List<UsuarioPublicoDTO> getAllUsers(@RequestParam(required = false) Integer ativo) {
        return services.getAllUsersService(ativo);
    }
    
    @GetMapping("/users/{idUsuario}")
    public UsuarioPublicoDTO getUserById(@PathVariable String idUsuario) {
        Usuario usuarioNoBd = userRepository.findByIdUsuario(idUsuario);
        return new UsuarioPublicoDTO(
            usuarioNoBd.getIdUsuario(),
            usuarioNoBd.getUsername(),
            usuarioNoBd.getNomeCompleto(),
            usuarioNoBd.getEmail(),
            cargoRepository.findNomeById(usuarioNoBd.getIdCargo()),
            usuarioNoBd.getDataCriacao(),
            Utils.formataAtivo(usuarioNoBd.getAtivo())

        );
    }
    
    @PutMapping("/users/{idUsuario}")
    public UsuarioPublicoDTO putUser(
        @PathVariable String idUsuario, 
        @RequestBody UsuarioAlteraveisDTO objUsuarioDTO) {
        return services.putUserService(objUsuarioDTO, idUsuario);
    }

    @DeleteMapping("/users/{idUsuario}")
    public ResponseEntity<String> deleteUser(@PathVariable String idUsuario) {
        userRepository.deleteById(idUsuario);
        return ResponseEntity
               .status(HttpStatus.OK)
               .body("Usuário excluído!");
    }
}
