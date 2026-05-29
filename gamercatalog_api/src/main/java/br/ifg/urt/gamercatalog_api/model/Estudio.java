package br.ifg.urt.gamercatalog_api.model;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity // Indica que esta classe é uma tabela no banco
@Table(name = "estudios")
public class Estudio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id // Define a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 100)
    private String pais;

    // Relacionamento:
    // Um estúdio pode desenvolver vários jogos
    @JsonIgnore
    @OneToMany(mappedBy = "estudio")
    private List<Jogo> jogos;

    // Construtor padrão obrigatório para JPA
    public Estudio() {
    }

    public Estudio(Long id,
                   String nome,
                   String pais) {

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

    public List<Jogo> getJogos() {
        return jogos;
    }

    public void setJogos(List<Jogo> jogos) {
        this.jogos = jogos;
    }
}