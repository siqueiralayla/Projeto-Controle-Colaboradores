package com.layla.colaboradores.repository;

import com.layla.colaboradores.entity.Cargo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {
    Page<Cargo> findAll(Pageable pageable);

    // Method Queries
    Optional<Cargo> findByNome(String nome);
    List<Cargo> findByNomeContainingIgnoreCase(String nome);
    List<Cargo> findAllByOrderByNomeAsc();
    List<Cargo> findByDepartamentoId(Long idDepartamento);

    // Custom JPQL query com @Query
    @Query("SELECT c FROM Cargo c WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Cargo> buscarPorNome(@Param("nome") String nome);

    @Query("SELECT c FROM Cargo c WHERE c.departamento.id = :id")
    List<Cargo> buscarPorDepartamento(@Param("id") Long idDepartamento);

    // Com paginação e filtro por nome
    Page<Cargo> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}