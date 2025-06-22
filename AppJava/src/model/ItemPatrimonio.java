package model;

import java.util.Date;

public class ItemPatrimonio {
    private int id;
    private String nome;
    private String categoria;
    private String setor;
    private String situacao;
    private double valorUnitario;
    private int quantidade;
    private String recebidoPor;
    private java.sql.Date dataRecebimento;
    private String fornecedor;
    private java.sql.Date dataAquisicao;
    private String chaveAcesso;
    private String numero;
    private String serie;
    private Date dtCadastro;
    // Construtor completo
    public ItemPatrimonio(int id, String nome, String categoria, String setor, String situacao,
                          double valorUnitario, int quantidade, String recebidoPor,
                          java.sql.Date dataRecebimento, String fornecedor, java.sql.Date dataAquisicao,
                          String chaveAcesso, String numero, String serie, Date dtCadastro) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.setor = setor;
        this.situacao = situacao;
        this.valorUnitario = valorUnitario;
        this.quantidade = quantidade;
        this.recebidoPor = recebidoPor;
        this.dataRecebimento = dataRecebimento;
        this.fornecedor = fornecedor;
        this.dataAquisicao = dataAquisicao;
        this.chaveAcesso = chaveAcesso;
        this.numero = numero;
        this.serie = serie;
        this.dtCadastro = dtCadastro;
    }


    // Construtor SEM o ID (para INSERT)
    public ItemPatrimonio(String nome, String categoria, String setor, String situacao,
                          double valorUnitario, int quantidade, String recebidoPor,
                          java.sql.Date dataRecebimento, String fornecedor, java.sql.Date dataAquisicao,
                          String chaveAcesso, String numero, String serie) {
        this.nome = nome;
        this.categoria = categoria;
        this.setor = setor;
        this.situacao = situacao;
        this.valorUnitario = valorUnitario;
        this.quantidade = quantidade;
        this.recebidoPor = recebidoPor;
        this.dataRecebimento = dataRecebimento;
        this.fornecedor = fornecedor;
        this.dataAquisicao = dataAquisicao;
        this.chaveAcesso = chaveAcesso;
        this.numero = numero;
        this.serie = serie;
    }

    // Getters e Setters (obrigatórios para TableView)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getRecebidoPor() {
        return recebidoPor;
    }

    public void setRecebidoPor(String recebidoPor) {
        this.recebidoPor = recebidoPor;
    }

    public java.sql.Date getDataRecebimento() {
        return dataRecebimento;
    }

    public void setDataRecebimento(java.sql.Date dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public java.sql.Date getDataAquisicao() {
        return dataAquisicao;
    }

    public void setDataAquisicao(java.sql.Date dataAquisicao) {
        this.dataAquisicao = dataAquisicao;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getChaveAcesso () {
        return chaveAcesso;
    }

    public void setChaveAcesso (String chaveAcesso) {
        this.chaveAcesso = chaveAcesso;
    }

    // Valor total calculado
    public double getValorTotal() {
        return this.valorUnitario * this.quantidade;
    }
}
