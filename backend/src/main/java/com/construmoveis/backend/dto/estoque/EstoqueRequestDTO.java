package com.construmoveis.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class EstoqueRequestDTO {
    @NotNull
    private Integer idPeca;

    @NotNull
    @Min(0)
    private Integer quantidade;

    public EstoqueRequestDTO() {
    }

    public EstoqueRequestDTO(Integer idPeca, Integer quantidade) {
        this.idPeca = idPeca;
        this.quantidade = quantidade;
    }

    public Integer getIdPeca() {
        return idPeca;
    }

    public void setIdPeca(Integer idPeca) {
        this.idPeca = idPeca;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}

