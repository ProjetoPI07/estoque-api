package com.construmoveis.backend.mapper;

import com.construmoveis.backend.dto.movhistorico.MovimentacaoEstoqueRequestDTO;
import com.construmoveis.backend.dto.movhistorico.MovimentacaoEstoqueResponseDTO;
import com.construmoveis.backend.entity.MovimentacaoEstoque;
import com.construmoveis.backend.entity.Peca;

public class MovimentacaoEstoqueMapper {

    public static MovimentacaoEstoque toEntity(
            MovimentacaoEstoqueRequestDTO dto,
            Peca peca
    ) {
        if (dto == null) {
            return null;
        }

        MovimentacaoEstoque movimentacao = new MovimentacaoEstoque();

        movimentacao.setPeca(peca);
        movimentacao.setTipo(dto.getTipo());
        movimentacao.setQuantidade(dto.getQuantidade());

        return movimentacao;
    }

    public static MovimentacaoEstoqueResponseDTO toResponse(
            MovimentacaoEstoque movimentacao
    ) {
        if (movimentacao == null) {
            return null;
        }

        MovimentacaoEstoqueResponseDTO response = new MovimentacaoEstoqueResponseDTO();

        response.setId(movimentacao.getId());
        response.setTipo(movimentacao.getTipo());
        response.setQuantidade(movimentacao.getQuantidade());
        response.setDataMovimentacao(movimentacao.getDataMovimentacao());

        if (movimentacao.getPeca() != null) {
            response.setPecaId(movimentacao.getPeca().getId());
            response.setNomePeca(movimentacao.getPeca().getNome());
        }

        return response;
    }
}
