package controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;
import controller.InterfaceController;
import model.ItemPatrimonio;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.sql.SQLException;

public class PreviewBagController {
    private InterfaceController mainController;
    public void setMainController(InterfaceController controller) {
        this.mainController = controller;
    }

    @FXML
    private void abrirCadastro() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CadastroItensPatrimonio.fxml"));
        Region tela = loader.load();
        mainController.getRootPane().setCenter(tela);
    }

    @FXML
    private TableView<ItemPatrimonio> tabelaPatrimonios;

    @FXML
    private TableColumn<ItemPatrimonio, String> colID;

    @FXML
    private TableColumn<ItemPatrimonio, String> colNomePrd;

    @FXML
    private TableColumn<ItemPatrimonio, String> colCategoria;

    @FXML
    private TableColumn<ItemPatrimonio, String> colSetResp;

    @FXML
    private TableColumn<ItemPatrimonio, String> colSituacao;

    @FXML
    private TableColumn<ItemPatrimonio, String> colValUn;

    @FXML
    private TableColumn<ItemPatrimonio, String> colQuantidade;

    @FXML
    private TableColumn<ItemPatrimonio, String> colRecebPor;

    @FXML
    private TableColumn<ItemPatrimonio, String> colDtReceb;

    @FXML
    private TableColumn<ItemPatrimonio, String> colFornec;

    @FXML
    private TableColumn<ItemPatrimonio, String> colDtAquisicao;

    @FXML
    private TableColumn<ItemPatrimonio, String> colNumero;

    @FXML
    private TableColumn<ItemPatrimonio, String> colSerie;

    @FXML
    private void initialize() {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNomePrd.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colSetResp.setCellValueFactory(new PropertyValueFactory<>("setor"));
        colSituacao.setCellValueFactory(new PropertyValueFactory<>("situacao"));
        colValUn.setCellValueFactory(new PropertyValueFactory<>("valorUnitario"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colRecebPor.setCellValueFactory(new PropertyValueFactory<>("recebidoPor"));
        colDtReceb.setCellValueFactory(new PropertyValueFactory<>("dataRecebimento"));
        colFornec.setCellValueFactory(new PropertyValueFactory<>("fornecedor"));
        colDtAquisicao.setCellValueFactory(new PropertyValueFactory<>("dataAquisicao"));
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colSerie.setCellValueFactory(new PropertyValueFactory<>("serie"));

        // Ajustar colunas para largura automática
        tabelaPatrimonios.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        carregarDados();
    }

    private void carregarDados() {
        List<ItemPatrimonio> dados = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/PATP", "root", "1404");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM patrimonio")) {

            while (rs.next()) {
                dados.add(new ItemPatrimonio(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("categoria"),
                        rs.getString("setor_responsavel"),
                        rs.getString("situacao"),
                        rs.getDouble("valor"),
                        rs.getInt("quantidade"),
                        rs.getString("recebido_por"),
                        rs.getDate("data_recebimento"),
                        rs.getString("fornecedor"),
                        rs.getDate("data_aquisicao"),
                        rs.getString("chave_acesso"),
                        rs.getString("numero_documento"),
                        rs.getString("serie"),
                        rs.getTimestamp("criado_em")
                ));
            }

            tabelaPatrimonios.getItems().setAll(dados);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}