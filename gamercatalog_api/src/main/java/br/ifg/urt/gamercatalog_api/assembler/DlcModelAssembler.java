package br.ifg.urt.gamercatalog_api.assembler;

import br.ifg.urt.gamercatalog_api.controller.DlcController;
import br.ifg.urt.gamercatalog_api.dto.response.DlcResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DlcModelAssembler implements RepresentationModelAssembler<DlcResponseDTO, EntityModel<DlcResponseDTO>> {
    @Override
    public EntityModel<DlcResponseDTO> toModel(DlcResponseDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(DlcController.class).buscarPorId(dto.id())).withSelfRel(),
                linkTo(methodOn(DlcController.class).atualizar(dto.id(), null)).withRel("atualizar"),
                linkTo(methodOn(DlcController.class).deletar(dto.id())).withRel("deletar")
        );
    }
}