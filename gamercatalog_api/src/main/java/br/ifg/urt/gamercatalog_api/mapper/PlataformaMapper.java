package br.ifg.urt.gamercatalog_api.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import br.ifg.urt.gamercatalog_api.dto.request.PlataformaRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.PlataformaResponseDTO;
import br.ifg.urt.gamercatalog_api.model.Plataforma;

@Mapper(componentModel = "spring")
public interface PlataformaMapper {

    PlataformaResponseDTO toResponseDTO(Plataforma plataforma);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "jogos", ignore = true)
    Plataforma toEntity(PlataformaRequestDTO dto);

    List<PlataformaResponseDTO> toResponseDTOList(List<Plataforma> plataformas);
}