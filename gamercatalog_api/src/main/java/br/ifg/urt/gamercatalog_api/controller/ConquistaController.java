package br.ifg.urt.gamercatalog_api.controller;

import br.ifg.urt.gamercatalog_api.assembler.ConquistaModelAssembler;
import br.ifg.urt.gamercatalog_api.dto.request.ConquistaRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.ConquistaResponseDTO;
import br.ifg.urt.gamercatalog_api.exception.ExceptionResponse;
import br.ifg.urt.gamercatalog_api.service.ConquistaService;
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
@RequestMapping("/conquistas")
@Tag(name = "Conquistas", description = "Endpoints para gerenciamento de Conquistas")
public class ConquistaController {

    private final ConquistaService service;
    private final ConquistaModelAssembler assembler;
    private final PagedResourcesAssembler<ConquistaResponseDTO> pagedAssembler;

    public ConquistaController(ConquistaService service, ConquistaModelAssembler assembler, PagedResourcesAssembler<ConquistaResponseDTO> pagedAssembler) {
        this.service = service;
        this.assembler = assembler;
        this.pagedAssembler = pagedAssembler;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Buscar conquista por ID", 
        description = "Retorna os detalhes de uma conquista específica enriquecida com links",
        responses = {
            @ApiResponse(description = "Sucesso", responseCode = "200"),
            @ApiResponse(description = "Conquista não encontrada", responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(description = "ID inválido", responseCode = "400", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        }
    )
    public ResponseEntity<EntityModel<ConquistaResponseDTO>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(service.findById(id)));
    }

    @GetMapping(value = "/jogo/{jogoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Listar todas as conquistas de um jogo específico", 
        description = "Retorna uma lista paginada de conquistas vinculadas ao ID do jogo com links do HATEOAS",
        responses = {
            @ApiResponse(description = "Sucesso", responseCode = "200"),
            @ApiResponse(description = "Erro Interno", responseCode = "500", 
                         content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        }
    )
    public ResponseEntity<PagedModel<EntityModel<ConquistaResponseDTO>>> buscarPorJogo(
            @PathVariable Long jogoId,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        
        Page<ConquistaResponseDTO> paginaConquistas = service.findByJogo(jogoId, pageable);
        return ResponseEntity.ok(pagedAssembler.toModel(paginaConquistas, assembler));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Criar uma nova conquista", 
        description = "Cadastra uma nova conquista e retorna os links das ações disponíveis",
        responses = {
            @ApiResponse(description = "Criado com sucesso", responseCode = "201"),
            @ApiResponse(description = "Erro de validação nos dados enviados", responseCode = "400", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        }
    )
    public ResponseEntity<EntityModel<ConquistaResponseDTO>> criar(@Valid @RequestBody ConquistaRequestDTO dto) {
        ConquistaResponseDTO novaConquista = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(novaConquista));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Atualizar uma conquista", 
        description = "Atualiza os dados de uma conquista existente e atualiza os links de hipermídia",
        responses = {
            @ApiResponse(description = "Atualizado com sucesso", responseCode = "200"),
            @ApiResponse(description = "Não foi possível atualizar: Conquista não existe", responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(description = "Dados de atualização inválidos", responseCode = "400", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        }
    )
    public ResponseEntity<EntityModel<ConquistaResponseDTO>> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ConquistaRequestDTO dto) {
        return ResponseEntity.ok(assembler.toModel(service.update(id, dto)));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Excluir uma conquista", 
        description = "Remove uma conquista por ID",
        responses = {
            @ApiResponse(description = "Excluído com sucesso", responseCode = "204"),
            @ApiResponse(description = "Não foi possível excluir: Conquista não existe", responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        }
    )
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}