package br.ifg.urt.gamercatalog_api.model;

import java.io.Serializable;
import java.util.Objects;

public class Estudio implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private String pais;

    public Estudio() {
    }

    public Estudio(Long id, String nome, String pais) {
        this.id = id;
        this.nome = nome;
        this.pais = pais;
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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
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

        Estudio other = (Estudio) obj;
        return Objects.equals(id, other.id);
    }
}
