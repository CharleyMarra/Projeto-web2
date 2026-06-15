package br.ifg.urt.gamercatalog_api.controller;

import br.ifg.urt.gamercatalog_api.assembler.ComentariosModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import br.ifg.urt.gamercatalog_api.dto.request.ComentariosRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.ComentariosResponseDTO;
import br.ifg.urt.gamercatalog_api.service.ComentariosService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.validation.annotation.Validated;

@Validated
@RestController
@RequestMapping("/comentarios")
@Tag(name = "Comentários", description = "Endpoints para gerenciamento de Comentários")
public class ComentariosController {

    private final ComentariosService service;
    private final ComentariosModelAssembler assembler;

    public ComentariosController(ComentariosService service, ComentariosModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Listar comentários paginados e com filtro")
    public ResponseEntity<PagedModel<EntityModel<ComentariosResponseDTO>>> buscarTodos(
            @RequestParam(required = false) String texto,
            @RequestParam(required = false) Long jogoId,
            @PageableDefault(size = 10, sort = "dataHora") Pageable pageable,
            PagedResourcesAssembler<ComentariosResponseDTO> pagedAssembler) {

        Page<ComentariosResponseDTO> page = (jogoId != null)
            ? service.findByJogo(jogoId, pageable)
            : service.findAll(texto, pageable);

        return ResponseEntity.ok(pagedAssembler.toModel(page, assembler));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Buscar por ID")
    public ResponseEntity<EntityModel<ComentariosResponseDTO>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(service.findById(id)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Criar novo registro")
    public ResponseEntity<EntityModel<ComentariosResponseDTO>> criar(@Valid @RequestBody ComentariosRequestDTO dto) {
        ComentariosResponseDTO novoComentario = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(novoComentario));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Atualizar registro")
    public ResponseEntity<EntityModel<ComentariosResponseDTO>> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ComentariosRequestDTO dto) {
        return ResponseEntity.ok(assembler.toModel(service.update(id, dto)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir registro")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}