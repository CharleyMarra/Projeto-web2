package br.ifg.urt.gamercatalog_api.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import br.ifg.urt.gamercatalog_api.dto.request.FavoritosRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.FavoritosResponseDTO;
import br.ifg.urt.gamercatalog_api.model.Favoritos;

@Mapper(componentModel = "spring")
public interface FavoritosMapper {

    @Mapping(target = "nomeUsuario", source = "usuario.nome")
    @Mapping(target = "jogoId", source = "jogo.id")
    @Mapping(target = "nomeJogo", source = "jogo.titulo") // Alterado para titulo
    FavoritosResponseDTO toResponseDTO(Favoritos f);

    @Mapping(target = "idFavorito", ignore = true)
    @Mapping(target = "dataAdicionado", ignore = true)
    @Mapping(target = "usuario.id", source = "usuarioId")
    @Mapping(target = "jogo.id", source = "jogoId")
    Favoritos toEntity(FavoritosRequestDTO dto);

    List<FavoritosResponseDTO> toResponseDTOList(List<Favoritos> lista);
}