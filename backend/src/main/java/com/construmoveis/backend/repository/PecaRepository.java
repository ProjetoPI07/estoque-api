package com.construmoveis.backend.repository;

import com.construmoveis.backend.entity.Peca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PecaRepository extends JpaRepository<Peca, Integer> {

    boolean existsByNome(String nome);
}
