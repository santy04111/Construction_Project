package com.barberia.barberia.repository;

import com.barberia.barberia.entities.Barbero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BarberoRepository extends JpaRepository<Barbero, Integer> {
    Optional<Barbero> findById(Integer id);
    boolean existsById(Integer id);
}