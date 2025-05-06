package com.ideau.controlepatrimonio_api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ideau.controlepatrimonio_api.model.Auditoria.UsuarioAudit;

@Repository
public interface AuditoriaRepository extends CrudRepository<UsuarioAudit, Long> {}
