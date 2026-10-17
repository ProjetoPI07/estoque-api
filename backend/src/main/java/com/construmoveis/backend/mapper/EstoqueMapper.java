package com.construmoveis.backend.mapper;

import com.construmoveis.backend.dto.EstoqueResponseDTO;
import com.construmoveis.backend.entity.Estoque;

public class EstoqueMapper {
    public static EstoqueResponseDTO toResponse(Estoque estoque) {

        EstoqueResponseDTO response = new EstoqueResponseDTO();

        response.setId(estoque.getId());
        response.setIdPeca(estoque.getPeca().getId());
        response.setQuantidade(estoque.getQuantidade());

        return response;
    }
}
