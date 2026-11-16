package com.construmoveis.backend.repository;

import com.construmoveis.backend.entity.Estoque;
import com.construmoveis.backend.entity.MovimentacaoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovimentacaoEstoqueRepository extends JpaRepository<MovimentacaoEstoque, Integer> {

    Optional<Estoque> findByPecaId(Integer pecaId);
}
