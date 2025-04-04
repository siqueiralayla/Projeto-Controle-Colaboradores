package com.layla.colaboradores.repository;

import com.layla.colaboradores.entity.Cargo;
import com.layla.colaboradores.entity.Departamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CargoRepositoryTests {

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    private Departamento depTI;

    @BeforeEach
    void setUp() {
        // Criar um departamento para relacionar aos cargos
        depTI = new Departamento();
        depTI.setNome("TI");
        departamentoRepository.save(depTI);

        // Cargos para teste
        Cargo c1 = new Cargo();
        c1.setNome("Desenvolvedor Java");
        c1.setDepartamento(depTI);
        cargoRepository.save(c1);

        Cargo c2 = new Cargo();
        c2.setNome("Analista de Sistemas");
        c2.setDepartamento(depTI);
        cargoRepository.save(c2);
    }

    @Test
    void testFindByNome() {
        Optional<Cargo> resultado = cargoRepository.findByNome("Desenvolvedor Java");
        assertTrue(resultado.isPresent());
        assertEquals("Desenvolvedor Java", resultado.get().getNome());
    }

    @Test
    void testFindByNomeContainingIgnoreCase() {
        List<Cargo> resultados = cargoRepository.findByNomeContainingIgnoreCase("analista");
        assertEquals(1, resultados.size());
    }

    @Test
    void testBuscarPorNomeJPQL() {
        List<Cargo> resultados = cargoRepository.buscarPorNome("java");
        assertEquals(1, resultados.size());
        assertEquals("Desenvolvedor Java", resultados.get(0).getNome());
    }

    @Test
    void testFindByDepartamentoId() {
        List<Cargo> cargos = cargoRepository.findByDepartamentoId(depTI.getId());
        assertEquals(2, cargos.size());
    }

    @Test
    void testBuscarPorDepartamentoJPQL() {
        List<Cargo> cargos = cargoRepository.buscarPorDepartamento(depTI.getId());
        assertFalse(cargos.isEmpty());
    }
}