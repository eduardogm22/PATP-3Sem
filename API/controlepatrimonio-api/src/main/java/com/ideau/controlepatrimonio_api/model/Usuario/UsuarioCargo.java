package com.ideau.controlepatrimonio_api.model.Usuario;

public enum UsuarioCargo {
    DEV("dev"),
    ADMIN("admin"),
    REGISTRADOR("registrador"),
    VISUALIZADOR("visualizador");

    private String cargo;
    
    UsuarioCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCargo() {
        return cargo;
    }
}
