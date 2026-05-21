package br.ifg.urt.gamercatalog_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ifg.urt.gamercatalog_api.model.Jogo;
import br.ifg.urt.gamercatalog_api.service.JogoService;

@RestController
@RequestMapping("/jogos")
public class JogoController {

    private final JogoService service;

    public JogoController(JogoService service) {
        this.service = service;
    }

    // 200 OK - Padrão para listagens
    @GetMapping
    public ResponseEntity<List<Jogo>> buscarTodos() {

        List<Jogo> jogos = service.findAll();

        return ResponseEntity.ok(jogos);
    }

    // 200 OK - Padrão para busca individual bem-sucedida
    @GetMapping("/{id}")
    public ResponseEntity<Jogo> buscarPorId(@PathVariable Long id) {

        Jogo jogo = service.findById(id);

        return ResponseEntity.ok(jogo);
    }

    // 201 Created - Padrão para criação de recursos
    @PostMapping
    public ResponseEntity<Jogo> criar(@RequestBody Jogo jogo) {

        Jogo novoJogo = service.create(jogo);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(novoJogo);
    }

    // 200 OK - Recurso atualizado com sucesso
    @PutMapping("/{id}")
    public ResponseEntity<Jogo> atualizar(
            @PathVariable Long id,
            @RequestBody Jogo jogo) {

        jogo.setId(id);

        Jogo jogoAtualizado = service.update(jogo);

        return ResponseEntity.ok(jogoAtualizado);
    }

    // 204 No Content - Padrão para remoção
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}