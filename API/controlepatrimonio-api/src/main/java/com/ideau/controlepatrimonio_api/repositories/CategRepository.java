package com.ideau.controlepatrimonio_api.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ideau.controlepatrimonio_api.model.DetalhesPatr.Categoria;

public interface CategRepository extends CrudRepository<Categoria, String> {
    List<Categoria> findAllByAtivo(Integer ativo);
    List<Categoria> findAll();
}
