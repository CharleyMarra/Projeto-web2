package br.ifg.urt.gamercatalog_api.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Comentarios implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idComentario;
    private String texto;
    private LocalDateTime dataHora;

    private Usuario usuario;
    private Jogo jogo;

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
        return Objects.hash(idComentario);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Comentarios other = (Comentarios) obj;

        return Objects.equals(idComentario, other.idComentario);
    }
}