package com.layla.colaboradores.repository;

import com.layla.colaboradores.entity.Departamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class DepartamentoRepositoryTests {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @BeforeEach
    void setUp() {
        Departamento d1 = new Departamento();
        d1.setNome("Financeiro");
        departamentoRepository.save(d1);

        Departamento d2 = new Departamento();
        d2.setNome("Tecnologia da Informação");
        departamentoRepository.save(d2);
    }

    @Test
    void testFindByNome() {
        Departamento resultado = departamentoRepository.findByNome("Financeiro");
        assertNotNull(resultado);
        assertEquals("Financeiro", resultado.getNome());
    }

    @Test
    void testBuscarPorNomeSemelhante() {
        List<Departamento> resultados = departamentoRepository.buscarPorNomeSemelhante("tecno");
        assertEquals(1, resultados.size());
        assertTrue(resultados.get(0).getNome().toLowerCase().contains("tecno"));
    }
}
