package com.construmoveis.backend.mapper;

import com.construmoveis.backend.dto.cobranca.CobrancaRequestDTO;
import com.construmoveis.backend.dto.cobranca.CobrancaResponseDTO;
import com.construmoveis.backend.entity.Cobranca;
import com.construmoveis.backend.entity.Pedido;

import java.util.ArrayList;
import java.util.List;

public class CobrancaMapper {

    public static CobrancaResponseDTO toResponse(Cobranca entity) {
        if (entity == null) {
            return null;
        }

        return new CobrancaResponseDTO(
                entity.getId(),
                Math.toIntExact(entity.getPedido().getId()),
                entity.getValorTotal(),
                entity.getStatusCobranca()
        );
    }

    public static List<CobrancaResponseDTO> toResponseList(List<Cobranca> cobrancas) {
        List<CobrancaResponseDTO> response = new ArrayList<>();

        for (Cobranca cobranca : cobrancas) {
            response.add(toResponse(cobranca));
        }

        return response;
    }

    public static Cobranca toEntity(CobrancaRequestDTO request, Pedido pedido) {
        if (request == null) {
            return null;
        }

        return new Cobranca(
                null,
                pedido,
                request.getValorTotal(),
                request.getStatusCobranca()
        );
    }
}