package br.ifg.urt.gamercatalog_api.assembler;

import br.ifg.urt.gamercatalog_api.controller.ComentariosController;
import br.ifg.urt.gamercatalog_api.dto.response.ComentariosResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ComentariosModelAssembler implements RepresentationModelAssembler<ComentariosResponseDTO, EntityModel<ComentariosResponseDTO>> {
    @Override
    public EntityModel<ComentariosResponseDTO> toModel(ComentariosResponseDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(ComentariosController.class).buscarPorId(dto.idComentario())).withSelfRel(),
                linkTo(methodOn(ComentariosController.class).atualizar(dto.idComentario(), null)).withRel("atualizar"),
                linkTo(methodOn(ComentariosController.class).deletar(dto.idComentario())).withRel("deletar")
        );
    }
}