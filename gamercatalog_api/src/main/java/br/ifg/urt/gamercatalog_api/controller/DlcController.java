package br.ifg.urt.gamercatalog_api.controller;

import br.ifg.urt.gamercatalog_api.model.Dlc;
import br.ifg.urt.gamercatalog_api.service.DlcService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dlcs")
public class DlcController {

    private final DlcService service;

    public DlcController(DlcService service) {
        this.service = service;
    }

    @GetMapping
    public List<Dlc> listar() {
        return service.listar();
    }

    @PostMapping
    public Dlc cadastrar(@RequestBody Dlc dlc) {
        return service.cadastrar(dlc);
    }
}