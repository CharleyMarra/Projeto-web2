package br.ifg.urt.gamercatalog_api.model;

public class Dlc {

    private int idDlc;
    private String nome;
    private float preco;

    public Dlc() {
    }

    public Dlc(int idDlc, String nome, float preco) {
        this.idDlc = idDlc;
        this.nome = nome;
        this.preco = preco;
    }

    public int getIdDlc() {
        return idDlc;
    }

    public void setIdDlc(int idDlc) {
        this.idDlc = idDlc;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }
}