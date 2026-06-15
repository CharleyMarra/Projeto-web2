package br.ifg.urt.gamercatalog_api.controller;

import br.ifg.urt.gamercatalog_api.assembler.PlataformaModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import br.ifg.urt.gamercatalog_api.dto.request.PlataformaRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.PlataformaResponseDTO;
import br.ifg.urt.gamercatalog_api.service.PlataformaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

@RestController
@RequestMapping("/plataformas")
@Tag(name = "Plataformas", description = "Endpoints para gerenciamento de Plataformas")
public class PlataformaController {

    private final PlataformaService service;
    private final PlataformaModelAssembler assembler;

    public PlataformaController(PlataformaService service, PlataformaModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Listar plataformas paginadas e com filtro")
    public ResponseEntity<PagedModel<EntityModel<PlataformaResponseDTO>>> buscarTodos(
            @RequestParam(required = false) String nome,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable,
            PagedResourcesAssembler<PlataformaResponseDTO> pagedAssembler) {
        
        Page<PlataformaResponseDTO> page = service.findAll(nome, pageable);
        return ResponseEntity.ok(pagedAssembler.toModel(page, assembler));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Buscar por ID")
    public ResponseEntity<EntityModel<PlataformaResponseDTO>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(service.findById(id)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Criar novo registro")
    public ResponseEntity<EntityModel<PlataformaResponseDTO>> criar(@Valid @RequestBody PlataformaRequestDTO dto) {
        PlataformaResponseDTO novaPlataforma = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(novaPlataforma));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Atualizar registro")
    public ResponseEntity<EntityModel<PlataformaResponseDTO>> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody PlataformaRequestDTO dto) {
        return ResponseEntity.ok(assembler.toModel(service.update(id, dto)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir registro")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}