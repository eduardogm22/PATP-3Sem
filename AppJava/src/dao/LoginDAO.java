package dao;

import model.LoginDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import db.Conexao;

public class LoginDAO {
    public boolean autenticar(LoginDTO login) {
        String sql = "SELECT * FROM usuarios WHERE nome_usuario = ? AND senha = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login.getLogin());
            stmt.setString(2, login.getSenha());

            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println("Erro no login: " + e.getMessage());
            return false;
        }
    }
}
