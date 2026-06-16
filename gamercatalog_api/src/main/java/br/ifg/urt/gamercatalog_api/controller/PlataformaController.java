package br.ifg.urt.gamercatalog_api.controller;

import br.ifg.urt.gamercatalog_api.assembler.PlataformaModelAssembler;
import br.ifg.urt.gamercatalog_api.dto.request.PlataformaRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.PlataformaResponseDTO;
import br.ifg.urt.gamercatalog_api.exception.ExceptionResponse;
import br.ifg.urt.gamercatalog_api.service.PlataformaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/plataformas")
@Tag(name = "Plataformas", description = "Endpoints para gerenciamento de Plataformas")
public class PlataformaController {

    private final PlataformaService service;
    private final PlataformaModelAssembler assembler;
    private final PagedResourcesAssembler<PlataformaResponseDTO> pagedAssembler;

    public PlataformaController(PlataformaService service, PlataformaModelAssembler assembler, PagedResourcesAssembler<PlataformaResponseDTO> pagedAssembler) {
        this.service = service;
        this.assembler = assembler;
        this.pagedAssembler = pagedAssembler;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Listar plataformas paginadas e com filtro", 
        description = "Retorna uma lista paginada de plataformas com os links do HATEOAS",
        responses = {
            @ApiResponse(description = "Sucesso", responseCode = "200"),
            @ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        }
    )
    public ResponseEntity<PagedModel<EntityModel<PlataformaResponseDTO>>> buscarTodos(
            @RequestParam(required = false) String nome,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        
        Page<PlataformaResponseDTO> page = service.findAll(nome, pageable);
        return ResponseEntity.ok(pagedAssembler.toModel(page, assembler));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Buscar plataforma por ID", 
        description = "Retorna os detalhes de uma plataforma específica enriquecida com links",
        responses = {
            @ApiResponse(description = "Sucesso", responseCode = "200"),
            @ApiResponse(description = "Plataforma não encontrada", responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(description = "ID inválido", responseCode = "400", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        }
    )
    public ResponseEntity<EntityModel<PlataformaResponseDTO>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(service.findById(id)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Criar uma nova plataforma", 
        description = "Cadastra uma nova plataforma e retorna os links das ações disponíveis",
        responses = {
            @ApiResponse(description = "Criado com sucesso", responseCode = "201"),
            @ApiResponse(description = "Erro de validação nos dados enviados", responseCode = "400", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        }
    )
    public ResponseEntity<EntityModel<PlataformaResponseDTO>> criar(@Valid @RequestBody PlataformaRequestDTO dto) {
        PlataformaResponseDTO novaPlataforma = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(novaPlataforma));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Atualizar uma plataforma", 
        description = "Atualiza os dados de uma plataforma existente e atualiza os links de hipermídia",
        responses = {
            @ApiResponse(description = "Atualizado com sucesso", responseCode = "200"),
            @ApiResponse(description = "Não foi possível atualizar: Plataforma não existe", responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(description = "Dados de atualização inválidos", responseCode = "400", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        }
    )
    public ResponseEntity<EntityModel<PlataformaResponseDTO>> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody PlataformaRequestDTO dto) {
        return ResponseEntity.ok(assembler.toModel(service.update(id, dto)));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Excluir uma plataforma", 
        description = "Remove uma plataforma por ID",
        responses = {
            @ApiResponse(description = "Excluído com sucesso", responseCode = "204"),
            @ApiResponse(description = "Não foi possível excluir: Plataforma não existe", responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        }
    )
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}