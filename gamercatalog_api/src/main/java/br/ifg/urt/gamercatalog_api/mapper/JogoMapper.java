package br.ifg.urt.gamercatalog_api.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import br.ifg.urt.gamercatalog_api.dto.request.JogoRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.JogoResponseDTO;
import br.ifg.urt.gamercatalog_api.model.Jogo;

@Mapper(componentModel = "spring")
public interface JogoMapper {

    @Mapping(target = "nome", source = "titulo")
    JogoResponseDTO toResponseDTO(Jogo j);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "titulo", source = "nome")
    @Mapping(target = "estudio", ignore = true)  
    @Mapping(target = "publisher", ignore = true)
    @Mapping(target = "dlcs", ignore = true)     
    @Mapping(target = "plataformas", ignore = true) 
    Jogo toEntity(JogoRequestDTO dto);

    List<JogoResponseDTO> toResponseDTOList(List<Jogo> jogos);
}