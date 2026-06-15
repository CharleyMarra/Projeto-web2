package br.ifg.urt.gamercatalog_api.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import br.ifg.urt.gamercatalog_api.dto.request.ComentariosRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.ComentariosResponseDTO;
import br.ifg.urt.gamercatalog_api.service.ComentariosService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@RestController
@RequestMapping("/comentarios")
@Tag(name = "Comentários", description = "Endpoints para gerenciamento de Comentários")
public class ComentariosController {

    private final ComentariosService service;

    public ComentariosController(ComentariosService service) {
        this.service = service;
    }

    // Retorna a lista enxuta de ResponseDTO (Aceita o filtro opcional ?jogoId=X)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Listar comentários paginados e com filtro")
    public ResponseEntity<Page<ComentariosResponseDTO>> buscarTodos(
            @RequestParam(required = false) String texto,
            @RequestParam(required = false) Long jogoId,
            @PageableDefault(size = 10, sort = "dataHora") Pageable pageable) { // Ordenação sugerida por data

        if (jogoId != null) {
            return ResponseEntity.ok(service.findByJogo(jogoId, pageable));
        }
        return ResponseEntity.ok(service.findAll(texto, pageable));
    }

    // Retorna a resposta enxuta de um único comentário por ID
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Buscar por ID", description = "Retorna os detalhes de um registro específico de comentários através do seu id único.", responses = {
            @ApiResponse(description = "Sucesso", responseCode = "200", content = @Content(schema = @Schema(implementation = ComentariosResponseDTO.class))),
            @ApiResponse(description = "Não encontrado", responseCode = "404", content = @Content),
            @ApiResponse(description = "ID inválido", responseCode = "400", content = @Content)
    })
    public ResponseEntity<ComentariosResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.findById(id));
    }

    // Recebe RequestDTO no corpo e devolve um ResponseDTO
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Criar novo registro", description = "Cadastra um novo registro de comentários no sistema e retorna o objeto criado.", responses = {
            @ApiResponse(description = "Criado com sucesso", responseCode = "201", content = @Content(schema = @Schema(implementation = ComentariosResponseDTO.class))),
            @ApiResponse(description = "Erro de validação", responseCode = "400", content = @Content)
    })
    public ResponseEntity<ComentariosResponseDTO> criar(
            @Valid @RequestBody ComentariosRequestDTO dto) {

        ComentariosResponseDTO novoComentario = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoComentario);
    }

    // Atualiza recebendo os novos dados estruturados em DTO
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Atualizar registro", description = "Atualiza todos os dados de um registro existente de comentários.", responses = {
            @ApiResponse(description = "Atualizado com sucesso", responseCode = "200", content = @Content(schema = @Schema(implementation = ComentariosResponseDTO.class))),
            @ApiResponse(description = "Não encontrado", responseCode = "404", content = @Content),
            @ApiResponse(description = "Dados inválidos", responseCode = "400", content = @Content)
    })
    public ResponseEntity<ComentariosResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ComentariosRequestDTO dto) {

        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir registro", description = "Remove um registro de comentários do sistema pelo seu ID.", responses = {
            @ApiResponse(description = "Excluído com sucesso", responseCode = "204", content = @Content),
            @ApiResponse(description = "Não encontrado", responseCode = "404", content = @Content)
    })
    public ResponseEntity<Void> deletar(
            @PathVariable Long id) {

        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}