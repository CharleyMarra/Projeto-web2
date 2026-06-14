package br.ifg.urt.gamercatalog_api.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.ifg.urt.gamercatalog_api.dto.request.FavoritosRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.FavoritosResponseDTO;
import br.ifg.urt.gamercatalog_api.service.FavoritosService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/favoritos")
public class FavoritosController {

    private final FavoritosService service;

    public FavoritosController(FavoritosService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<FavoritosResponseDTO>> buscarTodos(
            @RequestParam(required = false) Long usuarioId) {

        if (usuarioId != null) {
            return ResponseEntity.ok(service.findByUsuario(usuarioId));
        }
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FavoritosResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<FavoritosResponseDTO> criar(@Valid @RequestBody FavoritosRequestDTO dto) {
        FavoritosResponseDTO novoFavorito = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoFavorito);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}