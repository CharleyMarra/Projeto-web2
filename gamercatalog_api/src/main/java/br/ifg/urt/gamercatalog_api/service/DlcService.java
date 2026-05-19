package br.ifg.urt.gamercatalog_api.service;

import br.ifg.urt.gamercatalog_api.model.Dlc;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DlcService {

    private List<Dlc> dlcs = new ArrayList<>();

    public List<Dlc> listar() {
        return dlcs;
    }

    public Dlc cadastrar(Dlc dlc){
        dlcs.add(dlc);
        return dlc;
    }
}