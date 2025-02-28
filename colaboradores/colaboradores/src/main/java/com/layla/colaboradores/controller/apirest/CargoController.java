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
@RequestMapping("/cargos")
@Tag(name = "CargoController", description = "Gerenciamento de Cargos")
public class CargoController {

    private final List<Cargo> cargos = new ArrayList<>();
    private final List<DepartamentoController.Departamento> departamentos = new ArrayList<>();

    public CargoController() {
        // Criando departamentos mockados
        DepartamentoController.Departamento rh = new DepartamentoController.Departamento(1L, "RH");
        DepartamentoController.Departamento financeiro = new DepartamentoController.Departamento(2L, "Financeiro");

        departamentos.add(rh);
        departamentos.add(financeiro);

        // Criando cargos mockados e associando a departamentos
        cargos.add(new Cargo(1L, "Analista de RH", rh));
        cargos.add(new Cargo(2L, "Gerente Financeiro", financeiro));
    }

    @Operation(summary = "Lista todos os cargos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de cargos retornada com sucesso")
    })
    @GetMapping("/listar")
    public ResponseEntity<List<Cargo>> getAllCargos() {
        return ResponseEntity.ok(cargos);
    }

    @Operation(summary = "Obtém um cargo pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "CargoRepository.java encontrado"),
            @ApiResponse(responseCode = "404", description = "CargoRepository.java não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Cargo> getCargoById(@PathVariable Long id) {
        Optional<Cargo> cargo = cargos.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();

        return cargo.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cria um novo cargo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CargoRepository.java criado com sucesso")
    })
    @PostMapping("/criar")
    public ResponseEntity<Cargo> createCargo(@RequestBody Cargo novoCargo) {
        novoCargo.setId((long) (cargos.size() + 1)); // Simulando ID auto incrementável
        cargos.add(novoCargo);
        return ResponseEntity.status(201).body(novoCargo);
    }

    @Operation(summary = "Atualiza um cargo existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "CargoRepository.java atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "CargoRepository.java não encontrado")
    })
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Cargo> updateCargo(@PathVariable Long id, @RequestBody Cargo cargoAtualizado) {
        for (int i = 0; i < cargos.size(); i++) {
            if (cargos.get(i).getId().equals(id)) {
                cargos.set(i, new Cargo(id, cargoAtualizado.getNome(), cargoAtualizado.getDepartamento()));
                return ResponseEntity.ok(cargos.get(i));
            }
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Exclui um cargo pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "CargoRepository.java excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "CargoRepository.java não encontrado")
    })
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> deleteCargo(@PathVariable Long id) {
        boolean removed = cargos.removeIf(c -> c.getId().equals(id));
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Classe interna representando um CargoRepository.java
    static class Cargo {
        private Long id;
        private String nome;
        private DepartamentoController.Departamento departamento;

        public Cargo() {}

        public Cargo(Long id, String nome, DepartamentoController.Departamento departamento) {
            this.id = id;
            this.nome = nome;
            this.departamento = departamento;
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

        public DepartamentoController.Departamento getDepartamento() {
            return departamento;
        }

        public void setDepartamento(DepartamentoController.Departamento departamento) {
            this.departamento = departamento;
        }
    }
}
