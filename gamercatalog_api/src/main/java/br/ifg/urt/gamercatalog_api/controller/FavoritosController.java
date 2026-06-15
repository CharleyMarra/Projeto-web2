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
import br.ifg.urt.gamercatalog_api.dto.request.FavoritosRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.FavoritosResponseDTO;
import br.ifg.urt.gamercatalog_api.service.FavoritosService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@RestController
@RequestMapping("/favoritos")
@Tag(name = "Favoritos", description = "Endpoints para gerenciamento de Favoritos")
public class FavoritosController {

    private final FavoritosService service;

    public FavoritosController(FavoritosService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Listar favoritos paginados")
    public ResponseEntity<Page<FavoritosResponseDTO>> buscarTodos(
            @RequestParam(required = false) Long usuarioId,
            @PageableDefault(size = 10, sort = "dataAdicionado") Pageable pageable) {

        if (usuarioId != null) {
            return ResponseEntity.ok(service.findByUsuario(usuarioId, pageable));
        }
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Buscar por ID", description = "Retorna os detalhes de um registro específico de favoritos através do seu id único.", responses = {
            @ApiResponse(description = "Sucesso", responseCode = "200", content = @Content(schema = @Schema(implementation = FavoritosResponseDTO.class))),
            @ApiResponse(description = "Não encontrado", responseCode = "404", content = @Content),
            @ApiResponse(description = "ID inválido", responseCode = "400", content = @Content)
    })
    public ResponseEntity<FavoritosResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Criar novo registro", description = "Cadastra um novo registro de favoritos no sistema e retorna o objeto criado.", responses = {
            @ApiResponse(description = "Criado com sucesso", responseCode = "201", content = @Content(schema = @Schema(implementation = FavoritosResponseDTO.class))),
            @ApiResponse(description = "Erro de validação", responseCode = "400", content = @Content)
    })
    public ResponseEntity<FavoritosResponseDTO> criar(@Valid @RequestBody FavoritosRequestDTO dto) {
        FavoritosResponseDTO novoFavorito = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoFavorito);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir registro", description = "Remove um registro de favoritos do sistema pelo seu ID.", responses = {
            @ApiResponse(description = "Excluído com sucesso", responseCode = "204", content = @Content),
            @ApiResponse(description = "Não encontrado", responseCode = "404", content = @Content)
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}