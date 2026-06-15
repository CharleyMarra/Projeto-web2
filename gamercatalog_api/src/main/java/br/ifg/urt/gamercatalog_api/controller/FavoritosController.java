package br.ifg.urt.gamercatalog_api.controller;

import br.ifg.urt.gamercatalog_api.assembler.FavoritosModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import br.ifg.urt.gamercatalog_api.dto.request.FavoritosRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.FavoritosResponseDTO;
import br.ifg.urt.gamercatalog_api.service.FavoritosService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

@RestController
@RequestMapping("/favoritos")
@Tag(name = "Favoritos", description = "Endpoints para gerenciamento de Favoritos")
public class FavoritosController {

    private final FavoritosService service;
    private final FavoritosModelAssembler assembler;

    public FavoritosController(FavoritosService service, FavoritosModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Listar favoritos paginados")
    public ResponseEntity<PagedModel<EntityModel<FavoritosResponseDTO>>> buscarTodos(
            @RequestParam(required = false) Long usuarioId,
            @PageableDefault(size = 10, sort = "dataAdicionado") Pageable pageable,
            PagedResourcesAssembler<FavoritosResponseDTO> pagedAssembler) {

        Page<FavoritosResponseDTO> page = (usuarioId != null)
            ? service.findByUsuario(usuarioId, pageable)
            : service.findAll(pageable);

        return ResponseEntity.ok(pagedAssembler.toModel(page, assembler));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Buscar por ID")
    public ResponseEntity<EntityModel<FavoritosResponseDTO>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(service.findById(id)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Criar novo registro")
    public ResponseEntity<EntityModel<FavoritosResponseDTO>> criar(@Valid @RequestBody FavoritosRequestDTO dto) {
        FavoritosResponseDTO novoFavorito = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(novoFavorito));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir registro")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}