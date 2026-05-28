package br.ifg.urt.gamercatalog_api.model;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity // Indica que esta classe é uma tabela no banco
@Table(name = "publishers")
public class Publisher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id // Define a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 150)
    private String sede;

    // Relacionamento:
    // Um publisher possui vários jogos
    @JsonIgnore
    @OneToMany(mappedBy = "publisher")
    private List<Jogo> jogos;

    // Construtor padrão obrigatório para JPA
    public Publisher() {
    }

    public Publisher(Long id,
                     String nome,
                     String sede) {

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

    public List<Jogo> getJogos() {
        return jogos;
    }

    public void setJogos(List<Jogo> jogos) {
        this.jogos = jogos;
    }
}