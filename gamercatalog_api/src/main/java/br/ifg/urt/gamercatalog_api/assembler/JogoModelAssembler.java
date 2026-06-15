package br.ifg.urt.gamercatalog_api.assembler;

import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import br.ifg.urt.gamercatalog_api.controller.JogoController;
import br.ifg.urt.gamercatalog_api.controller.AvaliacaoController;
import br.ifg.urt.gamercatalog_api.dto.response.JogoResponseDTO;

@Component
public class JogoModelAssembler implements RepresentationModelAssembler<JogoResponseDTO, EntityModel<JogoResponseDTO>> {

    @Override
    public EntityModel<JogoResponseDTO> toModel(JogoResponseDTO dto) {
        // Cria um envelope ao redor do seu DTO, onde colocaremos os links
        EntityModel<JogoResponseDTO> model = EntityModel.of(dto);

        // Link Self: Busca o próprio jogo e define que este link aponta para o próprio recurso
        model.add(linkTo(methodOn(JogoController.class).buscarPorId(dto.id())).withSelfRel());

        // Link para Atualizar Jogo
        model.add(linkTo(methodOn(JogoController.class).atualizar(dto.id(), null)).withRel("atualizar-jogo"));

        // HATEOAS Dinâmico: Você pode interligar seus recursos. 
        // Exemplo: criar um link apontando para as avaliações deste jogo
        model.add(linkTo(methodOn(AvaliacaoController.class).buscarTodos(dto.id(), null, null)).withRel("avaliacoes"));

        return model;
    }
}