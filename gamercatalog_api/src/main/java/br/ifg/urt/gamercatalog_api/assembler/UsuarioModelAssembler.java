package br.ifg.urt.gamercatalog_api.assembler;

import br.ifg.urt.gamercatalog_api.controller.UsuarioController;
import br.ifg.urt.gamercatalog_api.dto.response.UsuarioResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<UsuarioResponseDTO, EntityModel<UsuarioResponseDTO>> {
    @Override
    public EntityModel<UsuarioResponseDTO> toModel(UsuarioResponseDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(UsuarioController.class).buscarPorId(dto.id())).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).atualizar(dto.id(), null)).withRel("atualizar"),
                linkTo(methodOn(UsuarioController.class).deletar(dto.id())).withRel("deletar"),
                linkTo(methodOn(UsuarioController.class).buscarTodos(null, null)).withRel("usuarios")
        );
    }
}