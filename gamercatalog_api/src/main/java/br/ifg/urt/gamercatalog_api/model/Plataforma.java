package br.ifg.urt.gamercatalog_api.model;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity // Indica que esta classe é uma tabela no banco
@Table(name = "plataformas")
public class Plataforma implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id // Chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 100)
    private String fabricante;

    // Relacionamento:
    // Uma plataforma possui vários jogos
    @JsonIgnore
    @OneToMany(mappedBy = "plataforma")
    private List<Jogo> jogos;

    // Construtor padrão obrigatório
    public Plataforma() {
    }

    public Plataforma(Long id,
                      String nome,
                      String fabricante) {

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

    public List<Jogo> getJogos() {
        return jogos;
    }

    public void setJogos(List<Jogo> jogos) {
        this.jogos = jogos;
    }
}