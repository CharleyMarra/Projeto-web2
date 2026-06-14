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
    @Mapping(target = "preco", source = "preco.valor")
    // Usa o método do VO para preencher o precoFormatado [cite: 2161]
    @Mapping(target = "precoFormatado", expression = "java(j.getPreco() != null ? j.getPreco().getFormatado() : null)")
    JogoResponseDTO toResponseDTO(Jogo j);

    // Mapeamento de Request DTO para Entidade (com VO)
    // O MapStruct precisa saber como construir o record Preco a partir do Double do DTO [cite: 2167]
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "titulo", source = "nome")
    @Mapping(target = "preco.valor", source = "preco") // Diz que o 'preco' do DTO vai para dentro do 'valor' do VO
    @Mapping(target = "preco.moeda", constant = "BRL") // Define moeda padrão na criação [cite: 2169]
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