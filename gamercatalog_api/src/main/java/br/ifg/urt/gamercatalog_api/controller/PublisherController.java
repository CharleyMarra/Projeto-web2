package br.ifg.urt.gamercatalog_api.controller;

import br.ifg.urt.gamercatalog_api.assembler.PublisherModelAssembler;
import br.ifg.urt.gamercatalog_api.dto.request.PublisherRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.PublisherResponseDTO;
import br.ifg.urt.gamercatalog_api.exception.ExceptionResponse;
import br.ifg.urt.gamercatalog_api.service.PublisherService;
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
@RequestMapping("/publishers")
@Tag(name = "Publishers", description = "Endpoints para gerenciamento de Publishers")
public class PublisherController {

    private final PublisherService service;
    private final PublisherModelAssembler assembler;
    private final PagedResourcesAssembler<PublisherResponseDTO> pagedAssembler;

    public PublisherController(PublisherService service, PublisherModelAssembler assembler, PagedResourcesAssembler<PublisherResponseDTO> pagedAssembler) {
        this.service = service;
        this.assembler = assembler;
        this.pagedAssembler = pagedAssembler;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Listar publishers paginadas e com filtro", 
        description = "Retorna uma lista paginada de publishers com os links do HATEOAS",
        responses = {
            @ApiResponse(description = "Sucesso", responseCode = "200"),
            @ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        }
    )
    public ResponseEntity<PagedModel<EntityModel<PublisherResponseDTO>>> buscarTodos(
            @RequestParam(required = false) String nome,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) { 
        
        Page<PublisherResponseDTO> page = service.findAll(nome, pageable);
        return ResponseEntity.ok(pagedAssembler.toModel(page, assembler));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Buscar publisher por ID", 
        description = "Retorna os detalhes de um publisher específico enriquecido com links",
        responses = {
            @ApiResponse(description = "Sucesso", responseCode = "200"),
            @ApiResponse(description = "Publisher não encontrado", responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(description = "ID inválido", responseCode = "400", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        }
    )
    public ResponseEntity<EntityModel<PublisherResponseDTO>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(service.findById(id)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Criar um novo publisher", 
        description = "Cadastra um novo publisher e retorna os links das ações disponíveis",
        responses = {
            @ApiResponse(description = "Criado com sucesso", responseCode = "201"),
            @ApiResponse(description = "Erro de validação nos dados enviados", responseCode = "400", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        }
    )
    public ResponseEntity<EntityModel<PublisherResponseDTO>> criar(@Valid @RequestBody PublisherRequestDTO dto) {
        PublisherResponseDTO novoPublisher = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(novoPublisher));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Atualizar um publisher", 
        description = "Atualiza os dados de um publisher existente e atualiza os links de hipermídia",
        responses = {
            @ApiResponse(description = "Atualizado com sucesso", responseCode = "200"),
            @ApiResponse(description = "Não foi possível atualizar: Publisher não existe", responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(description = "Dados de atualização inválidos", responseCode = "400", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        }
    )
    public ResponseEntity<EntityModel<PublisherResponseDTO>> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody PublisherRequestDTO dto) {
        return ResponseEntity.ok(assembler.toModel(service.update(id, dto)));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Excluir um publisher", 
        description = "Remove um publisher por ID",
        responses = {
            @ApiResponse(description = "Excluído com sucesso", responseCode = "204"),
            @ApiResponse(description = "Não foi possível excluir: Publisher não existe", responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        }
    )
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}