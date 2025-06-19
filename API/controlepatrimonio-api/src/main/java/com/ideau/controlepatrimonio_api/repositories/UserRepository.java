package com.ideau.controlepatrimonio_api.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ideau.controlepatrimonio_api.model.Usuario.Usuario;

@Repository
public interface UserRepository extends CrudRepository<Usuario, String> {
     List<Usuario> findAll();
     Usuario findByIdUsuario(String idUsuario);
     Usuario findByUsername(String username);
     List<Usuario> findAllByAtivo(Integer ativo);
}