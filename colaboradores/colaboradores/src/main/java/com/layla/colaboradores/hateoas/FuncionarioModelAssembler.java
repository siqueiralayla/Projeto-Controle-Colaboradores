package com.layla.colaboradores.hateoas;

import com.layla.colaboradores.controller.apirest.FuncionarioControllerAPI;
import com.layla.colaboradores.entity.Funcionario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class FuncionarioModelAssembler implements RepresentationModelAssembler<Funcionario, EntityModel<Funcionario>> {

    @Override
    public EntityModel<Funcionario> toModel(Funcionario funcionario) {
        return EntityModel.of(funcionario,
                linkTo(methodOn(FuncionarioControllerAPI.class).getFuncionarioById(funcionario.getId())).withSelfRel(),
                linkTo(methodOn(FuncionarioControllerAPI.class).getAllFuncionarios()).withRel("todos-funcionarios")
        );
    }
}
