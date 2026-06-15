package br.ifg.urt.gamercatalog_api.assembler;

import br.ifg.urt.gamercatalog_api.controller.FavoritosController;
import br.ifg.urt.gamercatalog_api.dto.response.FavoritosResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FavoritosModelAssembler implements RepresentationModelAssembler<FavoritosResponseDTO, EntityModel<FavoritosResponseDTO>> {
    @Override
    public EntityModel<FavoritosResponseDTO> toModel(FavoritosResponseDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(FavoritosController.class).buscarPorId(dto.idFavorito())).withSelfRel(),
                linkTo(methodOn(FavoritosController.class).deletar(dto.idFavorito())).withRel("deletar")
        );
    }
}