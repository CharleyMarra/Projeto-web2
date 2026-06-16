package br.ifg.urt.gamercatalog_api.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import br.ifg.urt.gamercatalog_api.dto.request.JogoRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.JogoResponseDTO;
import br.ifg.urt.gamercatalog_api.model.Jogo;
import br.ifg.urt.gamercatalog_api.model.vo.Preco;

@Mapper(componentModel = "spring")
public interface JogoMapper {

    // 1. ENSINAMOS O MAPSTRUCT COMO CONVERTER O DOUBLE PARA O RECORD PRECO
    default Preco mapDoubleToPreco(Double valor) {
        if (valor == null) {
            return null;
        }
        return new Preco(valor, "BRL"); // Já cria o VO se autovalidando e com a moeda padrão
    }

    @Mapping(target = "nome", source = "titulo")
    @Mapping(target = "preco", source = "preco.valor")
    @Mapping(target = "precoFormatado", expression = "java(j.getPreco() != null ? j.getPreco().getFormatado() : null)")
    JogoResponseDTO toResponseDTO(Jogo j);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "titulo", source = "nome")
    @Mapping(target = "preco", source = "preco")
    @Mapping(target = "estudio", ignore = true)  
    @Mapping(target = "publisher", ignore = true)
    @Mapping(target = "plataforma", ignore = true) 
    @Mapping(target = "avaliacoes", ignore = true) 
    @Mapping(target = "comentarios", ignore = true)
    @Mapping(target = "conquistas", ignore = true)
    @Mapping(target = "favoritos", ignore = true)
    @Mapping(target = "dlcs", ignore = true) 
    Jogo toEntity(JogoRequestDTO dto);

    List<JogoResponseDTO> toResponseDTOList(List<Jogo> jogos);
}