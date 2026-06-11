package br.ifg.urt.gamercatalog_api.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.ifg.urt.gamercatalog_api.dto.request.JogoRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.JogoResponseDTO;
import br.ifg.urt.gamercatalog_api.service.JogoService;

@RestController
@RequestMapping("/jogos")
public class JogoController {

    private final JogoService service;

    public JogoController(JogoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<JogoResponseDTO>> buscarTodos() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JogoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<JogoResponseDTO> criar(@RequestBody JogoRequestDTO dto) {
        JogoResponseDTO novoJogo = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoJogo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JogoResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody JogoRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}