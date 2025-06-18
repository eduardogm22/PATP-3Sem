package com.ideau.controlepatrimonio_api.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ideau.controlepatrimonio_api.model.DetalhesPatr.Fornecedor;

public interface FornecRepository extends CrudRepository<Fornecedor, String> {
    List<Fornecedor> findAllByAtivo(Integer ativo);
    List<Fornecedor> findAll();
}
