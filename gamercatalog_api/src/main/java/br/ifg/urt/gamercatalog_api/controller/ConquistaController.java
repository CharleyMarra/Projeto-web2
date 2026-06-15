package br.ifg.urt.gamercatalog_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import br.ifg.urt.gamercatalog_api.dto.request.ConquistaRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.ConquistaResponseDTO;
import br.ifg.urt.gamercatalog_api.service.ConquistaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@RestController
@RequestMapping("/conquistas")
@Tag(name = "Conquistas", description = "Endpoints para gerenciamento de Conquistas")
public class ConquistaController {

    private final ConquistaService service;

    public ConquistaController(ConquistaService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Listar conquistas paginadas e com filtro")
    public ResponseEntity<Page<ConquistaResponseDTO>> buscarTodos(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Long usuarioId,
            @PageableDefault(size = 10, sort = "titulo") Pageable pageable) {

        if (usuarioId != null) {
            return ResponseEntity.ok(service.findByUsuario(usuarioId, pageable));
        }
        return ResponseEntity.ok(service.findAll(titulo, pageable));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Buscar por ID", description = "Retorna os detalhes de um registro específico de conquistas através do seu id único.", responses = {
            @ApiResponse(description = "Sucesso", responseCode = "200", content = @Content(schema = @Schema(implementation = ConquistaResponseDTO.class))),
            @ApiResponse(description = "Não encontrado", responseCode = "404", content = @Content),
            @ApiResponse(description = "ID inválido", responseCode = "400", content = @Content)
    })
    public ResponseEntity<ConquistaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Criar novo registro", description = "Cadastra um novo registro de conquistas no sistema e retorna o objeto criado.", responses = {
            @ApiResponse(description = "Criado com sucesso", responseCode = "201", content = @Content(schema = @Schema(implementation = ConquistaResponseDTO.class))),
            @ApiResponse(description = "Erro de validação", responseCode = "400", content = @Content)
    })
    public ResponseEntity<ConquistaResponseDTO> criar(@Valid @RequestBody ConquistaRequestDTO dto) {
        ConquistaResponseDTO novaConquista = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaConquista);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Atualizar registro", description = "Atualiza todos os dados de um registro existente de conquistas.", responses = {
            @ApiResponse(description = "Atualizado com sucesso", responseCode = "200", content = @Content(schema = @Schema(implementation = ConquistaResponseDTO.class))),
            @ApiResponse(description = "Não encontrado", responseCode = "404", content = @Content),
            @ApiResponse(description = "Dados inválidos", responseCode = "400", content = @Content)
    })
    public ResponseEntity<ConquistaResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ConquistaRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir registro", description = "Remove um registro de conquistas do sistema pelo seu ID.", responses = {
            @ApiResponse(description = "Excluído com sucesso", responseCode = "204", content = @Content),
            @ApiResponse(description = "Não encontrado", responseCode = "404", content = @Content)
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}