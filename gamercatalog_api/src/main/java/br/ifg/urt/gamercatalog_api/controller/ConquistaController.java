package br.ifg.urt.gamercatalog_api.controller;

import br.ifg.urt.gamercatalog_api.model.Conquista;
import br.ifg.urt.gamercatalog_api.service.ConquistaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conquistas")
public class ConquistaController {

    private final ConquistaService service;

    public ConquistaController(ConquistaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Conquista> listar() {
        return service.listar();
    }

    @PostMapping
    public Conquista cadastrar(@RequestBody Conquista conquista) {
        return service.cadastrar(conquista);
    }
}