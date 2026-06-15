package br.ifg.urt.gamercatalog_api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping
    public ResponseEntity<Page<Conquista>> buscarTodos(
            @RequestParam(required = false) String nome,
            @PageableDefault(size = 10, sort = "titulo") Pageable pageable) {

        Page<Conquista> conquistas = service.findAll(nome, pageable);
        return ResponseEntity.ok(conquistas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conquista> buscarPorId(@PathVariable Long id) {
        Conquista conquista = service.findById(id);
        return ResponseEntity.ok(conquista);
    }

    @PostMapping
    public ResponseEntity<Conquista> criar(@RequestBody Conquista conquista) {
        Conquista novaConquista = service.create(conquista);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaConquista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Conquista> atualizar(@PathVariable Long id, @RequestBody Conquista conquista) {
        conquista.setId(id);
        Conquista conquistaAtualizada = service.update(conquista);
        return ResponseEntity.ok(conquistaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}