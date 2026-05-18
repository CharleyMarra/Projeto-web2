package br.ifg.urt.gamercatalog_api.model;

import java.io.Serializable;
import java.util.Objects;

public class Jogo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String titulo;
    private String descricao;
    private Double preco;
    private String genero;
    private Integer classificacaoIndicativa;

    public Jogo() {
    }

    public Jogo(Long id, String titulo, String descricao,
                 Double preco, String genero,
                 Integer classificacaoIndicativa) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.preco = preco;
        this.genero = genero;
        this.classificacaoIndicativa = classificacaoIndicativa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getClassificacaoIndicativa() {
        return classificacaoIndicativa;
    }

    public void setClassificacaoIndicativa(Integer classificacaoIndicativa) {
        this.classificacaoIndicativa = classificacaoIndicativa;
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

        Jogo other = (Jogo) obj;
        return Objects.equals(id, other.id);
    }
}
