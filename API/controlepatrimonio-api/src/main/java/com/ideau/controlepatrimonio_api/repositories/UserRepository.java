package com.ideau.controlepatrimonio_api.repositories;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.ideau.controlepatrimonio_api.model.Usuario.Usuario;

@Repository
public interface UserRepository extends CrudRepository<Usuario, String> {
    List<Usuario> findAll();
    Usuario findByIdUsuario(String idUsuario);
    UserDetails findByUsername(String username);
}