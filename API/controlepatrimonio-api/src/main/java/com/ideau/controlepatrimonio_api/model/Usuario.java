package com.ideau.controlepatrimonio_api.model;

import java.time.LocalDateTime;

public class Usuario {
    private int idUsuario;
    private String usuario;
    private String senha;
    private String nomeCompleto;
    private String email;
    private int idCargo;
    private LocalDateTime dataCriacao;

    //getters
    public int getIdUsuario () {
        return idUsuario;
    }
    public String getUsuario () {
        return usuario;
    }
    public String getSenha () {
        return senha;
    }
    public String getNomeCompleto () {
        return nomeCompleto;
    }
    public String getEmail () {
        return email;
    }
    public int getIdCargo () {
        return idCargo;
    }
    public LocalDateTime getDataCriacao () {
        return dataCriacao;
    }
    //setters
    public void setIdUsuario ( int idUsuario ) {
        this.idUsuario = idUsuario;
    }
    public void setUsuario ( String usuario ) {
        this.usuario = usuario;
    }
    public void setSenha ( String senha ) {
        this.senha = senha;
    }
    public void setNomeCompleto ( String nomeCompleto ) {
        this.nomeCompleto = nomeCompleto;
    }
    public void setEmail ( String email ) {
        this.email = email;
    }
    public void setIdCargo ( int idCargo ) {
        this.idCargo = idCargo;
    }
    public void setDataCriacao ( LocalDateTime dataCriacao ) {
        this.dataCriacao = dataCriacao;
    }
}
