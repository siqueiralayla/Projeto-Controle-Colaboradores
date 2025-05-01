package com.layla.colaboradores.controller.apirest;

import com.layla.colaboradores.entity.Departamento;
import com.layla.colaboradores.exception.RecursoNaoEncontradoException;
import com.layla.colaboradores.hateoas.DepartamentoModelAssembler;
import com.layla.colaboradores.service.DepartamentoService;

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
@RequestMapping("/departamentos")
@Tag(name = "DepartamentoController", description = "Gerenciamento de Departamentos")
public class DepartamentoControllerAPI {

    @Autowired
    private DepartamentoService departamentoService;

    @Autowired
    private DepartamentoModelAssembler assembler;

    @Operation(summary = "Lista todos os departamentos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de departamentos retornada com sucesso")
    })
    @GetMapping
    public CollectionModel<EntityModel<Departamento>> getAllDepartamentos() {
        List<Departamento> lista = departamentoService.buscarTodos();
        List<EntityModel<Departamento>> models = lista.stream()
                .map(assembler::toModel)
                .toList();

        return CollectionModel.of(models, linkTo(methodOn(DepartamentoControllerAPI.class).getAllDepartamentos()).withSelfRel());
    }

    @Operation(summary = "Obtém um departamento pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Departamento encontrado"),
            @ApiResponse(responseCode = "404", description = "Departamento não encontrado")
    })
    @GetMapping("/{id}")
    public EntityModel<Departamento> getDepartamentoById(@PathVariable Long id) {
        Departamento departamento = departamentoService.buscarPorId(id);

        if (departamento == null) {
            throw new RecursoNaoEncontradoException("Departamento com ID " + id + " não encontrado");
        }

        return assembler.toModel(departamento);
    }

    @Operation(summary = "Cria um novo departamento")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Departamento criado com sucesso")
    })
    @PostMapping
    public ResponseEntity<EntityModel<Departamento>> createDepartamento(@RequestBody Departamento novoDepartamento) {
        departamentoService.salvar(novoDepartamento);
        return ResponseEntity
                .created(linkTo(methodOn(DepartamentoControllerAPI.class).getDepartamentoById(novoDepartamento.getId())).toUri())
                .body(assembler.toModel(novoDepartamento));
    }

    @Operation(summary = "Atualiza um departamento existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Departamento atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Departamento não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Departamento>> updateDepartamento(@PathVariable Long id, @RequestBody Departamento dadosAtualizados) {
        Departamento existente = departamentoService.buscarPorId(id);
        if (existente == null) {
            throw new RecursoNaoEncontradoException("Departamento com ID " + id + " não encontrado");
        }

        existente.setNome(dadosAtualizados.getNome());
        departamentoService.editar(existente);
        return ResponseEntity.ok(assembler.toModel(existente));
    }

    @Operation(summary = "Exclui um departamento pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Departamento excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Departamento não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartamento(@PathVariable Long id) {
        Departamento existente = departamentoService.buscarPorId(id);
        if (existente == null) {
            throw new RecursoNaoEncontradoException("Departamento com ID " + id + " não encontrado");
        }

        departamentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}