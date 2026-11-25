package com.construmoveis.backend.dto.cobranca;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CobrancaRequestDTO {

    @NotNull
    private Integer idPedido;

    @NotNull
    @Positive
    private Double valorTotal;

    @NotBlank
    private String statusCobranca;

    public CobrancaRequestDTO() {
    }

    public CobrancaRequestDTO(Integer idPedido, Double valorTotal, String statusCobranca) {
        this.idPedido = idPedido;
        this.valorTotal = valorTotal;
        this.statusCobranca = statusCobranca;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getStatusCobranca() {
        return statusCobranca;
    }

    public void setStatusCobranca(String statusCobranca) {
        this.statusCobranca = statusCobranca;
    }
}