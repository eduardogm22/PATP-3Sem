package com.ideau.controlepatrimonio_api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ideau.controlepatrimonio_api.model.Usuario;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
public class controller {
    
    @GetMapping("/teste/{nome}")
    public String readRoot(@PathVariable String nome) {
        return "Seja bem vindo, "+ nome +'!';
    }

    @PostMapping("/usuario")
    public Usuario postUser(@RequestBody Usuario usuario) {

        return usuario;
    }
    
    
}
