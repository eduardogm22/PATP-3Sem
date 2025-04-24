package com.ideau.controlepatrimonio_api.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ideau.controlepatrimonio_api.model.Usuario.Cargos;

public interface CargoRepository extends CrudRepository<Cargos, Integer> {
    @Query("SELECT UPPER(c.nome) FROM Cargos c WHERE c.idCargo = :idCargo")
    String findNomeById(@Param("idCargo") Integer idCargo);
}
