package br.ifg.urt.gamercatalog_api.mapper;

import br.ifg.urt.gamercatalog_api.dto.request.JogoRequestDTO;
import br.ifg.urt.gamercatalog_api.dto.response.JogoResponseDTO;
import br.ifg.urt.gamercatalog_api.model.Jogo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PlataformaMapper.class, EstudioMapper.class, PublisherMapper.class})
public interface JogoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estudio", ignore = true)
    @Mapping(target = "publisher", ignore = true)
    @Mapping(target = "plataforma", ignore = true)
    @Mapping(target = "titulo", source = "nome") 
    // CORREÇÃO 1: Adicionado "BRL" como segundo parâmetro do construtor
    @Mapping(target = "preco", expression = "java(new br.ifg.urt.gamercatalog_api.model.vo.Preco(dto.preco(), \"BRL\"))")
    Jogo toEntity(JogoRequestDTO dto);

    @Mapping(target = "nome", source = "titulo") 
    @Mapping(target = "preco", source = "preco.valor")
    // CORREÇÃO 2: Alterado de precoFormatado() para getFormatado()
    @Mapping(target = "precoFormatado", expression = "java(entity.getPreco() != null ? entity.getPreco().getFormatado() : null)")
    JogoResponseDTO toResponseDTO(Jogo entity);
}