package com.layla.colaboradores.repository;


import com.layla.colaboradores.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
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
}