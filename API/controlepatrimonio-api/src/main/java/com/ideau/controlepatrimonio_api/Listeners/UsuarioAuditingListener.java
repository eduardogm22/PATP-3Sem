package com.ideau.controlepatrimonio_api.Listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.ideau.controlepatrimonio_api.model.Auditoria.UsuarioAudit;
import com.ideau.controlepatrimonio_api.model.Usuario.Usuario;
import com.ideau.controlepatrimonio_api.repositories.AuditoriaRepository;
import com.ideau.controlepatrimonio_api.repositories.UserRepository;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;

@Component
public class UsuarioAuditingListener {

    @Autowired
    private UsuarioAuditRepository auditRepository;

    @Autowired
    private UserRepository userRepository;

    @PostPersist
    public void onPostPersist(Usuario usuario) {
        salvaAuditoria(usuario, "insert", null);
    }

    @PostUpdate
    public void onPostUpdate(Usuario usuario) {
        // Busque o estado anterior do usuário
        Usuario oldUser = UserRepository.findByIdUsuario(usuario.getIdUsuario());
        salvaAuditoria(usuario, "update", oldUser);
    }

    @PostRemove
    public void onPostRemove(Usuario usuario) {
        salvaAuditoria(usuario, "delete", usuario);
    }

    private void salvaAuditoria(Usuario usuarioNovo, String tipo, Usuario usuarioAntigo) {
        String idResponsavel = SecurityContextHolder.getContext().getAuthentication().getName();

        UsuarioAudit audit = new UsuarioAudit();
        audit.setIdResponsavelAlteracao(idResponsavel);
        audit.setTipoAlteracao(tipo);

        if (tipo.equals("insert")) {
            audit.setDadosNovos(usuarioNovo.getIdUsuario());
        } else if (tipo.equals("delete")) {
            audit.setDadosAntigos(
                "idUsuario: " + usuarioAntigo.getIdUsuario() +
                ", username: " + usuarioAntigo.getUsername() +
                ", nomeCompleto: " + usuarioAntigo.getNomeCompleto() +
                ", email: " + usuarioAntigo.getEmail() +
                ", idCargo: " + usuarioAntigo.getIdCargo() +
                ", ativo: " + usuarioAntigo.getAtivo()
            );
        } else if (tipo.equals("update")) {
            audit.setDadosAntigos(
                "idUsuario: " + usuarioAntigo.getIdUsuario() +
                ", username: " + usuarioAntigo.getUsername() +
                ", nomeCompleto: " + usuarioAntigo.getNomeCompleto() +
                ", email: " + usuarioAntigo.getEmail() +
                ", idCargo: " + usuarioAntigo.getIdCargo() +
                ", ativo: " + usuarioAntigo.getAtivo()
            );
            audit.setDadosNovos(
                "idUsuario: " + usuarioNovo.getIdUsuario() +
                ", username: " + usuarioNovo.getUsername() +
                ", nomeCompleto: " + usuarioNovo.getNomeCompleto() +
                ", email: " + usuarioNovo.getEmail() +
                ", idCargo: " + usuarioNovo.getIdCargo() +
                ", ativo: " + usuarioNovo.getAtivo()
            );
        }

        AuditoriaRepository.save(audit);
    }
}

