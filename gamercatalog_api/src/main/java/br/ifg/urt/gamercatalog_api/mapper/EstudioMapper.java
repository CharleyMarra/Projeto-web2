package br.ifg.urt.gamercatalog_api.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import br.ifg.urt.gamercatalog_api.dto.request.EstudioRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.EstudioResponseDTO;
import br.ifg.urt.gamercatalog_api.model.Estudio;

@Mapper(componentModel = "spring")
public interface EstudioMapper {

    @Mapping(target = "paisOrigem", source = "pais")
    EstudioResponseDTO toResponseDTO(Estudio estudio);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pais", source = "paisOrigem")
    @Mapping(target = "jogos", ignore = true)
    Estudio toEntity(EstudioRequestDTO dto);

    List<EstudioResponseDTO> toResponseDTOList(List<Estudio> estudios);
}