package br.ifg.urt.gamercatalog_api.model;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity // Indica que esta classe é uma tabela no banco de dados
@Table(name = "jogos") // Nome da tabela
public class Jogo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id // Define a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremento
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(length = 500)
    private String descricao;

    @Column(nullable = false)
    private Double preco;

    @Column(nullable = false, length = 100)
    private String genero;

    @Column(nullable = false)
    private Integer classificacaoIndicativa;

    @ManyToOne
    @JoinColumn(name = "plataforma_id")
    private Plataforma plataforma;

    @ManyToOne
    @JoinColumn(name = "estudio_id")
    private Estudio estudio;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @OneToMany(mappedBy = "jogo")
    private List<Avaliacao> avaliacoes;

    @OneToMany(mappedBy = "jogo")
    private List<Comentarios> comentarios;

    @OneToMany(mappedBy = "jogo")
    private List<Dlc> dlcs;

    @OneToMany(mappedBy = "jogo")
    private List<Conquista> conquistas;

    @OneToMany(mappedBy = "jogo")
    private List<Favoritos> favoritos;

    // O construtor padrão é obrigatório para o JPA
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
