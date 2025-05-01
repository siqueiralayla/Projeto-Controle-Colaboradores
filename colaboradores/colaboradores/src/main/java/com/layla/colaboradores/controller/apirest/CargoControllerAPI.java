package com.layla.colaboradores.controller.apirest;

import com.layla.colaboradores.entity.Cargo;
import com.layla.colaboradores.exception.RecursoNaoEncontradoException;
import com.layla.colaboradores.hateoas.CargoModelAssembler;
import com.layla.colaboradores.service.CargoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/cargos")
@Tag(name = "CargoController", description = "Gerenciamento de Cargos")
public class CargoControllerAPI {

    @Autowired
    private CargoService cargoService;

    @Autowired
    private CargoModelAssembler assembler;

    @Operation(summary = "Lista todos os cargos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de cargos retornada com sucesso")
    })
    @GetMapping
    public CollectionModel<EntityModel<Cargo>> getAllCargos() {
        List<Cargo> cargos = cargoService.buscarTodos();
        List<EntityModel<Cargo>> models = cargos.stream().map(assembler::toModel).toList();

        return CollectionModel.of(models, linkTo(methodOn(CargoControllerAPI.class).getAllCargos()).withSelfRel());
    }

    @Operation(summary = "Obtém um cargo pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cargo encontrado"),
            @ApiResponse(responseCode = "404", description = "Cargo não encontrado")
    })
    @GetMapping("/{id}")
    public EntityModel<Cargo> getCargoById(@PathVariable Long id) {
        Cargo cargo = cargoService.buscarPorId(id);
        if (cargo == null) {
            throw new RecursoNaoEncontradoException("Cargo com ID " + id + " não encontrado");
        }
        return assembler.toModel(cargo);
    }

    @Operation(summary = "Cria um novo cargo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cargo criado com sucesso")
    })
    @PostMapping
    public ResponseEntity<EntityModel<Cargo>> createCargo(@RequestBody Cargo novoCargo) {
        cargoService.salvar(novoCargo);
        return ResponseEntity.created(
                        linkTo(methodOn(CargoControllerAPI.class).getCargoById(novoCargo.getId())).toUri())
                .body(assembler.toModel(novoCargo));
    }

    @Operation(summary = "Atualiza um cargo existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cargo atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cargo não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Cargo>> updateCargo(@PathVariable Long id, @RequestBody Cargo atualizado) {
        Cargo existente = cargoService.buscarPorId(id);
        if (existente == null) {
            throw new RecursoNaoEncontradoException("Cargo com ID " + id + " não encontrado");
        }

        existente.setNome(atualizado.getNome());
        existente.setDepartamento(atualizado.getDepartamento());
        cargoService.editar(existente);

        return ResponseEntity.ok(assembler.toModel(existente));
    }

    @Operation(summary = "Exclui um cargo pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cargo excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cargo não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCargo(@PathVariable Long id) {
        Cargo existente = cargoService.buscarPorId(id);
        if (existente == null) {
            throw new RecursoNaoEncontradoException("Cargo com ID " + id + " não encontrado");
        }
        cargoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
