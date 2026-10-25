package com.construmoveis.backend.repository;

import com.construmoveis.backend.entity.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<Estoque, Integer> {
    Optional<Estoque> findByPeca_Id(Integer pecaId);

}
