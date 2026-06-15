package br.ifg.urt.gamercatalog_api.controller;

import br.ifg.urt.gamercatalog_api.assembler.ConquistaModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import br.ifg.urt.gamercatalog_api.dto.request.ConquistaRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.ConquistaResponseDTO;
import br.ifg.urt.gamercatalog_api.service.ConquistaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

@RestController
@RequestMapping("/conquistas")
@Tag(name = "Conquistas", description = "Endpoints para gerenciamento de Conquistas")
public class ConquistaController {

    private final ConquistaService service;
    private final ConquistaModelAssembler assembler;

    public ConquistaController(ConquistaService service, ConquistaModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Listar conquistas paginadas e com filtro")
    public ResponseEntity<PagedModel<EntityModel<ConquistaResponseDTO>>> buscarTodos(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Long usuarioId,
            @PageableDefault(size = 10, sort = "titulo") Pageable pageable,
            PagedResourcesAssembler<ConquistaResponseDTO> pagedAssembler) {

        Page<ConquistaResponseDTO> page = (usuarioId != null)
            ? service.findByUsuario(usuarioId, pageable)
            : service.findAll(titulo, pageable);

        return ResponseEntity.ok(pagedAssembler.toModel(page, assembler));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Buscar por ID")
    public ResponseEntity<EntityModel<ConquistaResponseDTO>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(service.findById(id)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Criar novo registro")
    public ResponseEntity<EntityModel<ConquistaResponseDTO>> criar(@Valid @RequestBody ConquistaRequestDTO dto) {
        ConquistaResponseDTO novaConquista = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(novaConquista));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Atualizar registro")
    public ResponseEntity<EntityModel<ConquistaResponseDTO>> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ConquistaRequestDTO dto) {
        return ResponseEntity.ok(assembler.toModel(service.update(id, dto)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir registro")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}