package br.ifg.urt.gamercatalog_api.model;

public class Conquista {

    private int idConquista;
    private String titulo;
    private String raridade;

    public Conquista() {
    }

    public Conquista(int idConquista, String titulo, String raridade) {
        this.idConquista = idConquista;
        this.titulo = titulo;
        this.raridade = raridade;
    }

    public int getIdConquista() {
        return idConquista;
    }

    public void setIdConquista(int idConquista) {
        this.idConquista = idConquista;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getRaridade() {
        return raridade;
    }

    public void setRaridade(String raridade) {
        this.raridade = raridade;
    }
}