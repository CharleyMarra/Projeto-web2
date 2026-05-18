package br.ifg.urt.gamercatalog_api.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Favoritos implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idFavorito;
    private LocalDate dataAdicionado;

    private Usuario usuario;
    private Jogo jogo;

    public Favoritos() {
    }

    public Favoritos(Long idFavorito, LocalDate dataAdicionado,
                      Usuario usuario, Jogo jogo) {

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

    @Override
    public int hashCode() {
        return Objects.hash(idFavorito);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Favoritos other = (Favoritos) obj;

        return Objects.equals(idFavorito, other.idFavorito);
    }
}