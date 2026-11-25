package com.construmoveis.backend.mapper;

import com.construmoveis.backend.dto.parcela.ParcelaRequestDTO;
import com.construmoveis.backend.dto.parcela.ParcelaResponseDTO;
import com.construmoveis.backend.entity.Cobranca;
import com.construmoveis.backend.entity.Parcela;

import java.util.ArrayList;
import java.util.List;

public class ParcelaMapper {

    public static ParcelaResponseDTO toResponse(Parcela entity) {
        if (entity == null) {
            return null;
        }

        return new ParcelaResponseDTO(
                entity.getId(),
                entity.getCobranca().getId(),
                entity.getNumeroParcela(),
                entity.getValor(),
                entity.getDataVencimento(),
                entity.getDataPagamento(),
                entity.getFormaPagamento(),
                entity.getSituacao(),
                entity.getObservacao()
        );
    }

    public static List<ParcelaResponseDTO> toResponseList(List<Parcela> parcelas) {
        List<ParcelaResponseDTO> response = new ArrayList<>();

        for (Parcela parcela : parcelas) {
            response.add(toResponse(parcela));
        }

        return response;
    }

    public static Parcela toEntity(ParcelaRequestDTO request, Cobranca cobranca) {
        if (request == null) {
            return null;
        }

        return new Parcela(
                null,
                cobranca,
                request.getNumeroParcela(),
                request.getValor(),
                request.getDataVencimento(),
                request.getDataPagamento(),
                request.getFormaPagamento(),
                request.getSituacao(),
                request.getObservacao()
        );
    }
}