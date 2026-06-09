package br.ifg.urt.gamercatalog_api.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import br.ifg.urt.gamercatalog_api.dto.request.PublisherRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.PublisherResponseDTO;
import br.ifg.urt.gamercatalog_api.model.Publisher;

@Mapper(componentModel = "spring")
public interface PublisherMapper {

    PublisherResponseDTO toResponseDTO(Publisher publisher);

    @Mapping(target = "id", ignore = true)
    Publisher toEntity(PublisherRequestDTO dto);

    List<PublisherResponseDTO> toResponseDTOList(List<Publisher> publishers);
}