package com.layla.colaboradores.controller.apirest;

import com.layla.colaboradores.entity.Funcionario;
import com.layla.colaboradores.exception.RecursoNaoEncontradoException;
import com.layla.colaboradores.hateoas.FuncionarioModelAssembler;
import com.layla.colaboradores.service.FuncionarioService;

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
@RequestMapping("/funcionarios")
@Tag(name = "FuncionarioController", description = "Gerenciamento de Funcionários")
public class FuncionarioControllerAPI {

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private FuncionarioModelAssembler assembler;

    @Operation(summary = "Lista todos os funcionários")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de funcionários retornada com sucesso")
    })
    @GetMapping
    public CollectionModel<EntityModel<Funcionario>> getAllFuncionarios() {
        List<Funcionario> funcionarios = funcionarioService.buscarTodos();
        List<EntityModel<Funcionario>> models = funcionarios.stream().map(assembler::toModel).toList();

        return CollectionModel.of(models, linkTo(methodOn(FuncionarioControllerAPI.class).getAllFuncionarios()).withSelfRel());
    }

    @Operation(summary = "Obtém um funcionário pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionário encontrado"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado")
    })
    @GetMapping("/{id}")
    public EntityModel<Funcionario> getFuncionarioById(@PathVariable Long id) {
        Funcionario funcionario = funcionarioService.buscarPorId(id);
        if (funcionario == null) {
            throw new RecursoNaoEncontradoException("Funcionário com ID " + id + " não encontrado");
        }
        return assembler.toModel(funcionario);
    }

    @Operation(summary = "Cria um novo funcionário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Funcionário criado com sucesso")
    })
    @PostMapping
    public ResponseEntity<EntityModel<Funcionario>> createFuncionario(@RequestBody Funcionario novoFuncionario) {
        funcionarioService.salvar(novoFuncionario);
        return ResponseEntity.created(
                        linkTo(methodOn(FuncionarioControllerAPI.class).getFuncionarioById(novoFuncionario.getId())).toUri())
                .body(assembler.toModel(novoFuncionario));
    }

    @Operation(summary = "Atualiza um funcionário existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Funcionario>> updateFuncionario(@PathVariable Long id, @RequestBody Funcionario atualizado) {
        Funcionario existente = funcionarioService.buscarPorId(id);
        if (existente == null) {
            throw new RecursoNaoEncontradoException("Funcionário com ID " + id + " não encontrado");
        }

        existente.setNome(atualizado.getNome());
        existente.setSalario(atualizado.getSalario());
        existente.setDataEntrada(atualizado.getDataEntrada());
        existente.setDataSaida(atualizado.getDataSaida());
        existente.setCargo(atualizado.getCargo());

        funcionarioService.editar(existente);
        return ResponseEntity.ok(assembler.toModel(existente));
    }

    @Operation(summary = "Exclui um funcionário pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Funcionário excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuncionario(@PathVariable Long id) {
        Funcionario existente = funcionarioService.buscarPorId(id);
        if (existente == null) {
            throw new RecursoNaoEncontradoException("Funcionário com ID " + id + " não encontrado");
        }
        funcionarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
