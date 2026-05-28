package br.ifg.urt.gamercatalog_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ifg.urt.gamercatalog_api.model.Usuario;
import br.ifg.urt.gamercatalog_api.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    // Listar todos os usuários
    @GetMapping
    public ResponseEntity<List<Usuario>> buscarTodos() {

        List<Usuario> usuarios = service.findAll();

        return ResponseEntity.ok(usuarios);
    }

    // Buscar usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {

        Usuario usuario = service.findById(id);

        return ResponseEntity.ok(usuario);
    }

    // Cadastrar novo usuário
    @PostMapping
    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario) {

        Usuario novoUsuario = service.create(usuario);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(novoUsuario);
    }

    // Atualizar usuário
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(
            @PathVariable Long id,
            @RequestBody Usuario usuario) {

        usuario.setId(id);

        Usuario usuarioAtualizado = service.update(usuario);

        return ResponseEntity.ok(usuarioAtualizado);
    }

    // Deletar usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}