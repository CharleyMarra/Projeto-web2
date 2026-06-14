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
import br.ifg.urt.gamercatalog_api.dto.request.PublisherRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.PublisherResponseDTO;
import br.ifg.urt.gamercatalog_api.service.PublisherService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/publishers")
@Tag(name = "Publishers", description = "Endpoints para gerenciamento de Publishers")
public class PublisherController {

    private final PublisherService service;

    public PublisherController(PublisherService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Listar todos",
        description = "Retorna uma lista com todos os registros de publishers cadastrados.",
        responses = {
            @ApiResponse(description = "Sucesso", responseCode = "200",
                         content = @Content(schema = @Schema(implementation = PublisherResponseDTO.class))),
            @ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)
        }
    )
    public ResponseEntity<List<PublisherResponseDTO>> buscarTodos() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Buscar por ID",
        description = "Retorna os detalhes de um registro específico de publishers através do seu id único.",
        responses = {
            @ApiResponse(description = "Sucesso", responseCode = "200",
                         content = @Content(schema = @Schema(implementation = PublisherResponseDTO.class))),
            @ApiResponse(description = "Não encontrado", responseCode = "404", content = @Content),
            @ApiResponse(description = "ID inválido", responseCode = "400", content = @Content)
        }
    )
    public ResponseEntity<PublisherResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Criar novo registro",
        description = "Cadastra um novo registro de publishers no sistema e retorna o objeto criado.",
        responses = {
            @ApiResponse(description = "Criado com sucesso", responseCode = "201",
                         content = @Content(schema = @Schema(implementation = PublisherResponseDTO.class))),
            @ApiResponse(description = "Erro de validação", responseCode = "400", content = @Content)
        }
    )
    public ResponseEntity<PublisherResponseDTO> criar(@Valid @RequestBody PublisherRequestDTO dto) {
        PublisherResponseDTO novoPublisher = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPublisher);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Atualizar registro",
        description = "Atualiza todos os dados de um registro existente de publishers.",
        responses = {
            @ApiResponse(description = "Atualizado com sucesso", responseCode = "200",
                         content = @Content(schema = @Schema(implementation = PublisherResponseDTO.class))),
            @ApiResponse(description = "Não encontrado", responseCode = "404", content = @Content),
            @ApiResponse(description = "Dados inválidos", responseCode = "400", content = @Content)
        }
    )
    public ResponseEntity<PublisherResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody PublisherRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Excluir registro",
        description = "Remove um registro de publishers do sistema pelo seu ID.",
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