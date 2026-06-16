package br.ifg.urt.gamercatalog_api.controller;

import br.ifg.urt.gamercatalog_api.assembler.UsuarioModelAssembler;
import br.ifg.urt.gamercatalog_api.dto.request.UsuarioRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.UsuarioResponseDTO;
import br.ifg.urt.gamercatalog_api.exception.ExceptionResponse;
import br.ifg.urt.gamercatalog_api.service.UsuarioService;
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
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de Usuários")
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioModelAssembler assembler;
    private final PagedResourcesAssembler<UsuarioResponseDTO> pagedAssembler;

    public UsuarioController(UsuarioService service, UsuarioModelAssembler assembler, PagedResourcesAssembler<UsuarioResponseDTO> pagedAssembler) {
        this.service = service;
        this.assembler = assembler;
        this.pagedAssembler = pagedAssembler;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Listar usuários paginados e com filtro", 
        description = "Retorna uma lista paginada de usuários com os links do HATEOAS",
        responses = {
            @ApiResponse(description = "Sucesso", responseCode = "200"),
            @ApiResponse(description = "Erro Interno", responseCode = "500", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        }
    )
    public ResponseEntity<PagedModel<EntityModel<UsuarioResponseDTO>>> buscarTodos(
            @RequestParam(required = false) String nome,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
    
        Page<UsuarioResponseDTO> page = service.findAll(nome, pageable);
        return ResponseEntity.ok(pagedAssembler.toModel(page, assembler));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Buscar por ID", 
        description = "Retorna os detalhes de um registro específico de usuários através do seu id único.",
        responses = {
            @ApiResponse(description = "Sucesso", responseCode = "200"),
            @ApiResponse(description = "Usuário não encontrado", responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(description = "ID inválido", responseCode = "400", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        }
    )
    public ResponseEntity<EntityModel<UsuarioResponseDTO>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(service.findById(id)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Criar novo registro", 
        description = "Cadastra um novo registro de usuários no sistema e retorna o objeto criado.",
        responses = {
            @ApiResponse(description = "Criado com sucesso", responseCode = "201"),
            @ApiResponse(description = "Erro de validação nos dados enviados", responseCode = "400", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        }
    )
    public ResponseEntity<EntityModel<UsuarioResponseDTO>> criar(@Valid @RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO novoUsuario = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(novoUsuario));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Atualizar registro", 
        description = "Atualiza todos os dados de um registro existente de usuários.",
        responses = {
            @ApiResponse(description = "Atualizado com sucesso", responseCode = "200"),
            @ApiResponse(description = "Não foi possível atualizar: Usuário não existe", responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(description = "Dados de atualização inválidos", responseCode = "400", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        }
    )
    public ResponseEntity<EntityModel<UsuarioResponseDTO>> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.ok(assembler.toModel(service.update(id, dto)));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Excluir registro", 
        description = "Remove um registro de usuários do sistema pelo seu ID.",
        responses = {
            @ApiResponse(description = "Excluído com sucesso", responseCode = "204"),
            @ApiResponse(description = "Não foi possível excluir: Usuário não existe", responseCode = "404", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
        }
    )
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}