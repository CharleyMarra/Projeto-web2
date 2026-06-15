package br.ifg.urt.gamercatalog_api.controller;

import org.springframework.data.domain.Page; // Adicionado
import org.springframework.data.domain.Pageable; // Adicionado
import org.springframework.data.web.PageableDefault; // Adicionado
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ifg.urt.gamercatalog_api.model.Dlc;
import br.ifg.urt.gamercatalog_api.service.DlcService;

@RestController
@RequestMapping("/dlcs")
public class DlcController {

    private final DlcService service;

    public DlcController(DlcService service) {
        this.service = service;
    }

    // Listar todas as DLCs paginadas e com filtro por nome
    @GetMapping
    public ResponseEntity<Page<Dlc>> buscarTodos(
            @RequestParam(required = false) String nome,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {

        Page<Dlc> dlcs = service.findAll(nome, pageable);

        return ResponseEntity.ok(dlcs);
    }

    // Buscar DLC por ID
    @GetMapping("/{id}")
    public ResponseEntity<Dlc> buscarPorId(@PathVariable Long id) {

        Dlc dlc = service.findById(id);

        return ResponseEntity.ok(dlc);
    }

    // Cadastrar nova DLC
    @PostMapping
    public ResponseEntity<Dlc> criar(@RequestBody Dlc dlc) {

        Dlc novaDlc = service.create(dlc);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(novaDlc);
    }

    // Atualizar DLC
    @PutMapping("/{id}")
    public ResponseEntity<Dlc> atualizar(
            @PathVariable Long id,
            @RequestBody Dlc dlc) {

        dlc.setId(id);

        Dlc dlcAtualizada = service.update(dlc);

        return ResponseEntity.ok(dlcAtualizada);
    }

    // Deletar DLC
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}