package com.layla.colaboradores.repository;

import com.layla.colaboradores.entity.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public  interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
}