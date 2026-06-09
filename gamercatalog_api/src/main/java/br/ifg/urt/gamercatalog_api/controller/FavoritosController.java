package br.ifg.urt.gamercatalog_api.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.ifg.urt.gamercatalog_api.model.Favoritos;
import br.ifg.urt.gamercatalog_api.service.FavoritosService;

@RestController
@RequestMapping("/favoritos")
public class FavoritosController {

    private final FavoritosService service;

    public FavoritosController(FavoritosService service) {
        this.service = service;
    }

    // 200 OK - Padrão para listagens (Aceita o filtro opcional ?usuarioId=X)
    @GetMapping
    public ResponseEntity<List<Favoritos>> buscarTodos(
            @RequestParam(required = false) Long usuarioId) {

        if (usuarioId != null) {
            List<Favoritos> favoritosPorUsuario = service.findByUsuario(usuarioId);
            return ResponseEntity.ok(favoritosPorUsuario);
        }

        List<Favoritos> favoritos = service.findAll();

        return ResponseEntity.ok(favoritos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Favoritos> buscarPorId(@PathVariable Long id) {
        Favoritos favorito = service.findById(id);
        return ResponseEntity.ok(favorito);
    }

    @PostMapping
    public ResponseEntity<Favoritos> criar(@RequestBody Favoritos favorito) {
        Favoritos novoFavorito = service.create(favorito);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoFavorito);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Favoritos> atualizar(@PathVariable Long id, @RequestBody Favoritos favorito) {
        favorito.setIdFavorito(id);
        Favoritos favoritoAtualizado = service.update(favorito);
        return ResponseEntity.ok(favoritoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}