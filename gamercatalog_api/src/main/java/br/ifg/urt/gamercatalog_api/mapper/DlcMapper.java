package br.ifg.urt.gamercatalog_api.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import br.ifg.urt.gamercatalog_api.dto.request.DlcRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.DlcResponseDTO;
import br.ifg.urt.gamercatalog_api.model.Dlc;

@Mapper(componentModel = "spring")
public interface DlcMapper {

    @Mapping(target = "nomeJogo", source = "jogo.titulo")
    DlcResponseDTO toResponseDTO(Dlc dlc);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "jogo.id", source = "jogoId")
    Dlc toEntity(DlcRequestDTO dto);

    List<DlcResponseDTO> toResponseDTOList(List<Dlc> dlcs);
}