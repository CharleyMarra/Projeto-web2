package br.ifg.urt.gamercatalog_api.service;

import br.ifg.urt.gamercatalog_api.model.Conquista;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConquistaService {

    private List<Conquista> conquistas = new ArrayList<>();

    public List<Conquista> listar() {
        return conquistas;
    }

    public Conquista cadastrar(Conquista conquista){
        conquistas.add(conquista);
        return conquista;
    }
}