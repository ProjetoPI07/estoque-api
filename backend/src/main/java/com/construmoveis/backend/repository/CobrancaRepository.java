package com.construmoveis.backend.repository;

import com.construmoveis.backend.entity.Cobranca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CobrancaRepository extends JpaRepository<Cobranca, Integer> {
    List<Cobranca> findByPedido_Cliente_Id(Integer clienteId);
}