package com.layla.colaboradores.repository;

import com.layla.colaboradores.entity.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public  interface DepartamentoRepository extends JpaRepository<Departamento, Long> {

    // Method Query: busca por nome exato
    Departamento findByNome(String nome);

    // @Query JPQL: busca departamentos que contém parte do nome (case-insensitive)
    @Query("SELECT d FROM Departamento d WHERE LOWER(d.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Departamento> buscarPorNomeSemelhante(String nome);
}