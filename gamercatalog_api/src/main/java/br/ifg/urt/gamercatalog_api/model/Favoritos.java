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

@Entity
@Table(name = "favoritos")
public class Favoritos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFavorito;

    @Column(nullable = false)
    private LocalDate dataAdicionado;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

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

    public void adicionarFavorito() {

        if (this.dataAdicionado == null) {
            this.dataAdicionado = LocalDate.now();
        }
    }
}