package br.ifg.urt.gamercatalog_api.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.ifg.urt.gamercatalog_api.dto.request.DlcRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.DlcResponseDTO;
import br.ifg.urt.gamercatalog_api.service.DlcService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/dlcs")
public class DlcController {

    private final DlcService service;

    public DlcController(DlcService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<DlcResponseDTO>> buscarTodos(
            @RequestParam(required = false) Long jogoId) {

        if (jogoId != null) {
            return ResponseEntity.ok(service.findByJogo(jogoId));
        }
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DlcResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<DlcResponseDTO> criar(@Valid @RequestBody DlcRequestDTO dto) {
        DlcResponseDTO novaDlc = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaDlc);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DlcResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody DlcRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}