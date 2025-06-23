package controller;

public class SessaoController {
    private static String nomeUsuario;

    public static void setNomeUsuario(String nome) {
        nomeUsuario = nome;
    }

    public static String getNomeUsuario() {
        return nomeUsuario;
    }

    public static void limpar() {
        nomeUsuario = null;
    }
}