package br.ifg.urt.gamercatalog_api.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import br.ifg.urt.gamercatalog_api.dto.request.ComentariosRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.ComentariosResponseDTO;
import br.ifg.urt.gamercatalog_api.model.Comentarios;

@Mapper(componentModel = "spring")
public interface ComentariosMapper {

    @Mapping(target = "nomeUsuario", source = "usuario.nome")
    @Mapping(target = "nomeJogo", source = "jogo.titulo") // Alterado para titulo
    ComentariosResponseDTO toResponseDTO(Comentarios c);

    @Mapping(target = "idComentario", ignore = true)
    @Mapping(target = "dataHora", ignore = true)
    @Mapping(target = "usuario.id", source = "usuarioId")
    @Mapping(target = "jogo.id", source = "jogoId")
    Comentarios toEntity(ComentariosRequestDTO dto);

    List<ComentariosResponseDTO> toResponseDTOList(List<Comentarios> lista);
}