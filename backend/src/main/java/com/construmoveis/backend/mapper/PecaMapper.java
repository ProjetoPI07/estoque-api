
package com.construmoveis.backend.mapper;

import com.construmoveis.backend.dto.PecaRequestDTO;
import com.construmoveis.backend.dto.PecaResponseDTO;
import com.construmoveis.backend.entity.Peca;

import java.util.ArrayList;
import java.util.List;

public class PecaMapper {

    public static List<PecaResponseDTO> toResponseDto(
            List<Peca> pecas
    ) {
        List<PecaResponseDTO> listar = new ArrayList<>();

        for (Peca peca : pecas) {
            PecaResponseDTO dto = new PecaResponseDTO();

            dto.setId(peca.getId());
            dto.setNome(peca.getNome());
            dto.setValor(peca.getValor());
            dto.setDescricao(peca.getDescricao());
            dto.setCategoria(peca.getCategoria());
            dto.setLimiteEstoque(peca.getLimiteEstoque());
            dto.setItemAtivo(peca.getItemAtivo());

            listar.add(dto);
        }

        return listar;
    }

    public static Peca toEntity(PecaRequestDTO dto) {
        Peca peca = new Peca();

        peca.setNome(dto.getNome());
        peca.setValor(dto.getValor());
        peca.setDescricao(dto.getDescricao());
        peca.setCategoria(dto.getCategoria());
        peca.setLimiteEstoque(dto.getLimiteEstoque());
        peca.setItemAtivo(dto.getItemAtivo());

        return peca;
    }

    public static PecaResponseDTO toResponse(Peca dto) {
        PecaResponseDTO response = new PecaResponseDTO();

        response.setId(dto.getId());
        response.setNome(dto.getNome());
        response.setValor(dto.getValor());
        response.setDescricao(dto.getDescricao());
        response.setCategoria(dto.getCategoria());
        response.setLimiteEstoque(dto.getLimiteEstoque());
        response.setItemAtivo(dto.getItemAtivo());

        return response;
    }
}
