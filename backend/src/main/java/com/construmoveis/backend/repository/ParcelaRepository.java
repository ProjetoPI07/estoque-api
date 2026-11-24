package com.construmoveis.backend.repository;

import com.construmoveis.backend.entity.Parcela;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParcelaRepository extends JpaRepository<Parcela, Integer> {
    List<Parcela> findByCobranca_Id(Integer cobrancaId);
    boolean existsByCobranca_Id(Integer cobrancaId);
}