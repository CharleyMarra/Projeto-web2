package br.ifg.urt.gamercatalog_api.controller;

import br.ifg.urt.gamercatalog_api.assembler.JogoModelAssembler;
import br.ifg.urt.gamercatalog_api.dto.request.JogoRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.JogoResponseDTO;
import br.ifg.urt.gamercatalog_api.service.JogoService;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jogos")
@Tag(name = "Jogos", description = "Endpoints para gerenciamento de Jogos")
public class JogoController {

    private final JogoService service;
    private final JogoModelAssembler assembler;

    public JogoController(JogoService service, JogoModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Listar todos os jogos", description = "Retorna uma lista paginada de jogos com os links do HATEOAS")
    public ResponseEntity<PagedModel<EntityModel<JogoResponseDTO>>> buscarTodos(
            @RequestParam(required = false) String nome,
            @PageableDefault(size = 10, sort = "titulo") Pageable pageable,
            PagedResourcesAssembler<JogoResponseDTO> pagedAssembler) {
        
        Page<JogoResponseDTO> paginaJogos = service.findAll(nome, pageable);
        return ResponseEntity.ok(pagedAssembler.toModel(paginaJogos, assembler));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Buscar jogo por ID", description = "Retorna os detalhes de um jogo específico enriquecido com links")
    public ResponseEntity<EntityModel<JogoResponseDTO>> buscarPorId(@PathVariable Long id) {
        JogoResponseDTO response = service.findById(id);
        return ResponseEntity.ok(assembler.toModel(response));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Criar um novo jogo", description = "Cadastra um novo jogo no catálogo e retorna os links das ações disponíveis")
    public ResponseEntity<EntityModel<JogoResponseDTO>> criar(@Valid @RequestBody JogoRequestDTO dto) {
        JogoResponseDTO novoJogo = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(novoJogo));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Atualizar um jogo", description = "Atualiza os dados de um jogo existente e atualiza os links de hipermídia")
    public ResponseEntity<EntityModel<JogoResponseDTO>> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody JogoRequestDTO dto) {
        JogoResponseDTO response = service.update(id, dto);
        return ResponseEntity.ok(assembler.toModel(response));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um jogo", description = "Remove um jogo do catálogo por ID")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}