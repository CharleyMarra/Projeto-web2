package br.ifg.urt.gamercatalog_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ifg.urt.gamercatalog_api.model.Conquista;
import br.ifg.urt.gamercatalog_api.service.ConquistaService;

@RestController
@RequestMapping("/conquistas")
public class ConquistaController {

    private final ConquistaService service;

    public ConquistaController(ConquistaService service) {
        this.service = service;
    }

    // Listar todas as conquistas
    @GetMapping
    public ResponseEntity<List<Conquista>> buscarTodos() {

        List<Conquista> conquistas = service.findAll();

        return ResponseEntity.ok(conquistas);
    }

    // Buscar conquista por ID
    @GetMapping("/{id}")
    public ResponseEntity<Conquista> buscarPorId(@PathVariable Long id) {

        Conquista conquista = service.findById(id);

        return ResponseEntity.ok(conquista);
    }

    // Cadastrar nova conquista
    @PostMapping
    public ResponseEntity<Conquista> criar(@RequestBody Conquista conquista) {

        Conquista novaConquista = service.create(conquista);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(novaConquista);
    }

    // Atualizar conquista
    @PutMapping("/{id}")
    public ResponseEntity<Conquista> atualizar(
            @PathVariable Long id,
            @RequestBody Conquista conquista) {

        conquista.setId(id);

        Conquista conquistaAtualizada = service.update(conquista);

        return ResponseEntity.ok(conquistaAtualizada);
    }

    // Deletar conquista
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}