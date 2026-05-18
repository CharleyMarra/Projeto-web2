package br.ifg.urt.gamercatalog_api.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Avaliacao implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idAvaliacao;
    private Integer nota;
    private String textoCritica;
    private LocalDate dataPostagem;

    private Usuario usuario;
    private Jogo jogo;

    public Avaliacao() {
    }

    public Avaliacao(Long idAvaliacao, Integer nota, String textoCritica,
                      LocalDate dataPostagem, Usuario usuario, Jogo jogo) {

        this.idAvaliacao = idAvaliacao;
        this.nota = nota;
        this.textoCritica = textoCritica;
        this.dataPostagem = dataPostagem;
        this.usuario = usuario;
        this.jogo = jogo;
    }

    public Long getIdAvaliacao() {
        return idAvaliacao;
    }

    public void setIdAvaliacao(Long idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {

        if (nota == null || nota < 0 || nota > 10) {
            throw new IllegalArgumentException(
                "A nota deve estar entre 0 e 10."
            );
        }

        this.nota = nota;
    }

    public String getTextoCritica() {
        return textoCritica;
    }

    public void setTextoCritica(String textoCritica) {
        this.textoCritica = textoCritica;
    }

    public LocalDate getDataPostagem() {
        return dataPostagem;
    }

    public void setDataPostagem(LocalDate dataPostagem) {
        this.dataPostagem = dataPostagem;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {

        if (usuario == null) {
            throw new IllegalArgumentException(
                "O usuário não pode ser nulo."
            );
        }

        this.usuario = usuario;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {

        if (jogo == null) {
            throw new IllegalArgumentException(
                "O jogo não pode ser nulo."
            );
        }

        this.jogo = jogo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAvaliacao);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Avaliacao other = (Avaliacao) obj;

        return Objects.equals(idAvaliacao, other.idAvaliacao);
    }
}