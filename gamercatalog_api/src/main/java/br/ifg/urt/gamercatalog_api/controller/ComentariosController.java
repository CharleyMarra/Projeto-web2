package br.ifg.urt.gamercatalog_api.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.ifg.urt.gamercatalog_api.model.Comentarios;
import br.ifg.urt.gamercatalog_api.service.ComentariosService;

@RestController
@RequestMapping("/comentarios")
public class ComentariosController {

    private final ComentariosService service;

    public ComentariosController(ComentariosService service) {
        this.service = service;
    }

    // 200 OK - Padrão para listagens
    @GetMapping
    public ResponseEntity<List<Comentarios>> buscarTodos() {

        List<Comentarios> comentarios = service.findAll();

        return ResponseEntity.ok(comentarios);
    }

    // 200 OK - Padrão para busca individual bem-sucedida
    @GetMapping("/{id}")
    public ResponseEntity<Comentarios> buscarPorId(
            @PathVariable Long id) {

        Comentarios comentario = service.findById(id);

        return ResponseEntity.ok(comentario);
    }

    // 201 Created - Padrão para criação de recursos
    @PostMapping
    public ResponseEntity<Comentarios> criar(
            @RequestBody Comentarios comentario) {

        Comentarios novoComentario =
                service.create(comentario);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(novoComentario);
    }

    // 200 OK - Recurso atualizado com sucesso
    @PutMapping("/{id}")
    public ResponseEntity<Comentarios> atualizar(
            @PathVariable Long id,
            @RequestBody Comentarios comentario) {

        comentario.setIdComentario(id);

        Comentarios comentarioAtualizado =
                service.update(comentario);

        return ResponseEntity.ok(comentarioAtualizado);
    }

    // 204 No Content - Padrão para remoção
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}