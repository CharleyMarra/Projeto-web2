package br.ifg.urt.gamercatalog_api.model;

import java.io.Serializable;
import java.util.Objects;

public class Comentario implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String usuario;
    private String texto;

    public Comentario() {
    }

    public Comentario(Long id, String usuario, String texto) {
        this.id = id;
        this.usuario = usuario;
        this.texto = texto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("O comentário não pode ser vazio.");
        }
        this.texto = texto;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Comentario other = (Comentario) obj;
        return Objects.equals(id, other.id);
    }
}