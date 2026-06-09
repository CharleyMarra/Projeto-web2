package br.ifg.urt.gamercatalog_api.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.ifg.urt.gamercatalog_api.model.Avaliacao;
import br.ifg.urt.gamercatalog_api.service.AvaliacaoService;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService service;

    public AvaliacaoController(AvaliacaoService service) {
        this.service = service;
    }

    // 200 OK - Padrão para listagens (Aceita o filtro opcional ?jogoId=X)
    @GetMapping
    public ResponseEntity<List<Avaliacao>> buscarTodos(
            @RequestParam(required = false) Long jogoId) {

        if (jogoId != null) {
            List<Avaliacao> avaliacoesPorJogo = service.findByJogo(jogoId);
            return ResponseEntity.ok(avaliacoesPorJogo);
        }

        List<Avaliacao> avaliacoes = service.findAll();

        return ResponseEntity.ok(avaliacoes);
    }

    // 200 OK - Busca individual
    @GetMapping("/{id}")
    public ResponseEntity<Avaliacao> buscarPorId(
            @PathVariable Long id) {

        Avaliacao avaliacao = service.findById(id);

        return ResponseEntity.ok(avaliacao);
    }

    // 201 Created - Criação
    @PostMapping
    public ResponseEntity<Avaliacao> criar(
            @RequestBody Avaliacao avaliacao) {

        Avaliacao novaAvaliacao =
                service.create(avaliacao);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(novaAvaliacao);
    }

    // 200 OK - Atualização
    @PutMapping("/{id}")
    public ResponseEntity<Avaliacao> atualizar(
            @PathVariable Long id,
            @RequestBody Avaliacao avaliacao) {

        avaliacao.setIdAvaliacao(id);

        Avaliacao avaliacaoAtualizada =
                service.update(avaliacao);

        return ResponseEntity.ok(
                avaliacaoAtualizada
        );
    }

    // 204 No Content - Remoção
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id) {

        service.delete(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}