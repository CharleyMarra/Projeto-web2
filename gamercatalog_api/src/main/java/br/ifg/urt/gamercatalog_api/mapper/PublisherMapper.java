package br.ifg.urt.gamercatalog_api.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import br.ifg.urt.gamercatalog_api.dto.request.PublisherRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.PublisherResponseDTO;
import br.ifg.urt.gamercatalog_api.model.Publisher;

@Mapper(componentModel = "spring")
public interface PublisherMapper {

    // CORRIGIDO: Ensinando de onde vem o paisSede para a resposta
    @Mapping(target = "paisSede", source = "sede")
    PublisherResponseDTO toResponseDTO(Publisher publisher);

    // CORRIGIDO: Ensinando para onde vai o paisSede na hora de salvar
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sede", source = "paisSede") 
    @Mapping(target = "jogos", ignore = true) // Ignora a lista de jogos para evitar avisos
    Publisher toEntity(PublisherRequestDTO dto);

    List<PublisherResponseDTO> toResponseDTOList(List<Publisher> publishers);
}