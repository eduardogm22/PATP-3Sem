package controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.Conexao;

import javafx.scene.control.TextFormatter;
import javafx.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.UnaryOperator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import model.ItemPatrimonio;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.io.IOException;

import dao.*;


public class CadastroPatrimonioController {

    @FXML private TextField chaveAcesso;
    @FXML private TextField recebPor;
    @FXML private TextField numeroNF;
    @FXML private TextField numeroSerie;
    @FXML private DatePicker dataAquisicao;
    @FXML private DatePicker dataReceb;
    @FXML private ComboBox<String> cmbFornecedor;
    @FXML private TableColumn<ItemPatrimonio, String> colNome;
    @FXML private TableColumn<ItemPatrimonio, String> colSituacao;
    @FXML private TableColumn<ItemPatrimonio, Integer> colQuantidade;
    @FXML private TableColumn<ItemPatrimonio, Double> colValorTotal;
    @FXML private TableView<ItemPatrimonio> tabelaItens;
    @FXML private TextField nomeProduto;
    @FXML private ComboBox<String> situacaoProduto;
    @FXML private ComboBox<String> cmbCategoria;
    @FXML private ComboBox<String> cmbSetor;
    @FXML private TextField valorUnitario;
    @FXML private TextField quantidadeProduto;
    @FXML private Label labelValorTemp;

