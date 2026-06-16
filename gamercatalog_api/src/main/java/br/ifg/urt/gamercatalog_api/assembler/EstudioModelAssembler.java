package br.ifg.urt.gamercatalog_api.assembler;

import br.ifg.urt.gamercatalog_api.controller.EstudioController;
import br.ifg.urt.gamercatalog_api.dto.response.EstudioResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EstudioModelAssembler implements RepresentationModelAssembler<EstudioResponseDTO, EntityModel<EstudioResponseDTO>> {
    @Override
    public EntityModel<EstudioResponseDTO> toModel(EstudioResponseDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(EstudioController.class).buscarPorId(dto.id())).withSelfRel(),
                linkTo(methodOn(EstudioController.class).atualizar(dto.id(), null)).withRel("atualizar"),
                linkTo(methodOn(EstudioController.class).deletar(dto.id())).withRel("deletar")
        );
    }
}