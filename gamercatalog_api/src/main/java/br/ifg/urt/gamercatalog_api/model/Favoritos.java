package br.ifg.urt.gamercatalog_api.model;

import java.io.Serializable;
import java.util.Objects;

public class Favorito implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String usuario;
    private Produto produto;

    public Favorito() {
    }

    public Favorito(Long id, String usuario, Produto produto) {
        this.id = id;
        this.usuario = usuario;
        this.produto = produto;
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

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("O produto favorito não pode ser nulo.");
        }
        this.produto = produto;
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

        Favorito other = (Favorito) obj;
        return Objects.equals(id, other.id);
    }
}