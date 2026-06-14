package br.ifg.urt.gamercatalog_api.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import br.ifg.urt.gamercatalog_api.dto.request.ConquistaRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.ConquistaResponseDTO;
import br.ifg.urt.gamercatalog_api.model.Conquista;

@Mapper(componentModel = "spring")
public interface ConquistaMapper {

    // 1. Mapeia o título do jogo para a saída do Response
    @Mapping(target = "nomeJogo", source = "jogo.titulo") 
    ConquistaResponseDTO toResponseDTO(Conquista conquista);

    // 2. CORREÇÃO: Mapeia o Long usuarioId explicitamente para dentro do ID do objeto Usuario
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario.id", source = "usuarioId") // <-- Isso resolve o erro do terminal!
    @Mapping(target = "jogo", ignore = true) 
    Conquista toEntity(ConquistaRequestDTO dto);

    List<ConquistaResponseDTO> toResponseDTOList(List<Conquista> conquistas);
}