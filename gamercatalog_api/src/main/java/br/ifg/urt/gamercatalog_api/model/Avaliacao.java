package br.ifg.urt.gamercatalog_api.model;

import java.io.Serializable;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "avaliacoes")
public class Avaliacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAvaliacao;

    @Column(nullable = false)
    private Integer nota;

    @Column(length = 1000)
    private String textoCritica;

    @Column(nullable = false)
    private LocalDate dataPostagem;

    // Muitos avaliações para um usuário
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Muitas avaliações para um jogo
    @ManyToOne
    @JoinColumn(name = "jogo_id", nullable = false)
    private Jogo jogo;

    // Construtor padrão obrigatório para JPA
    public Avaliacao() {
    }

    public Avaliacao(Long idAvaliacao, Integer nota,
                      String textoCritica,
                      LocalDate dataPostagem,
                      Usuario usuario,
                      Jogo jogo) {

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

    /**
     * Regra de negócio
     */
    public void alterarNota(Integer novaNota) {

        if (novaNota == null || novaNota < 0 || novaNota > 10) {
            throw new IllegalArgumentException(
                    "A nota deve estar entre 0 e 10."
            );
        }

        this.nota = novaNota;
    }
}
