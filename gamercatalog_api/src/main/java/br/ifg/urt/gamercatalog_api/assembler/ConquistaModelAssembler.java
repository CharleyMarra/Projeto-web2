package br.ifg.urt.gamercatalog_api.assembler;

import br.ifg.urt.gamercatalog_api.controller.ConquistaController;
import br.ifg.urt.gamercatalog_api.dto.response.ConquistaResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ConquistaModelAssembler implements RepresentationModelAssembler<ConquistaResponseDTO, EntityModel<ConquistaResponseDTO>> {
    @Override
    public EntityModel<ConquistaResponseDTO> toModel(ConquistaResponseDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(ConquistaController.class).buscarPorId(dto.id())).withSelfRel(),
                linkTo(methodOn(ConquistaController.class).atualizar(dto.id(), null)).withRel("atualizar"),
                linkTo(methodOn(ConquistaController.class).deletar(dto.id())).withRel("deletar")
        );
    }
}