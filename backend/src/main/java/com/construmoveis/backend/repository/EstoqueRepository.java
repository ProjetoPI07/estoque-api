package com.construmoveis.backend.repository;

import com.construmoveis.backend.entity.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstoqueRepository extends JpaRepository<Estoque, Integer> {

}
