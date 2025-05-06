package com.layla.colaboradores.hateoas;

import com.layla.colaboradores.controller.apirest.CargoControllerAPI;
import com.layla.colaboradores.entity.Cargo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CargoModelAssembler implements RepresentationModelAssembler<Cargo, EntityModel<Cargo>> {

    @Override
    public EntityModel<Cargo> toModel(Cargo cargo) {
        return EntityModel.of(cargo,
                linkTo(methodOn(CargoControllerAPI.class).getCargoById(cargo.getId())).withSelfRel(),
                linkTo(methodOn(CargoControllerAPI.class).getAllCargos()).withRel("todos-cargos")
        );
    }
}
