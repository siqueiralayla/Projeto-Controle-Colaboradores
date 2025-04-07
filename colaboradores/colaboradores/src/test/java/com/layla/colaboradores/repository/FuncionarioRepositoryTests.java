package com.layla.colaboradores.repository;

import com.layla.colaboradores.entity.Cargo;
import com.layla.colaboradores.entity.Funcionario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class FuncionarioRepositoryTests {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private CargoRepository cargoRepository;

    private Cargo cargo;

    @BeforeEach
    void setUp() {
        cargo = new Cargo();
        cargo.setNome("Desenvolvedor");
        cargoRepository.save(cargo);

        Funcionario f1 = new Funcionario();
        f1.setNome("Ana");
        f1.setSalario(new BigDecimal("3000.00"));
        f1.setDataEntrada(LocalDate.of(2023, 1, 10));
        f1.setCargo(cargo);
        funcionarioRepository.save(f1);

        Funcionario f2 = new Funcionario();
        f2.setNome("Bruno");
        f2.setSalario(new BigDecimal("5000.00"));
        f2.setDataEntrada(LocalDate.of(2023, 1, 10));
        f2.setCargo(cargo);
        funcionarioRepository.save(f2);
    }

    @Test
    void testFindBySalarioGreaterThan() {
        List<Funcionario> ricos = funcionarioRepository.findBySalarioGreaterThan(new BigDecimal("4000"));
        assertEquals(1, ricos.size());
        assertEquals("Bruno", ricos.get(0).getNome());
    }

    @Test
    void testBuscarPorNomeEData() {
        List<Funcionario> resultado = funcionarioRepository.buscarPorNomeEData("ana", LocalDate.of(2023, 1, 10));
        assertEquals(1, resultado.size());
        assertEquals("Ana", resultado.get(0).getNome());
    }
}