    private ObservableList<ItemPatrimonio> listaItens = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        tabelaItens.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        colNome.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNome()));
        colSituacao.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getSituacao()));
        colQuantidade.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getQuantidade()));
        colValorTotal.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getValorTotal()));
        cmbCategoria.setItems(FXCollections.observableArrayList("Informática", "Mobiliário", "Ferramentas"));
        cmbSetor.setItems(FXCollections.observableArrayList("TI", "Manutenção", "Financeiro"));
        situacaoProduto.setItems(FXCollections.observableArrayList("Bom", "Regular", "Ruim"));
        cmbFornecedor.setItems(FXCollections.observableArrayList("Destack Móveis", "KLM Informática", "América Ferramentas"));
        tabelaItens.setItems(listaItens);
        nomeProduto.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-ZÀ-ÿ\\s]*")) {
                nomeProduto.setText(oldValue);
            }
        });

        valorUnitario.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.,]\\d{0,2})?")) {
                valorUnitario.setText(oldValue);
            } else {
                atualizarValorTemp();
            }
        });

        quantidadeProduto.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                quantidadeProduto.setText(oldValue);
            } else {
                atualizarValorTemp();
            }
        });

        chaveAcesso.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.matches("\\d*")) {
                chaveAcesso.setText(newText.replaceAll("[^\\d]", ""));
            }
            if (chaveAcesso.getText().length() > 44) {
                chaveAcesso.setText(chaveAcesso.getText().substring(0, 44));
            }
        });

        numeroNF.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.matches("\\d*")) {
                numeroNF.setText(newText.replaceAll("[^\\d]", ""));
            }
        });

        recebPor.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.matches("[a-zA-Z\\s]*")) {
                recebPor.setText(newText.replaceAll("[^a-zA-Z\\s]", ""));
            }
        });

        UnaryOperator<TextFormatter.Change> chaveFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d{0,44}")) {
                return change;
            }
            return null;
        };

        chaveAcesso.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.getControlNewText().matches("\\d{0,44}")) {
                return change;
            }
            return null;
        }));

        UnaryOperator<TextFormatter.Change> numeroFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d{0,10}")) {
                return change;
            }
            return null;
        };

        numeroNF.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.getControlNewText().matches("\\d{0,9}")) {
                return change;
            }
            return null;}));

        UnaryOperator<TextFormatter.Change> recebidoFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[a-zA-ZÀ-ÿ\\s]{0,50}")) {
                return change;
            }
            return null;
        };

        recebPor.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("[\\p{L} ]{0,100}")) {
                return change;
            }
            return null;
        }));

        numeroSerie.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("[A-Za-z]{0,2}-?[0-9]{0,4}")) {
                return change;
            }
            return null;
        }));
    }

    private void atualizarValorTemp() {
        try {
            String textoValor = valorUnitario.getText().replace(",", ".");
            double valor = textoValor.isEmpty() ? 0 : Double.parseDouble(textoValor);
            int quantidade = quantidadeProduto.getText().isEmpty() ? 0 : Integer.parseInt(quantidadeProduto.getText());
            double total = valor * quantidade;
            labelValorTemp.setText(String.format("R$ %.2f", total));
        } catch (NumberFormatException e) {
            labelValorTemp.setText("R$ 0.00");
        }
    }

    @FXML
    private void adicionarItem() {
        String nome = nomeProduto.getText();
        String situacao = situacaoProduto.getValue();
        String categoria = cmbCategoria.getValue();
        String setor = cmbSetor.getValue();
        double valor = Double.parseDouble(valorUnitario.getText());
        int qtd = Integer.parseInt(quantidadeProduto.getText());
        String recebidoPor = recebPor.getText();
        LocalDate localDateReceb = dataReceb.getValue();
        Date dataRecebimento = Date.valueOf(localDateReceb);
        String fornecedor = cmbFornecedor.getValue();
        LocalDate localDateAquisicao = dataAquisicao.getValue();
        Date dtAquisicao = Date.valueOf(localDateAquisicao);
        String chAcesso = chaveAcesso.getText();
        String numero = numeroNF.getText();
        String serie = numeroSerie.getText();

        ItemPatrimonio item = new ItemPatrimonio(nome, categoria, setor, situacao, valor, qtd, recebidoPor, dataRecebimento, fornecedor,
                dtAquisicao, chAcesso, numero, serie);
        listaItens.add(item);
        limparCampos();
    }

    @FXML
    private void confirmarCadastro() {
        try {
            if (listaItens.isEmpty()) {
                showAlert("Erro", "A lista de itens está vazia." + "\nPor favor, adicione pelo menos um item.", Alert.AlertType.ERROR);
                return;
            }
            if (listaItens.isEmpty()) {
                showAlert("Erro", "A lista de itens está vazia." + "\nPor favor, adicione pelo menos um item.", Alert.AlertType.ERROR);
                return;
            }

            PatrimonioDAO dao = new PatrimonioDAO();
            Map<String, Object> dadosBackup = new LinkedHashMap<>();
            LogDAO logDAO = new LogDAO();

            for (ItemPatrimonio item : listaItens) {
                Integer patrimonioId = dao.salvarPatrimonioCompleto(
                        item.getChaveAcesso(),
                        item.getNumero(),
                        item.getDataAquisicao(),
                        item.getDataRecebimento(),
                        item.getFornecedor(),
                        item.getRecebidoPor(),
                        item.getSerie(),
                        item.getNome(),
                        item.getCategoria(),
                        item.getSetor(),
                        item.getSituacao(),
                        item.getValorUnitario(),
                        item.getQuantidade());
                        //Montando JSON com dados do backup
                        dadosBackup.put("Id do Patrimônio: ", patrimonioId);
                        String jsonBackup = new ObjectMapper().writeValueAsString(dadosBackup);

                        logDAO.registrar("admin", "INSERIR", "patrimonio", "Cadastro do patrimônio ID: " + patrimonioId, jsonBackup);
            }
//            dao.salvarItensIndividuais(patrimonioId, listaItens);

            showAlert("Sucesso", "Patrimônio e itens cadastrados com sucesso!" , Alert.AlertType.INFORMATION);
            limparCampos();

        } catch (Exception e) {
            showAlert("Erro ao salvar", "Detalhes: " + e.getMessage() + "\nPor favor, tente novamente.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void retornarParaTelaInicial() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/home.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) tabelaItens.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert("Erro", "Não foi possível voltar à tela inicial: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void salvarNoBanco(ItemPatrimonio item) {
        System.out.println("Salvando no banco: " + item.getNome());
    }

    private void limparCampos() {
        nomeProduto.clear();
        situacaoProduto.getSelectionModel().clearSelection();
        valorUnitario.clear();
        quantidadeProduto.clear();
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

    private boolean validarNotaFiscal() {
        String chave = chaveAcesso.getText();
        String numero = numeroNF.getText();
        String serie = numeroSerie.getText();

        if (!chave.matches("\\d{44}")) {
            showAlert("Erro de Validação", "A Chave de Acesso deve conter exatamente 44 dígitos numéricos.", Alert.AlertType.ERROR);
            return false;
        }

        if (!numero.matches("\\d{1,9}")) {
            showAlert("Erro de Validação", "Número da nota deve conter até 9 dígitos numéricos.", Alert.AlertType.ERROR);
            return false;
        }

        if (!serie.matches("\\d{1,3}")) {
            showAlert("Erro de Validação", "A Série deve conter até 3 dígitos numéricos.", Alert.AlertType.ERROR);
            return false;
        }

        if (dataAquisicao.getValue() == null || dataReceb.getValue() == null) {
            showAlert("Erro de Validação", "As datas devem ser preenchidas.", Alert.AlertType.ERROR);
            return false;
        }

        if (dataAquisicao.getValue().isAfter(dataReceb.getValue())) {
            showAlert("Erro de Validação", "Data de aquisição não pode ser posterior à data de recebimento.", Alert.AlertType.ERROR);
            return false;
        }

        if (dataReceb.getValue().isAfter(java.time.LocalDate.now())) {
            showAlert("Erro de Validação", "Data de recebimento não pode estar no futuro.", Alert.AlertType.ERROR);
            return false;
        }

        return true;

    }

    @FXML
    private void cancelarCadastro() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Cancelar Cadastro");
        alert.setContentText("Tem certeza que deseja cancelar? Os dados não salvos serão perdidos.");
        ButtonType confirmar = new ButtonType("Sim", ButtonBar.ButtonData.YES);
        ButtonType cancelar = new ButtonType("Não", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(confirmar, cancelar);
        alert.showAndWait().ifPresent(tipo -> {
            if (tipo == confirmar) {
                retornarParaTelaInicial();
            }
        });
    }
}
