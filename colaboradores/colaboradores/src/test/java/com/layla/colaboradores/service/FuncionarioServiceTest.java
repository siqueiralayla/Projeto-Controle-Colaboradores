package com.layla.colaboradores.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.layla.colaboradores.entity.Funcionario;
import com.layla.colaboradores.repository.FuncionarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class FuncionarioServiceTest {

    @InjectMocks
    private FuncionarioService funcionarioService;

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve buscar Funcionario por ID com sucesso.")
    void testBuscarPorId() {
        // Cenário
        Funcionario funcionarioMock = new Funcionario();
        funcionarioMock.setId(1L);
        funcionarioMock.setNome("João Silva");
        funcionarioMock.setSalario(BigDecimal.valueOf(5000));
        funcionarioMock.setDataEntrada(LocalDate.of(2023, 1, 10));

        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionarioMock));

        // Ação
        Funcionario result = funcionarioService.buscarPorId(1L);

        // Verificação
        assertNotNull(result);
        assertEquals("João Silva", result.getNome());
        assertEquals(BigDecimal.valueOf(5000), result.getSalario());
        verify(funcionarioRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve salvar um novo Funcionario com sucesso.")
    void testSalvarFuncionario() {
        // Cenário
        Funcionario novoFuncionario = new Funcionario();
        novoFuncionario.setNome("Maria Oliveira");
        novoFuncionario.setSalario(BigDecimal.valueOf(4000));
        novoFuncionario.setDataEntrada(LocalDate.of(2023, 2, 15));

        when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(novoFuncionario);

        // Ação
        funcionarioService.salvar(novoFuncionario);

        // Verificação
        verify(funcionarioRepository).save(novoFuncionario);
    }

    @Test
    @DisplayName("Deve editar um Funcionario existente com sucesso.")
    void testEditarFuncionario() {
        // Cenário
        Funcionario funcionarioExistente = new Funcionario();
        funcionarioExistente.setId(1L);
        funcionarioExistente.setNome("Carlos Souza");
        funcionarioExistente.setSalario(BigDecimal.valueOf(4500));

        when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(funcionarioExistente);

        // Ação
        funcionarioService.editar(funcionarioExistente);

        // Verificação
        verify(funcionarioRepository).save(funcionarioExistente);
    }

    @Test
    @DisplayName("Deve excluir um Funcionario por ID com sucesso.")
    void testExcluirFuncionario() {
        // Ação
        funcionarioService.excluir(1L);

        // Verificação
        verify(funcionarioRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Deve retornar lista de todos os Funcionarios.")
    void testBuscarTodos() {
        // Cenário
        Funcionario f1 = new Funcionario();
        f1.setId(1L);
        f1.setNome("José Santos");

        Funcionario f2 = new Funcionario();
        f2.setId(2L);
        f2.setNome("Ana Lima");

        List<Funcionario> funcionarios = Arrays.asList(f1, f2);

        when(funcionarioRepository.findAll()).thenReturn(funcionarios);

        // Ação
        List<Funcionario> result = funcionarioService.buscarTodos();

        // Verificação
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(funcionarioRepository).findAll();
    }

    @Test
    @DisplayName("Deve buscar Funcionarios por Nome contendo uma string.")
    void testBuscarPorNome() {
        // Cenário
        Funcionario funcionario = new Funcionario();
        funcionario.setId(1L);
        funcionario.setNome("Lucas");

        when(funcionarioRepository.findByNomeContaining("Lucas")).thenReturn(List.of(funcionario));

        // Ação
        List<Funcionario> result = funcionarioService.buscarPorNome("Lucas");

        // Verificação
        assertEquals(1, result.size());
        assertEquals("Lucas", result.get(0).getNome());
        verify(funcionarioRepository).findByNomeContaining("Lucas");
    }
}