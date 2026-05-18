package br.ifg.urt.gamercatalog_api.model;

import java.io.Serializable;
import java.util.Objects;

public class Publisher implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private String sede;

    public Publisher() {
    }

    public Publisher(Long id, String nome, String sede) {
        this.id = id;
        this.nome = nome;
        this.sede = sede;
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

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
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

        Publisher other = (Publisher) obj;
        return Objects.equals(id, other.id);
    }
}
