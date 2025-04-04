package com.layla.colaboradores.repository;


import com.layla.colaboradores.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public  interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    List<Funcionario> findByNomeContaining(String nome);

    List<Funcionario> findByCargoId(Long id);

    List<Funcionario> findByDataEntradaBetween(LocalDate entrada, LocalDate saida);

    List<Funcionario> findByDataEntrada(LocalDate entrada);

    List<Funcionario> findByDataSaida(LocalDate saida);

    // Method Query: buscar funcionários com salário maior que
    List<Funcionario> findBySalarioGreaterThan(java.math.BigDecimal valor);

    // Query personalizada com JPQL: buscar por nome e data de entrada
    @Query("SELECT f FROM Funcionario f WHERE LOWER(f.nome) LIKE LOWER(CONCAT('%', :nome, '%')) AND f.dataEntrada = :data")
    List<Funcionario> buscarPorNomeEData(@org.springframework.data.repository.query.Param("nome") String nome,
                                         @org.springframework.data.repository.query.Param("data") LocalDate data);
}
