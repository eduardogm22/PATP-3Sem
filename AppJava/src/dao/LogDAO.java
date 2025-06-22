package dao;

import db.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LogDAO {

    public void registrar(String usuario, String acao, String tabela, String descricao, String jsonBackup) {
        String sql = "INSERT INTO log_usuarios (usuario, acao, tabela_afetada, descricao, jsonBackup) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            stmt.setString(2, acao);
            stmt.setString(3, tabela);
            stmt.setString(4, descricao);
            stmt.setString(5, jsonBackup);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // ou log para arquivo
        }
    }
}
