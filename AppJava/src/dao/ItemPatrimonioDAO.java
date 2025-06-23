package dao;

import model.ItemPatrimonio;
import db.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class ItemPatrimonioDAO {

    public void salvar(ItemPatrimonio item) throws SQLException {
        String sql = "INSERT INTO itens_patrimonio (nome, situacao, categoria, setor, valor_unitario, quantidade) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getNome());
            stmt.setString(2, item.getSituacao());
            stmt.setString(3, item.getCategoria());
            stmt.setString(4, item.getSetor());
            stmt.setDouble(5, item.getValorUnitario());
            stmt.setInt(6, item.getQuantidade());

            stmt.executeUpdate();
        }
    }

}

