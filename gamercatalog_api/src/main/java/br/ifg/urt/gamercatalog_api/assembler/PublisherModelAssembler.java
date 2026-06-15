package br.ifg.urt.gamercatalog_api.assembler;

import br.ifg.urt.gamercatalog_api.controller.PublisherController;
import br.ifg.urt.gamercatalog_api.dto.response.PublisherResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PublisherModelAssembler implements RepresentationModelAssembler<PublisherResponseDTO, EntityModel<PublisherResponseDTO>> {
    @Override
    public EntityModel<PublisherResponseDTO> toModel(PublisherResponseDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(PublisherController.class).buscarPorId(dto.id())).withSelfRel(),
                linkTo(methodOn(PublisherController.class).atualizar(dto.id(), null)).withRel("atualizar"),
                linkTo(methodOn(PublisherController.class).deletar(dto.id())).withRel("deletar")
        );
    }
}