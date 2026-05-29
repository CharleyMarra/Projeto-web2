package br.ifg.urt.gamercatalog_api.model;

import java.io.Serializable;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity // Indica que esta classe é uma tabela no banco de dados
@Table(name = "favoritos") // Nome da tabela
public class Favoritos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id // Define a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremento
    private Long idFavorito;

    @Column(nullable = false)
    private LocalDate dataAdicionado;

    // Muitos favoritos podem pertencer a um usuário
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Muitos favoritos podem referenciar um jogo
    @ManyToOne
    @JoinColumn(name = "jogo_id", nullable = false)
    private Jogo jogo;

    // Construtor padrão obrigatório para JPA
    public Favoritos() {
    }

    public Favoritos(Long idFavorito,
                      LocalDate dataAdicionado,
                      Usuario usuario,
                      Jogo jogo) {

        this.idFavorito = idFavorito;
        this.dataAdicionado = dataAdicionado;
        this.usuario = usuario;
        this.jogo = jogo;
    }

    public Long getIdFavorito() {
        return idFavorito;
    }

    public void setIdFavorito(Long idFavorito) {
        this.idFavorito = idFavorito;
    }

    public LocalDate getDataAdicionado() {
        return dataAdicionado;
    }

    public void setDataAdicionado(LocalDate dataAdicionado) {
        this.dataAdicionado = dataAdicionado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {

        if (usuario == null) {
            throw new IllegalArgumentException(
                    "O usuário não pode ser nulo."
            );
        }

        this.usuario = usuario;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {

        if (jogo == null) {
            throw new IllegalArgumentException(
                    "O jogo não pode ser nulo."
            );
        }

        this.jogo = jogo;
    }

    // Método de regra de negócio
    public void adicionarFavorito() {

        if (this.dataAdicionado == null) {
            this.dataAdicionado = LocalDate.now();
        }
    }
}