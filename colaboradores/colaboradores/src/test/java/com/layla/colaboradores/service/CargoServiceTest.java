package com.layla.colaboradores.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.layla.colaboradores.entity.Cargo;
import com.layla.colaboradores.entity.Departamento;
import com.layla.colaboradores.entity.Funcionario;
import com.layla.colaboradores.repository.CargoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class CargoServiceTest {

    @InjectMocks
    private CargoService cargoService;

    @Mock
    private CargoRepository cargoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve buscar Cargo por ID com sucesso.")
    void testBuscarPorId() {
        // Cenário
        Departamento departamento = new Departamento();
        departamento.setId(1L);
        departamento.setNome("TI");

        Cargo cargoMock = new Cargo();
        cargoMock.setId(1L);
        cargoMock.setNome("Desenvolvedor");
        cargoMock.setDepartamento(departamento);

        when(cargoRepository.findById(1L)).thenReturn(Optional.of(cargoMock));

        // Ação
        Cargo result = cargoService.buscarPorId(1L);

        // Verificação
        assertNotNull(result);
        assertEquals("Desenvolvedor", result.getNome());
        assertEquals("TI", result.getDepartamento().getNome());
        verify(cargoRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve retornar null ao buscar Cargo por ID inexistente.")
    void testBuscarPorIdNaoExistente() {
        // Cenário
        when(cargoRepository.findById(99L)).thenReturn(Optional.empty());

        // Ação
        Cargo result = cargoService.buscarPorId(99L);

        // Verificação
        assertNull(result);
        verify(cargoRepository).findById(99L);
    }

    @Test
    @DisplayName("Deve salvar um novo Cargo com sucesso.")
    void testSalvarCargo() {
        // Cenário
        Departamento departamento = new Departamento();
        departamento.setId(1L);
        departamento.setNome("RH");

        Cargo cargoNovo = new Cargo();
        cargoNovo.setNome("Coordenador");
        cargoNovo.setDepartamento(departamento);

        Cargo cargoSalvo = new Cargo();
        cargoSalvo.setId(2L);
        cargoSalvo.setNome("Coordenador");
        cargoSalvo.setDepartamento(departamento);

        when(cargoRepository.save(any(Cargo.class))).thenReturn(cargoSalvo);

        // Ação
        cargoService.salvar(cargoNovo);

        // Verificação
        verify(cargoRepository).save(cargoNovo);
    }

    @Test
    @DisplayName("Deve editar um Cargo existente com sucesso.")
    void testEditarCargo() {
        // Cenário
        Departamento departamento = new Departamento();
        departamento.setId(2L);
        departamento.setNome("Financeiro");

        Cargo cargoExistente = new Cargo();
        cargoExistente.setId(1L);
        cargoExistente.setNome("Analista");
        cargoExistente.setDepartamento(departamento);

        when(cargoRepository.save(any(Cargo.class))).thenReturn(cargoExistente);

        // Ação
        cargoService.editar(cargoExistente);

        // Verificação
        verify(cargoRepository).save(cargoExistente);
    }

    @Test
    @DisplayName("Deve excluir um Cargo por ID com sucesso.")
    void testExcluirCargo() {
        // Ação
        cargoService.excluir(1L);

        // Verificação
        verify(cargoRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Deve retornar lista de todos os Cargos.")
    void testBuscarTodos() {
        // Cenário
        Departamento departamentoTI = new Departamento();
        departamentoTI.setId(1L);
        departamentoTI.setNome("TI");

        Cargo cargo1 = new Cargo();
        cargo1.setId(1L);
        cargo1.setNome("Desenvolvedor");
        cargo1.setDepartamento(departamentoTI);

        Cargo cargo2 = new Cargo();
        cargo2.setId(2L);
        cargo2.setNome("Gerente");
        cargo2.setDepartamento(departamentoTI);

        List<Cargo> cargos = Arrays.asList(cargo1, cargo2);

        when(cargoRepository.findAll()).thenReturn(cargos);

        // Ação
        List<Cargo> result = cargoService.buscarTodos();

        // Verificação
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(cargoRepository).findAll();
    }

    @Test
    @DisplayName("Deve verificar se Cargo possui funcionários associados.")
    void testCargoTemFuncionarios() {
        // Cenário
        Cargo cargoComFuncionarios = new Cargo();
        cargoComFuncionarios.setId(1L);
        cargoComFuncionarios.setNome("Supervisor");

        Funcionario funcionario1 = new Funcionario();
        Funcionario funcionario2 = new Funcionario();
        cargoComFuncionarios.setFuncionarios(Arrays.asList(funcionario1, funcionario2));

        when(cargoRepository.findById(1L)).thenReturn(Optional.of(cargoComFuncionarios));

        // Ação
        boolean result = cargoService.cargoTemFuncionarios(1L);

        // Verificação
        assertTrue(result);
        verify(cargoRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve retornar false quando Cargo não possui funcionários.")
    void testCargoNaoTemFuncionarios() {
        // Cenário
        Cargo cargoSemFuncionarios = new Cargo();
        cargoSemFuncionarios.setId(2L);
        cargoSemFuncionarios.setNome("Estagiário");

        when(cargoRepository.findById(2L)).thenReturn(Optional.of(cargoSemFuncionarios));

        // Ação
        boolean result = cargoService.cargoTemFuncionarios(2L);

        // Verificação
        assertFalse(result);
        verify(cargoRepository).findById(2L);
    }
}