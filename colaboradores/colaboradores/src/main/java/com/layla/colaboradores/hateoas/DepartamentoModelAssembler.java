package com.layla.colaboradores.hateoas;

import com.layla.colaboradores.controller.apirest.DepartamentoControllerAPI;
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
                linkTo(methodOn(DepartamentoControllerAPI.class).getDepartamentoById(departamento.getId())).withSelfRel(),
                linkTo(methodOn(DepartamentoControllerAPI.class).getAllDepartamentos()).withRel("todos-departamentos")
        );
    }
}
