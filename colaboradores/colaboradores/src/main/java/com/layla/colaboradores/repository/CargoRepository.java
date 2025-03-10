package com.layla.colaboradores.repository;

import com.layla.colaboradores.entity.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public  interface CargoRepository extends JpaRepository<Cargo, Long> {
}