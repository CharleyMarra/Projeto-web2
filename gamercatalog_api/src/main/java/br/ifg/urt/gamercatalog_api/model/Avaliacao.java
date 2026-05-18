package br.ifg.urt.gamercatalog_api.model;

import java.io.Serializable;
import java.util.Objects;

public class Avaliacao implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String usuario;
    private Integer nota;
    private String descricao;

    public Avaliacao() {
    }

    public Avaliacao(Long id, String usuario, Integer nota, String descricao) {
        this.id = id;
        this.usuario = usuario;
        this.nota = nota;
        this.descricao = descricao;
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

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        if (nota < 1 || nota > 5) {
            throw new IllegalArgumentException("A nota deve estar entre 1 e 5.");
        }
        this.nota = nota;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

        Avaliacao other = (Avaliacao) obj;
        return Objects.equals(id, other.id);
    }
}