package com.ideau.controlepatrimonio_api.model.Auditoria;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Usuarios_audit")
@Getter
@Setter
public class UsuarioAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAlteracao;

    private Long idResponsavelAlteracao;
    private String tipoAlteracao;
    private LocalDateTime dataAlteracao = LocalDateTime.now();
    private String dadosNovos;
    private String dadosAntigos;
}
