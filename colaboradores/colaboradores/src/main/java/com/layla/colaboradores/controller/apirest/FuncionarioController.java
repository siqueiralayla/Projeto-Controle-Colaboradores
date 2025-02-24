package com.layla.colaboradores.controller.apirest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/funcionarios")
@Tag(name = "FuncionarioController", description = "Gerenciamento de Funcionários")
public class FuncionarioController {

    private final List<Funcionario> funcionarios = new ArrayList<>();
    private final List<CargoController.Cargo> cargos = new ArrayList<>();

    public FuncionarioController() {
        // Criando Cargos mockados
        CargoController.Cargo analistaRH = new CargoController.Cargo(1L, "Analista de RH", new DepartamentoController.Departamento(1L, "RH"));
        CargoController.Cargo gerenteFinanceiro = new CargoController.Cargo(2L, "Gerente Financeiro", new DepartamentoController.Departamento(2L, "Financeiro"));

        cargos.add(analistaRH);
        cargos.add(gerenteFinanceiro);

        // Criando Funcionários mockados
        funcionarios.add(new Funcionario(1L, "João Silva", 5000.00, LocalDate.of(2022, 5, 10), null, analistaRH));
        funcionarios.add(new Funcionario(2L, "Maria Souza", 7000.00, LocalDate.of(2021, 8, 20), LocalDate.of(2024, 1, 15), gerenteFinanceiro));
    }

    @Operation(summary = "Lista todos os funcionários")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de funcionários retornada com sucesso")
    })
    @GetMapping("/listar")
    public ResponseEntity<List<Funcionario>> getAllFuncionarios() {
        return ResponseEntity.ok(funcionarios);
    }

    @Operation(summary = "Obtém um funcionário pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionário encontrado"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> getFuncionarioById(@PathVariable Long id) {
        Optional<Funcionario> funcionario = funcionarios.stream()
                .filter(f -> f.getId().equals(id))
                .findFirst();

        return funcionario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Cria um novo funcionário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Funcionário criado com sucesso")
    })
    @PostMapping("/criar")
    public ResponseEntity<Funcionario> createFuncionario(@RequestBody Funcionario novoFuncionario) {
        novoFuncionario.setId((long) (funcionarios.size() + 1)); // Simulando ID auto incrementável
        funcionarios.add(novoFuncionario);
        return ResponseEntity.status(201).body(novoFuncionario);
    }

    @Operation(summary = "Atualiza um funcionário existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado")
    })
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Funcionario> updateFuncionario(@PathVariable Long id, @RequestBody Funcionario funcionarioAtualizado) {
        for (int i = 0; i < funcionarios.size(); i++) {
            if (funcionarios.get(i).getId().equals(id)) {
                funcionarios.set(i, new Funcionario(
                        id,
                        funcionarioAtualizado.getNome(),
                        funcionarioAtualizado.getSalario(),
                        funcionarioAtualizado.getDataEntrada(),
                        funcionarioAtualizado.getDataSaida(),
                        funcionarioAtualizado.getCargo()
                ));
                return ResponseEntity.ok(funcionarios.get(i));
            }
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Exclui um funcionário pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Funcionário excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado")
    })
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> deleteFuncionario(@PathVariable Long id) {
        boolean removed = funcionarios.removeIf(f -> f.getId().equals(id));
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Classe interna representando um Funcionário
    static class Funcionario {
        private Long id;
        private String nome;
        private double salario;
        private LocalDate dataEntrada;
        private LocalDate dataSaida; // Opcional
        private CargoController.Cargo cargo;

        public Funcionario() {}

        public Funcionario(Long id, String nome, double salario, LocalDate dataEntrada, LocalDate dataSaida, CargoController.Cargo cargo) {
            this.id = id;
            this.nome = nome;
            this.salario = salario;
            this.dataEntrada = dataEntrada;
            this.dataSaida = dataSaida;
            this.cargo = cargo;
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

        public double getSalario() {
            return salario;
        }

        public void setSalario(double salario) {
            this.salario = salario;
        }

        public LocalDate getDataEntrada() {
            return dataEntrada;
        }

        public void setDataEntrada(LocalDate dataEntrada) {
            this.dataEntrada = dataEntrada;
        }

        public LocalDate getDataSaida() {
            return dataSaida;
        }

        public void setDataSaida(LocalDate dataSaida) {
            this.dataSaida = dataSaida;
        }

        public CargoController.Cargo getCargo() {
            return cargo;
        }

        public void setCargo(CargoController.Cargo cargo) {
            this.cargo = cargo;
        }
    }
}
