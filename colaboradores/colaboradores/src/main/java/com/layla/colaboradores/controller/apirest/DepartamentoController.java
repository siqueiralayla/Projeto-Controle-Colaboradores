package com.layla.colaboradores.controller.apirest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/departamentos")
@Tag(name = "DepartamentoController", description = "Gerenciamento de Departamentos")
public class DepartamentoController {

    private final List<Departamento> departamentos = new ArrayList<>();

    public DepartamentoController() {
        // Mock de dados iniciais
        departamentos.add(new Departamento(1L, "RH"));
        departamentos.add(new Departamento(2L, "Financeiro"));
    }

    @Operation(summary = "Lista todos os departamentos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de departamentos retornada com sucesso")
    })
    @GetMapping("/listar")
    public ResponseEntity<List<Departamento>> getAllDepartamentos() {
        return ResponseEntity.ok(departamentos);
    }

    @Operation(summary = "Obtém um departamento pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Departamento encontrado"),
            @ApiResponse(responseCode = "404", description = "Departamento não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Departamento> getDepartamentoById(@PathVariable Long id) {
        Optional<Departamento> departamento = departamentos.stream()
                .filter(dep -> dep.getId().equals(id))
                .findFirst();

        return departamento.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cria um novo departamento")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Departamento criado com sucesso")
    })
    @PostMapping("/criar")
    public ResponseEntity<Departamento> createDepartamento(@RequestBody Departamento novoDepartamento) {
        novoDepartamento.setId((long) (departamentos.size() + 1)); // Simulando um ID auto incrementável
        departamentos.add(novoDepartamento);
        return ResponseEntity.status(201).body(novoDepartamento);
    }

    @Operation(summary = "Atualiza um departamento existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Departamento atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Departamento não encontrado")
    })
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Departamento> updateDepartamento(@PathVariable Long id, @RequestBody Departamento departamentoAtualizado) {
        for (int i = 0; i < departamentos.size(); i++) {
            if (departamentos.get(i).getId().equals(id)) {
                departamentos.set(i, new Departamento(id, departamentoAtualizado.getNome()));
                return ResponseEntity.ok(departamentos.get(i));
            }
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Exclui um departamento pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Departamento excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Departamento não encontrado")
    })
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> deleteDepartamento(@PathVariable Long id) {
        boolean removed = departamentos.removeIf(dep -> dep.getId().equals(id));
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Classe interna representando um Departamento
    static class Departamento {
        private Long id;
        private String nome;

        public Departamento() {}

        public Departamento(Long id, String nome) {
            this.id = id;
            this.nome = nome;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }
    }
}
