package com.layla.colaboradores.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.layla.colaboradores.entity.Cargo;
import com.layla.colaboradores.entity.Departamento;
import com.layla.colaboradores.repository.DepartamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class DepartamentoServiceTest {

    @InjectMocks
    private DepartamentoService departamentoService;

    @Mock
    private DepartamentoRepository departamentoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve buscar Departamento por ID com sucesso.")
    void testBuscarPorId() {
        // Cenário
        Departamento departamentoMock = new Departamento();
        departamentoMock.setId(1L);
        departamentoMock.setNome("TI");

        when(departamentoRepository.findById(1L)).thenReturn(Optional.of(departamentoMock));

        // Ação
        Departamento result = departamentoService.buscarPorId(1L);

        // Verificação
        assertNotNull(result);
        assertEquals("TI", result.getNome());
        verify(departamentoRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve retornar null ao buscar Departamento por ID inexistente.")
    void testBuscarPorIdNaoExistente() {
        // Cenário
        when(departamentoRepository.findById(99L)).thenReturn(Optional.empty());

        // Ação
        Departamento result = departamentoService.buscarPorId(99L);

        // Verificação
        assertNull(result);
        verify(departamentoRepository).findById(99L);
    }

    @Test
    @DisplayName("Deve salvar um novo Departamento com sucesso.")
    void testSalvarDepartamento() {
        // Cenário
        Departamento novoDepartamento = new Departamento();
        novoDepartamento.setNome("Recursos Humanos");

        when(departamentoRepository.save(any(Departamento.class))).thenReturn(novoDepartamento);

        // Ação
        departamentoService.salvar(novoDepartamento);

        // Verificação
        verify(departamentoRepository).save(novoDepartamento);
    }

    @Test
    @DisplayName("Deve editar um Departamento existente com sucesso.")
    void testEditarDepartamento() {
        // Cenário
        Departamento departamentoExistente = new Departamento();
        departamentoExistente.setId(1L);
        departamentoExistente.setNome("Financeiro");

        when(departamentoRepository.save(any(Departamento.class))).thenReturn(departamentoExistente);

        // Ação
        departamentoService.editar(departamentoExistente);

        // Verificação
        verify(departamentoRepository).save(departamentoExistente);
    }

    @Test
    @DisplayName("Deve excluir um Departamento por ID com sucesso.")
    void testExcluirDepartamento() {
        // Ação
        departamentoService.excluir(1L);

        // Verificação
        verify(departamentoRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Deve retornar lista de todos os Departamentos.")
    void testBuscarTodos() {
        // Cenário
        Departamento departamento1 = new Departamento();
        departamento1.setId(1L);
        departamento1.setNome("TI");

        Departamento departamento2 = new Departamento();
        departamento2.setId(2L);
        departamento2.setNome("RH");

        List<Departamento> departamentos = Arrays.asList(departamento1, departamento2);

        when(departamentoRepository.findAll()).thenReturn(departamentos);

        // Ação
        List<Departamento> result = departamentoService.buscarTodos();

        // Verificação
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(departamentoRepository).findAll();
    }

    @Test
    @DisplayName("Deve verificar se Departamento possui Cargos associados.")
    void testDepartamentoTemCargos() {
        // Cenário
        Departamento departamentoComCargos = new Departamento();
        departamentoComCargos.setId(1L);
        departamentoComCargos.setNome("TI");

        Cargo cargo1 = new Cargo();
        Cargo cargo2 = new Cargo();
        departamentoComCargos.setCargos(Arrays.asList(cargo1, cargo2));

        when(departamentoRepository.findById(1L)).thenReturn(Optional.of(departamentoComCargos));

        // Ação
        boolean result = departamentoService.departamentoTemCargos(1L);

        // Verificação
        assertTrue(result);
        verify(departamentoRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve retornar false quando Departamento não possui Cargos.")
    void testDepartamentoNaoTemCargos() {
        // Cenário
        Departamento departamentoSemCargos = new Departamento();
        departamentoSemCargos.setId(2L);
        departamentoSemCargos.setNome("Estoque");

        when(departamentoRepository.findById(2L)).thenReturn(Optional.of(departamentoSemCargos));

        // Ação
        boolean result = departamentoService.departamentoTemCargos(2L);

        // Verificação
        assertFalse(result);
        verify(departamentoRepository).findById(2L);
    }
}