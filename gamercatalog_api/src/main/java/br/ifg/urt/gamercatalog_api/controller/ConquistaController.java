package br.ifg.urt.gamercatalog_api.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.ifg.urt.gamercatalog_api.dto.request.ConquistaRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.ConquistaResponseDTO;
import br.ifg.urt.gamercatalog_api.service.ConquistaService;

@RestController
@RequestMapping("/conquistas")
public class ConquistaController {

    private final ConquistaService service;

    public ConquistaController(ConquistaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ConquistaResponseDTO>> buscarTodos(
            @RequestParam(required = false) Long usuarioId) {

        if (usuarioId != null) {
            return ResponseEntity.ok(service.findByUsuario(usuarioId));
        }
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConquistaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ConquistaResponseDTO> criar(@RequestBody ConquistaRequestDTO dto) {
        ConquistaResponseDTO novaConquista = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaConquista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConquistaResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody ConquistaRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}