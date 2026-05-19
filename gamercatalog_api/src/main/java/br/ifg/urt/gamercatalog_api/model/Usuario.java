package br.ifg.urt.gamercatalog_api.model;

public class Usuario {

    private int idUsuario;
    private String nome;
    private String email;
    private String senha;

    public Usuario() {
    }

    public Usuario(int idUsuario, String nome, String email, String senha) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public void fazerComentario() {
        System.out.println("Comentário realizado");
    }

    public void avaliarJogo() {
        System.out.println("Jogo avaliado");
    }

    public void favoritar() {
        System.out.println("Jogo favoritado");
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}