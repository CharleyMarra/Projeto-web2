package br.ifg.urt.gamercatalog_api.controller;

import br.ifg.urt.gamercatalog_api.assembler.EstudioModelAssembler;
import br.ifg.urt.gamercatalog_api.dto.request.EstudioRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.EstudioResponseDTO;
import br.ifg.urt.gamercatalog_api.exception.ExceptionResponse;
import br.ifg.urt.gamercatalog_api.service.EstudioService;
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
@RequestMapping("/estudios")
@Tag(name = "Estúdios", description = "Endpoints para gerenciamento de Estúdios")
public class EstudioController {

    private final EstudioService service;
    private final EstudioModelAssembler assembler;
    private final PagedResourcesAssembler<EstudioResponseDTO> pagedAssembler;

    public EstudioController(EstudioService service, EstudioModelAssembler assembler, PagedResourcesAssembler<EstudioResponseDTO> pagedAssembler) {
        this.service = service;
        this.assembler = assembler;
        this.pagedAssembler = pagedAssembler;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Listar estúdios paginados e com filtro", 
        description = "Retorna uma lista paginada de estúdios com os links do HATEOAS",
        responses = {
            @ApiResponse(description = "Sucesso", responseCode = "200"),
            @ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        }
    )
    public ResponseEntity<PagedModel<EntityModel<EstudioResponseDTO>>> buscarTodos(
            @RequestParam(required = false) String nome,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) { 
        
        Page<EstudioResponseDTO> page = service.findAll(nome, pageable);
        return ResponseEntity.ok(pagedAssembler.toModel(page, assembler));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Buscar estúdio por ID", 
        description = "Retorna os detalhes de um estúdio específico enriquecido com links",
        responses = {
            @ApiResponse(description = "Sucesso", responseCode = "200"),
            @ApiResponse(description = "Estúdio não encontrado", responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(description = "ID inválido", responseCode = "400", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        }
    )
    public ResponseEntity<EntityModel<EstudioResponseDTO>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(service.findById(id)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Criar um novo estúdio", 
        description = "Cadastra um novo estúdio e retorna os links das ações disponíveis",
        responses = {
            @ApiResponse(description = "Criado com sucesso", responseCode = "201"),
            @ApiResponse(description = "Erro de validação nos dados enviados", responseCode = "400", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        }
    )
    public ResponseEntity<EntityModel<EstudioResponseDTO>> criar(@Valid @RequestBody EstudioRequestDTO dto) {
        EstudioResponseDTO novoEstudio = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(novoEstudio));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Atualizar um estúdio", 
        description = "Atualiza os dados de um estúdio existente e atualiza os links de hipermídia",
        responses = {
            @ApiResponse(description = "Atualizado com sucesso", responseCode = "200"),
            @ApiResponse(description = "Não foi possível atualizar: Estúdio não existe", responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(description = "Dados de atualização inválidos", responseCode = "400", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        }
    )
    public ResponseEntity<EntityModel<EstudioResponseDTO>> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody EstudioRequestDTO dto) {
        return ResponseEntity.ok(assembler.toModel(service.update(id, dto)));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Excluir um estúdio", 
        description = "Remove um estúdio por ID",
        responses = {
            @ApiResponse(description = "Excluído com sucesso", responseCode = "204"),
            @ApiResponse(description = "Não foi possível excluir: Estúdio não existe", responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        }
    )
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}