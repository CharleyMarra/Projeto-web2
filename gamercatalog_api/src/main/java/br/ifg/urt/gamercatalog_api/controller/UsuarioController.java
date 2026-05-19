package br.ifg.urt.gamercatalog_api.controller;

import br.ifg.urt.gamercatalog_api.model.Usuario;
import br.ifg.urt.gamercatalog_api.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping
    public List<Usuario> listar() {
        return service.listar();
    }

    @PostMapping
    public Usuario cadastrar(@RequestBody Usuario usuario) {
        return service.cadastrar(usuario);
    }
}