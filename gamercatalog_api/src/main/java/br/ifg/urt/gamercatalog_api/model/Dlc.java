package br.ifg.urt.gamercatalog_api.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "dlcs")
public class Dlc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false)
    private Double preco;

    // Construtor padrão obrigatório para JPA
    public Dlc() {
    }

    public Dlc(Long id, String nome, Double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
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

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    // Método de regra de negócio
    public void alterarPreco(Double novoPreco) {

        // Validação
        if (novoPreco == null || novoPreco <= 0) {
            throw new IllegalArgumentException(
                    "O preço deve ser maior que zero."
            );
        }

        // Atualização do estado
        this.preco = novoPreco;
    }
}