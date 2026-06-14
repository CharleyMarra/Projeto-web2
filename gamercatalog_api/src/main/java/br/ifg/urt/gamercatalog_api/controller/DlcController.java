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
import br.ifg.urt.gamercatalog_api.dto.request.DlcRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.DlcResponseDTO;
import br.ifg.urt.gamercatalog_api.service.DlcService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/dlcs")
@Tag(name = "Dlcs", description = "Endpoints para gerenciamento de Dlcs")
public class DlcController {

    private final DlcService service;

    public DlcController(DlcService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Listar todos",
        description = "Retorna uma lista com todos os registros de dlcs cadastrados.",
        responses = {
            @ApiResponse(description = "Sucesso", responseCode = "200",
                         content = @Content(schema = @Schema(implementation = DlcResponseDTO.class))),
            @ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)
        }
    )
    public ResponseEntity<List<DlcResponseDTO>> buscarTodos(
            @RequestParam(required = false) Long jogoId) {

        if (jogoId != null) {
            return ResponseEntity.ok(service.findByJogo(jogoId));
        }
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Buscar por ID",
        description = "Retorna os detalhes de um registro específico de dlcs através do seu id único.",
        responses = {
            @ApiResponse(description = "Sucesso", responseCode = "200",
                         content = @Content(schema = @Schema(implementation = DlcResponseDTO.class))),
            @ApiResponse(description = "Não encontrado", responseCode = "404", content = @Content),
            @ApiResponse(description = "ID inválido", responseCode = "400", content = @Content)
        }
    )
    public ResponseEntity<DlcResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Criar novo registro",
        description = "Cadastra um novo registro de dlcs no sistema e retorna o objeto criado.",
        responses = {
            @ApiResponse(description = "Criado com sucesso", responseCode = "201",
                         content = @Content(schema = @Schema(implementation = DlcResponseDTO.class))),
            @ApiResponse(description = "Erro de validação", responseCode = "400", content = @Content)
        }
    )
    public ResponseEntity<DlcResponseDTO> criar(@Valid @RequestBody DlcRequestDTO dto) {
        DlcResponseDTO novaDlc = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaDlc);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Atualizar registro",
        description = "Atualiza todos os dados de um registro existente de dlcs.",
        responses = {
            @ApiResponse(description = "Atualizado com sucesso", responseCode = "200",
                         content = @Content(schema = @Schema(implementation = DlcResponseDTO.class))),
            @ApiResponse(description = "Não encontrado", responseCode = "404", content = @Content),
            @ApiResponse(description = "Dados inválidos", responseCode = "400", content = @Content)
        }
    )
    public ResponseEntity<DlcResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody DlcRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Excluir registro",
        description = "Remove um registro de dlcs do sistema pelo seu ID.",
        responses = {
            @ApiResponse(description = "Excluído com sucesso", responseCode = "204", content = @Content),
            @ApiResponse(description = "Não encontrado", responseCode = "404", content = @Content)
        }
    )
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}