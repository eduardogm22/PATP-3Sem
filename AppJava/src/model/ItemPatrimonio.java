package model;

public class ItemPatrimonio {
    private String nome;
    private String situacao;
    private String categoria;
    private String setor;
    private double valorUnitario;
    private int quantidade;

    // Construtor
    public ItemPatrimonio(String nome, String situacao, String categoria, String setor, double valorUnitario, int quantidade) {
        this.nome = nome;
        this.situacao = situacao;
        this.categoria = categoria;
        this.setor = setor;
        this.valorUnitario = valorUnitario;
        this.quantidade = quantidade;
    }

    // Getters e Setters (obrigatórios para TableView)
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getSituacao() { return situacao; }
    public void setSituacao(String situacao) { this.situacao = situacao; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getSetor() { return setor; }
    public void setSetor(String setor) { this.setor = setor; }

    public double getValorUnitario() { return valorUnitario; }
    public void setValorUnitario(double valorUnitario) { this.valorUnitario = valorUnitario; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
}
