package br.ifg.urt.gamercatalog_api.model;

import java.io.Serializable;
import java.util.Objects;

public class Plataforma implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private String fabricante;

    public Plataforma() {
    }

    public Plataforma(Long id, String nome, String fabricante) {
        this.id = id;
        this.nome = nome;
        this.fabricante = fabricante;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
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

        Plataforma other = (Plataforma) obj;
        return Objects.equals(id, other.id);
    }
}
