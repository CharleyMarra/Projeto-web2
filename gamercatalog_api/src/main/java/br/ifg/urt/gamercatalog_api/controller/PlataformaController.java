package br.ifg.urt.gamercatalog_api.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.ifg.urt.gamercatalog_api.dto.request.PlataformaRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.PlataformaResponseDTO;
import br.ifg.urt.gamercatalog_api.service.PlataformaService;

@RestController
@RequestMapping("/plataformas")
public class PlataformaController {

    private final PlataformaService service;

    public PlataformaController(PlataformaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PlataformaResponseDTO>> buscarTodos() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlataformaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<PlataformaResponseDTO> criar(@RequestBody PlataformaRequestDTO dto) {
        PlataformaResponseDTO novaPlataforma = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaPlataforma);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlataformaResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody PlataformaRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}