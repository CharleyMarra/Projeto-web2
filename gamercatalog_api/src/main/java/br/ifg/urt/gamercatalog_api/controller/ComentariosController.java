package br.ifg.urt.gamercatalog_api.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.ifg.urt.gamercatalog_api.dto.request.ComentariosRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.ComentariosResponseDTO;
import br.ifg.urt.gamercatalog_api.service.ComentariosService;

@RestController
@RequestMapping("/comentarios")
public class ComentariosController {

    private final ComentariosService service;

    public ComentariosController(ComentariosService service) {
        this.service = service;
    }

    // Retorna a lista enxuta de ResponseDTO (Aceita o filtro opcional ?jogoId=X)
    @GetMapping
    public ResponseEntity<List<ComentariosResponseDTO>> buscarTodos(
            @RequestParam(required = false) Long jogoId) {

        if (jogoId != null) {
            return ResponseEntity.ok(service.findByJogo(jogoId));
        }

        return ResponseEntity.ok(service.findAll());
    }

    // Retorna a resposta enxuta de um único comentário por ID
    @GetMapping("/{id}")
    public ResponseEntity<ComentariosResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.findById(id));
    }

    // Recebe RequestDTO no corpo e devolve um ResponseDTO
    @PostMapping
    public ResponseEntity<ComentariosResponseDTO> criar(
            @RequestBody ComentariosRequestDTO dto) {

        ComentariosResponseDTO novoComentario = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoComentario);
    }

    // Atualiza recebendo os novos dados estruturados em DTO
    @PutMapping("/{id}")
    public ResponseEntity<ComentariosResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody ComentariosRequestDTO dto) {

        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id) {

        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}