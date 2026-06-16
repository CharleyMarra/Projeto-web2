package br.ifg.urt.gamercatalog_api.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "comentarios")
public class Comentarios implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id // Define a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComentario;

    @Column(nullable = false, length = 1000)
    private String texto;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "jogo_id", nullable = false)
    private Jogo jogo;

    // Construtor padrão obrigatório para JPA
    public Comentarios() {
    }

    public Comentarios(Long idComentario, String texto,
                       LocalDateTime dataHora,
                       Usuario usuario, Jogo jogo) {

        this.idComentario = idComentario;
        this.texto = texto;
        this.dataHora = dataHora;
        this.usuario = usuario;
        this.jogo = jogo;
    }

    public Long getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(Long idComentario) {
        this.idComentario = idComentario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {

        // Validação
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException(
                    "O comentário não pode estar vazio."
            );
        }

        this.texto = texto;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {

        // Validação
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

        // Validação
        if (jogo == null) {
            throw new IllegalArgumentException(
                    "O jogo não pode ser nulo."
            );
        }

        this.jogo = jogo;
    }

    public void editarComentario(String novoTexto) {

        if (novoTexto == null || novoTexto.isBlank()) {
            throw new IllegalArgumentException(
                    "O comentário não pode estar vazio."
            );
        }

        this.texto = novoTexto;
    }
}