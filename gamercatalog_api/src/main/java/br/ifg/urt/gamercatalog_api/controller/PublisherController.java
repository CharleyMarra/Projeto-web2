package br.ifg.urt.gamercatalog_api.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.ifg.urt.gamercatalog_api.dto.request.PublisherRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.PublisherResponseDTO;
import br.ifg.urt.gamercatalog_api.service.PublisherService;

@RestController
@RequestMapping("/publishers")
public class PublisherController {

    private final PublisherService service;

    public PublisherController(PublisherService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PublisherResponseDTO>> buscarTodos() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<PublisherResponseDTO> criar(@RequestBody PublisherRequestDTO dto) {
        PublisherResponseDTO novoPublisher = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPublisher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublisherResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody PublisherRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}