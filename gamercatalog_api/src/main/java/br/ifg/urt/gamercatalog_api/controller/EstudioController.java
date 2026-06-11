package br.ifg.urt.gamercatalog_api.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.ifg.urt.gamercatalog_api.dto.request.EstudioRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.EstudioResponseDTO;
import br.ifg.urt.gamercatalog_api.service.EstudioService;

@RestController
@RequestMapping("/estudios")
public class EstudioController {

    private final EstudioService service;

    public EstudioController(EstudioService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<EstudioResponseDTO>> buscarTodos() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstudioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<EstudioResponseDTO> criar(@RequestBody EstudioRequestDTO dto) {
        EstudioResponseDTO novoEstudio = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEstudio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstudioResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody EstudioRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}