package br.ifg.urt.gamercatalog_api.controller;

import br.ifg.urt.gamercatalog_api.assembler.DlcModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import br.ifg.urt.gamercatalog_api.dto.request.DlcRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.DlcResponseDTO;
import br.ifg.urt.gamercatalog_api.service.DlcService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

@RestController
@RequestMapping("/dlcs")
@Tag(name = "Dlcs", description = "Endpoints para gerenciamento de Dlcs")
public class DlcController {

    private final DlcService service;
    private final DlcModelAssembler assembler;

    public DlcController(DlcService service, DlcModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Listar DLCs paginadas e com filtro")
    public ResponseEntity<PagedModel<EntityModel<DlcResponseDTO>>> buscarTodos(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Long jogoId,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable,
            PagedResourcesAssembler<DlcResponseDTO> pagedAssembler) {

        Page<DlcResponseDTO> page = (jogoId != null)
            ? service.findByJogo(jogoId, pageable)
            : service.findAll(nome, pageable);

        return ResponseEntity.ok(pagedAssembler.toModel(page, assembler));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Buscar por ID")
    public ResponseEntity<EntityModel<DlcResponseDTO>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(service.findById(id)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Criar novo registro")
    public ResponseEntity<EntityModel<DlcResponseDTO>> criar(@Valid @RequestBody DlcRequestDTO dto) {
        DlcResponseDTO novaDlc = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(novaDlc));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Atualizar registro")
    public ResponseEntity<EntityModel<DlcResponseDTO>> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody DlcRequestDTO dto) {
        return ResponseEntity.ok(assembler.toModel(service.update(id, dto)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir registro")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}