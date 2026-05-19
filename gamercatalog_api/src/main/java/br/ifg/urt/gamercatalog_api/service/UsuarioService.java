package br.ifg.urt.gamercatalog_api.service;

import br.ifg.urt.gamercatalog_api.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    private List<Usuario> usuarios = new ArrayList<>();

    public List<Usuario> listar() {
        return usuarios;
    }

    public Usuario cadastrar(Usuario usuario){
        usuarios.add(usuario);
        return usuario;
    }
}