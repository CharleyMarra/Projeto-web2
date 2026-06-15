package br.ifg.urt.gamercatalog_api.controller;

import br.ifg.urt.gamercatalog_api.assembler.AvaliacaoModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import br.ifg.urt.gamercatalog_api.dto.request.AvaliacaoRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.AvaliacaoResponseDTO;
import br.ifg.urt.gamercatalog_api.service.AvaliacaoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

@RestController
@RequestMapping("/avaliacoes")
@Tag(name = "Avaliações", description = "Endpoints para gerenciamento de Avaliações")
public class AvaliacaoController {

    private final AvaliacaoService service;
    private final AvaliacaoModelAssembler assembler;

    public AvaliacaoController(AvaliacaoService service, AvaliacaoModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Listar avaliações paginadas")
    public ResponseEntity<PagedModel<EntityModel<AvaliacaoResponseDTO>>> buscarTodos(
            @RequestParam(required = false) Long jogoId,
            @PageableDefault(size = 10, sort = "dataPostagem") Pageable pageable,
            PagedResourcesAssembler<AvaliacaoResponseDTO> pagedAssembler) {

        Page<AvaliacaoResponseDTO> page = (jogoId != null) 
            ? service.findByJogo(jogoId, pageable) 
            : service.findAll(pageable);

        return ResponseEntity.ok(pagedAssembler.toModel(page, assembler));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Buscar por ID")
    public ResponseEntity<EntityModel<AvaliacaoResponseDTO>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(service.findById(id)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Criar novo registro")
    public ResponseEntity<EntityModel<AvaliacaoResponseDTO>> criar(@Valid @RequestBody AvaliacaoRequestDTO dto) {
        AvaliacaoResponseDTO novaAvaliacao = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(novaAvaliacao));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Atualizar registro")
    public ResponseEntity<EntityModel<AvaliacaoResponseDTO>> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AvaliacaoRequestDTO dto) {
        return ResponseEntity.ok(assembler.toModel(service.update(id, dto)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir registro")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}