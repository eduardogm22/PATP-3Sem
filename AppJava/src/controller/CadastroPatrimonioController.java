package controller;
import db.Conexao;

import javafx.scene.control.TextFormatter;
import javafx.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @FXML private TextField ChaveAcesso;
    @FXML private TextField RecebPor;
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

        ChaveAcesso.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.matches("\\d*")) {
                ChaveAcesso.setText(newText.replaceAll("[^\\d]", ""));
            }
            if (ChaveAcesso.getText().length() > 44) {
                ChaveAcesso.setText(ChaveAcesso.getText().substring(0, 44));
            }
        });

        numeroNF.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.matches("\\d*")) {
                numeroNF.setText(newText.replaceAll("[^\\d]", ""));
            }
        });

        RecebPor.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.matches("[a-zA-Z\\s]*")) {
                RecebPor.setText(newText.replaceAll("[^a-zA-Z\\s]", ""));
            }
        });

        UnaryOperator<TextFormatter.Change> chaveFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d{0,44}")) {
                return change;
            }
            return null;
        };

        ChaveAcesso.setTextFormatter(new TextFormatter<String>(change -> {
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

        RecebPor.setTextFormatter(new TextFormatter<>(change -> {
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
        ItemPatrimonio item = new ItemPatrimonio(nome, situacao, categoria, setor, valor, qtd);
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
            double valorTotal = listaItens.stream()
                    .mapToDouble(item -> item.getValorUnitario() * item.getQuantidade())
                    .sum();
            String chave = ChaveAcesso.getText();
            String numeroDoc = numeroNF.getText();
            Date dataAq = Date.valueOf(dataAquisicao.getValue());
            Date dataRec = Date.valueOf(dataReceb.getValue());
            String fornecedor = cmbFornecedor.getValue();
            String recebidoPor = RecebPor.getText();
            String serie = numeroSerie.getText();
            String nomeProdutoStr = nomeProduto.getText();
            String categoria = cmbCategoria.getValue();
            String setor = cmbSetor.getValue();
            String situacao = situacaoProduto.getValue();

            int quantidadeTotal = listaItens.stream().mapToInt(ItemPatrimonio::getQuantidade).sum();

            PatrimonioDAO dao = new PatrimonioDAO();
            int patrimonioId = dao.salvarPatrimonioCompleto(
                    chave, numeroDoc, dataAq, dataRec, fornecedor, recebidoPor,
                    serie, nomeProdutoStr, categoria, setor, situacao, valorTotal, quantidadeTotal
            );

            dao.salvarItensIndividuais(patrimonioId, listaItens);
            LogDAO logDAO = new LogDAO();
            logDAO.registrar("admin", "INSERIR", "patrimonio", "Cadastro do patrimônio ID: " + patrimonioId);
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
        String chave = ChaveAcesso.getText();
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
