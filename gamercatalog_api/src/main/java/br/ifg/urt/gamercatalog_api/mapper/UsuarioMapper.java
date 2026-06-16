package br.ifg.urt.gamercatalog_api.mapper;

import org.springframework.stereotype.Component;
import br.ifg.urt.gamercatalog_api.dto.request.UsuarioRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.UsuarioResponseDTO;
import br.ifg.urt.gamercatalog_api.model.Usuario;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequestDTO dto) {
        if (dto == null) return null;

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(dto.senha());
        return usuario;
    }

    public UsuarioResponseDTO toResponseDTO(Usuario entity) {
        if (entity == null) return null;

        return new UsuarioResponseDTO(
                entity.getId(),
                entity.getNome(),
                entity.getEmail()
        );
    }

    public List<UsuarioResponseDTO> toResponseDTOList(List<Usuario> entities) {
        if (entities == null) return null;
        return entities.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }
}