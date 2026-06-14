package br.ifg.urt.gamercatalog_api.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.ifg.urt.gamercatalog_api.dto.request.AvaliacaoRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.AvaliacaoResponseDTO;
import br.ifg.urt.gamercatalog_api.service.AvaliacaoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService service;

    public AvaliacaoController(AvaliacaoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AvaliacaoResponseDTO>> buscarTodos(
            @RequestParam(required = false) Long jogoId) {

        if (jogoId != null) {
            return ResponseEntity.ok(service.findByJogo(jogoId));
        }
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<AvaliacaoResponseDTO> criar(@Valid @RequestBody AvaliacaoRequestDTO dto) {
        AvaliacaoResponseDTO novaAvaliacao = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaAvaliacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AvaliacaoRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}