package br.ifg.urt.gamercatalog_api.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.ifg.urt.gamercatalog_api.model.Plataforma;
import br.ifg.urt.gamercatalog_api.service.PlataformaService;

@RestController
@RequestMapping("/plataformas")
public class PlataformaController {

    private final PlataformaService service;

    public PlataformaController(PlataformaService service) {
        this.service = service;
    }

    // 200 OK - Padrão para listagens
    @GetMapping
    public ResponseEntity<List<Plataforma>> buscarTodos() {

        List<Plataforma> plataformas = service.findAll();

        return ResponseEntity.ok(plataformas);
    }

    // 200 OK - Padrão para busca individual
    @GetMapping("/{id}")
    public ResponseEntity<Plataforma> buscarPorId(
            @PathVariable Long id) {

        Plataforma plataforma = service.findById(id);

        return ResponseEntity.ok(plataforma);
    }

    // 201 Created - Criação de recurso
    @PostMapping
    public ResponseEntity<Plataforma> criar(
            @RequestBody Plataforma plataforma) {

        Plataforma novaPlataforma =
                service.create(plataforma);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(novaPlataforma);
    }

    // 200 OK - Atualização de recurso
    @PutMapping("/{id}")
    public ResponseEntity<Plataforma> atualizar(
            @PathVariable Long id,
            @RequestBody Plataforma plataforma) {

        plataforma.setId(id);

        Plataforma plataformaAtualizada =
                service.update(plataforma);

        return ResponseEntity.ok(
                plataformaAtualizada
        );
    }

    // 204 No Content - Remoção
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
