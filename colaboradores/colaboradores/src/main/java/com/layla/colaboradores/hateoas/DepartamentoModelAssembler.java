package com.layla.colaboradores.hateoas;

import com.layla.colaboradores.controller.apirest.DepartamentoController;
import com.layla.colaboradores.entity.Departamento;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class DepartamentoModelAssembler implements RepresentationModelAssembler<Departamento, EntityModel<Departamento>> {

    @Override
    public EntityModel<Departamento> toModel(Departamento departamento) {
        return EntityModel.of(departamento,
                linkTo(methodOn(DepartamentoController.class).getDepartamentoById(departamento.getId())).withSelfRel(),
                linkTo(methodOn(DepartamentoController.class).getAllDepartamentos()).withRel("todos-departamentos")
        );
    }
}
