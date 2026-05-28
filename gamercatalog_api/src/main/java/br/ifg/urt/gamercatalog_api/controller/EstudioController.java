package br.ifg.urt.gamercatalog_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ifg.urt.gamercatalog_api.model.Estudio;
import br.ifg.urt.gamercatalog_api.service.EstudioService;

@RestController
@RequestMapping("/estudios")
public class EstudioController {

    private final EstudioService service;

    public EstudioController(EstudioService service) {
        this.service = service;
    }

    // 200 OK - Padrão para listagens
    @GetMapping
    public ResponseEntity<List<Estudio>> buscarTodos() {

        List<Estudio> estudios = service.findAll();

        return ResponseEntity.ok(estudios);
    }

    // 200 OK - Padrão para busca individual
    @GetMapping("/{id}")
    public ResponseEntity<Estudio> buscarPorId(
            @PathVariable Long id) {

        Estudio estudio = service.findById(id);

        return ResponseEntity.ok(estudio);
    }

    // 201 Created - Criação de recurso
    @PostMapping
    public ResponseEntity<Estudio> criar(
            @RequestBody Estudio estudio) {

        Estudio novoEstudio =
                service.create(estudio);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(novoEstudio);
    }

    // 200 OK - Atualização de recurso
    @PutMapping("/{id}")
    public ResponseEntity<Estudio> atualizar(
            @PathVariable Long id,
            @RequestBody Estudio estudio) {

        estudio.setId(id);

        Estudio estudioAtualizado =
                service.update(estudio);

        return ResponseEntity.ok(
                estudioAtualizado
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
