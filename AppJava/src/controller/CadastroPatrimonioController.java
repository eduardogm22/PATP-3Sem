package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import model.ItemPatrimonio;

import java.io.IOException;

public class CadastroPatrimonioController {
    @FXML
    private TableColumn<ItemPatrimonio, String> colNome;

    @FXML
    private TableColumn<ItemPatrimonio, String> colSituacao;

    @FXML
    private TableView<ItemPatrimonio> tabelaItens;

    @FXML private TextField txtNomeProduto;
    @FXML private TextField txtSituacao;
    @FXML private ComboBox<String> cmbCategoria;
    @FXML private ComboBox<String> cmbSetor;
    @FXML private TextField txtValorUnitario;
    @FXML private TextField txtQuantidade;


    private ObservableList<ItemPatrimonio> listaItens = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Iniciar colunas da tabela
        colNome.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNome()));
        colSituacao.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getSituacao()));

        tabelaItens.setItems(listaItens);

        // Carregar categorias e setores de exemplo
        cmbCategoria.setItems(FXCollections.observableArrayList("Informática", "Mobiliário", "Ferramentas"));
        cmbSetor.setItems(FXCollections.observableArrayList("TI", "Manutenção", "Financeiro"));
    }

    @FXML
    private void adicionarItem() {
        String nome = txtNomeProduto.getText();
        String situacao = txtSituacao.getText();
        String categoria = cmbCategoria.getValue();
        String setor = cmbSetor.getValue();
        double valor = Double.parseDouble(txtValorUnitario.getText());
        int qtd = Integer.parseInt(txtQuantidade.getText());

        ItemPatrimonio item = new ItemPatrimonio(nome, situacao, categoria, setor, valor, qtd);
        listaItens.add(item);

        limparCampos();
    }

    @FXML
    private void confirmarCadastro() {
        for (ItemPatrimonio item : listaItens) {
            salvarNoBanco(item); // Aqui você pode chamar um método DAO real
        }

        listaItens.clear(); // Limpa a tabela após salvar
        showAlert("Cadastro", "Itens salvos com sucesso!", Alert.AlertType.INFORMATION);
    }

    private void salvarNoBanco(ItemPatrimonio item) {
        // Aqui você implementaria o código para salvar no banco (via JDBC ou API REST)
        System.out.println("Salvando no banco: " + item.getNome());
    }

    private void limparCampos() {
        txtNomeProduto.clear();
        txtSituacao.clear();
        txtValorUnitario.clear();
        txtQuantidade.clear();
        cmbCategoria.getSelectionModel().clearSelection();
        cmbSetor.getSelectionModel().clearSelection();
    }

    private void showAlert(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
    @FXML
    private void removerSelecionado() {
        ItemPatrimonio item = tabelaItens.getSelectionModel().getSelectedItem();
        if (item != null) {
            listaItens.remove(item);
        }
    }

}
