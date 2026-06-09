package br.ifg.urt.gamercatalog_api.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import br.ifg.urt.gamercatalog_api.dto.request.JogoRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.JogoResponseDTO;
import br.ifg.urt.gamercatalog_api.model.Jogo;

@Mapper(componentModel = "spring")
public interface JogoMapper {

    @Mapping(target = "nome", source = "titulo") // Transforma titulo em nome no DTO
    JogoResponseDTO toResponseDTO(Jogo j);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "titulo", source = "nome") // Transforma nome do DTO em titulo na Entity
    @Mapping(target = "estudio.id", source = "estudioId")
    @Mapping(target = "publisher.id", source = "publisherId")
    Jogo toEntity(JogoRequestDTO dto);

    List<JogoResponseDTO> toResponseDTOList(List<Jogo> jogos);
}