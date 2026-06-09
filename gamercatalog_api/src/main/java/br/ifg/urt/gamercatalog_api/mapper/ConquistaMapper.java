package br.ifg.urt.gamercatalog_api.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import br.ifg.urt.gamercatalog_api.dto.request.ConquistaRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.ConquistaResponseDTO;
import br.ifg.urt.gamercatalog_api.model.Conquista;

@Mapper(componentModel = "spring")
public interface ConquistaMapper {

    @Mapping(target = "nomeJogo", source = "jogo.titulo") // Alterado para titulo
    ConquistaResponseDTO toResponseDTO(Conquista conquista);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "jogo.id", source = "jogoId") // Link correto da Entity
    Conquista toEntity(ConquistaRequestDTO dto);

    List<ConquistaResponseDTO> toResponseDTOList(List<Conquista> conquistas);
}