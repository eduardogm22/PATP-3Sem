package dao;

import db.Conexao;
import model.ItemPatrimonio;
import java.sql.*;
import java.util.List;

public class PatrimonioDAO {

    public int salvarPatrimonioCompleto(String chaveAcesso, String numeroDocumento, Date dataAquisicao,
                                        Date dataRecebimento, String fornecedor, String recebidoPor,
                                        String serie, String nomeProduto, String categoria,
                                        String setorResponsavel, String situacao,
                                        double valorTotal, int quantidade) throws SQLException {

        String sql = "INSERT INTO patrimonio (" +
                "chave_acesso, " +
                "numero_documento, " +
                "data_aquisicao, " +
                "data_recebimento, " +
                "fornecedor, " +
                "recebido_por, " +
                "serie, " +
                "nome, " +
                "categoria, " +
                "setor_responsavel, " +
                "situacao, " +
                "valor, " +
                "quantidade) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, chaveAcesso);
            stmt.setString(2, numeroDocumento);
            stmt.setDate(3, dataAquisicao);
            stmt.setDate(4, dataRecebimento);
            stmt.setString(5, fornecedor);
            stmt.setString(6, recebidoPor);
            stmt.setString(7, serie);
            stmt.setString(8, nomeProduto);
            stmt.setString(9, categoria);
            stmt.setString(10, setorResponsavel);
            stmt.setString(11, situacao);
            stmt.setDouble(12, valorTotal);
            stmt.setInt(13, quantidade);

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // retorna o ID gerado do patrimônio
            } else {
                throw new SQLException("Erro ao obter ID do patrimônio.");
            }
        }
    }

    public void updatePatrimonioCompleto(int idPatrimonio, String chaveAcesso, String numeroDocumento, Date dataAquisicao,
                                         Date dataRecebimento, String fornecedor, String recebidoPor,
                                         String serie, String nomeProduto, String categoria,
                                         String setorResponsavel, String situacao,
                                         double valorTotal, int quantidade) throws SQLException {

        String sql = "UPDATE patrimonio SET " +
                "chave_acesso = ?, " +
                "numero_documento = ?, " +
                "data_aquisicao = ?, " +
                "data_recebimento = ?, " +
                "fornecedor = ?, " +
                "recebido_por = ?, " +
                "serie = ?, " +
                "nome = ?, " +
                "categoria = ?, " +
                "setor_responsavel = ?, " +
                "situacao = ?, " +
                "valor = ?, " +
                "quantidade = ? " +
                "WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, chaveAcesso);
            stmt.setString(2, numeroDocumento);
            stmt.setDate(3, dataAquisicao);
            stmt.setDate(4, dataRecebimento);
            stmt.setString(5, fornecedor);
            stmt.setString(6, recebidoPor);
            stmt.setString(7, serie);
            stmt.setString(8, nomeProduto);
            stmt.setString(9, categoria);
            stmt.setString(10, setorResponsavel);
            stmt.setString(11, situacao);
            stmt.setDouble(12, valorTotal);
            stmt.setInt(13, quantidade);
            stmt.setInt(14, idPatrimonio);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("Nenhum patrimônio foi atualizado. Verifique o ID informado.");
            }
        }
    }

//    public void salvarItensIndividuais(int patrimonioId, List<ItemPatrimonio> itens) throws SQLException {
//        String sql = "INSERT INTO itens_patrimonio (" +
//                "patrimonio_id, " +
//                "nome, " +
//                "situacao, " +
//                "categoria, " +
//                "setor, " +
//                "valor_unitario) " +
//                "VALUES (?, ?, ?, ?, ?, ?)";
//
//        try (Connection conn = Conexao.conectar();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            for (ItemPatrimonio item : itens) {
//                for (int i = 0; i < item.getQuantidade(); i++) { // salvar item a item
//                    stmt.setInt(1, patrimonioId);
//                    stmt.setString(2, item.getNome());
//                    stmt.setString(3, item.getSituacao());
//                    stmt.setString(4, item.getCategoria());
//                    stmt.setString(5, item.getSetor());
//                    stmt.setDouble(6, item.getValorUnitario());
//                    stmt.addBatch();
//                }
//            }
//            stmt.executeBatch();
//        }
//    }
}
