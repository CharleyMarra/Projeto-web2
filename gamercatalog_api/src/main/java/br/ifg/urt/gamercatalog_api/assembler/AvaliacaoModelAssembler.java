package br.ifg.urt.gamercatalog_api.assembler;

import br.ifg.urt.gamercatalog_api.controller.AvaliacaoController;
import br.ifg.urt.gamercatalog_api.dto.response.AvaliacaoResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AvaliacaoModelAssembler implements RepresentationModelAssembler<AvaliacaoResponseDTO, EntityModel<AvaliacaoResponseDTO>> {
    @Override
    public EntityModel<AvaliacaoResponseDTO> toModel(AvaliacaoResponseDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(AvaliacaoController.class).buscarPorId(dto.idAvaliacao())).withSelfRel(),
                linkTo(methodOn(AvaliacaoController.class).atualizar(dto.idAvaliacao(), null)).withRel("atualizar"),
                linkTo(methodOn(AvaliacaoController.class).deletar(dto.idAvaliacao())).withRel("deletar")
        );
    }
}