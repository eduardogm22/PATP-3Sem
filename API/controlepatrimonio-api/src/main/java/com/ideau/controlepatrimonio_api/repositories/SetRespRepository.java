package com.ideau.controlepatrimonio_api.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ideau.controlepatrimonio_api.model.DetalhesPatr.SetResp;

public interface SetRespRepository extends CrudRepository<SetResp, String> {
    List<SetResp> findAllByAtivo(Integer ativo);
    List<SetResp> findAll();
}
