package br.ifg.urt.gamercatalog_api.assembler;

import br.ifg.urt.gamercatalog_api.controller.PlataformaController;
import br.ifg.urt.gamercatalog_api.dto.response.PlataformaResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PlataformaModelAssembler implements RepresentationModelAssembler<PlataformaResponseDTO, EntityModel<PlataformaResponseDTO>> {
    @Override
    public EntityModel<PlataformaResponseDTO> toModel(PlataformaResponseDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(PlataformaController.class).buscarPorId(dto.id())).withSelfRel(),
                linkTo(methodOn(PlataformaController.class).atualizar(dto.id(), null)).withRel("atualizar"),
                linkTo(methodOn(PlataformaController.class).deletar(dto.id())).withRel("deletar")
        );
    }
}