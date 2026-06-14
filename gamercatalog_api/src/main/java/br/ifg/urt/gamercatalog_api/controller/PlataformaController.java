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
import br.ifg.urt.gamercatalog_api.dto.request.PlataformaRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.PlataformaResponseDTO;
import br.ifg.urt.gamercatalog_api.service.PlataformaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@RestController
@RequestMapping("/plataformas")
@Tag(name = "Plataformas", description = "Endpoints para gerenciamento de Plataformas")
public class PlataformaController {

    private final PlataformaService service;

    public PlataformaController(PlataformaService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Listar plataformas paginadas e com filtro",
        description = "Retorna uma página de plataformas. Permite filtrar por nome e utilizar paginação e ordenação com os parâmetros 'page', 'size' e 'sort'.",
        responses = {
            @ApiResponse(description = "Sucesso", responseCode = "200",
                         content = @Content(schema = @Schema(implementation = Page.class))),
            @ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content)
        }
    )
    public ResponseEntity<Page<PlataformaResponseDTO>> buscarTodos(
            @RequestParam(required = false) String nome,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) { // Note o sort = "nome"
        
        return ResponseEntity.ok(service.findAll(nome, pageable));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Buscar por ID",
        description = "Retorna os detalhes de um registro específico de plataformas através do seu id único.",
        responses = {
            @ApiResponse(description = "Sucesso", responseCode = "200",
                         content = @Content(schema = @Schema(implementation = PlataformaResponseDTO.class))),
            @ApiResponse(description = "Não encontrado", responseCode = "404", content = @Content),
            @ApiResponse(description = "ID inválido", responseCode = "400", content = @Content)
        }
    )
    public ResponseEntity<PlataformaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Criar novo registro",
        description = "Cadastra um novo registro de plataformas no sistema e retorna o objeto criado.",
        responses = {
            @ApiResponse(description = "Criado com sucesso", responseCode = "201",
                         content = @Content(schema = @Schema(implementation = PlataformaResponseDTO.class))),
            @ApiResponse(description = "Erro de validação", responseCode = "400", content = @Content)
        }
    )
    public ResponseEntity<PlataformaResponseDTO> criar(@Valid @RequestBody PlataformaRequestDTO dto) {
        PlataformaResponseDTO novaPlataforma = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaPlataforma);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Atualizar registro",
        description = "Atualiza todos os dados de um registro existente de plataformas.",
        responses = {
            @ApiResponse(description = "Atualizado com sucesso", responseCode = "200",
                         content = @Content(schema = @Schema(implementation = PlataformaResponseDTO.class))),
            @ApiResponse(description = "Não encontrado", responseCode = "404", content = @Content),
            @ApiResponse(description = "Dados inválidos", responseCode = "400", content = @Content)
        }
    )
    public ResponseEntity<PlataformaResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody PlataformaRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Excluir registro",
        description = "Remove um registro de plataformas do sistema pelo seu ID.",
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