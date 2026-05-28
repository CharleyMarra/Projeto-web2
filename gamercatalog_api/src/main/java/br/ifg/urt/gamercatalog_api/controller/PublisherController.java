package br.ifg.urt.gamercatalog_api.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.ifg.urt.gamercatalog_api.model.Publisher;
import br.ifg.urt.gamercatalog_api.service.PublisherService;

@RestController
@RequestMapping("/publishers")
public class PublisherController {

    private final PublisherService service;

    public PublisherController(PublisherService service) {
        this.service = service;
    }

    // 200 OK - Padrão para listagens
    @GetMapping
    public ResponseEntity<List<Publisher>> buscarTodos() {

        List<Publisher> publishers = service.findAll();

        return ResponseEntity.ok(publishers);
    }

    // 200 OK - Padrão para busca individual
    @GetMapping("/{id}")
    public ResponseEntity<Publisher> buscarPorId(
            @PathVariable Long id) {

        Publisher publisher = service.findById(id);

        return ResponseEntity.ok(publisher);
    }

    // 201 Created - Criação de recurso
    @PostMapping
    public ResponseEntity<Publisher> criar(
            @RequestBody Publisher publisher) {

        Publisher novoPublisher =
                service.create(publisher);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(novoPublisher);
    }

    // 200 OK - Atualização de recurso
    @PutMapping("/{id}")
    public ResponseEntity<Publisher> atualizar(
            @PathVariable Long id,
            @RequestBody Publisher publisher) {

        publisher.setId(id);

        Publisher publisherAtualizado =
                service.update(publisher);

        return ResponseEntity.ok(
                publisherAtualizado
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
