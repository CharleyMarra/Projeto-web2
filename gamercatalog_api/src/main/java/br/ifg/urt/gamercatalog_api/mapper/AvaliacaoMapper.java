package br.ifg.urt.gamercatalog_api.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import br.ifg.urt.gamercatalog_api.dto.request.AvaliacaoRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.AvaliacaoResponseDTO;
import br.ifg.urt.gamercatalog_api.model.Avaliacao;

@Mapper(componentModel = "spring")
public interface AvaliacaoMapper {

    @Mapping(target = "nomeUsuario", source = "usuario.nome")
    @Mapping(target = "nomeJogo", source = "jogo.titulo") // Alterado para titulo
    AvaliacaoResponseDTO toResponseDTO(Avaliacao a);

    @Mapping(target = "idAvaliacao", ignore = true)
    @Mapping(target = "dataPostagem", ignore = true)
    @Mapping(target = "usuario.id", source = "usuarioId")
    @Mapping(target = "jogo.id", source = "jogoId")
    Avaliacao toEntity(AvaliacaoRequestDTO dto);

    List<AvaliacaoResponseDTO> toResponseDTOList(List<Avaliacao> lista);
}