package com.construmoveis.backend.dto.cobranca;

import jakarta.validation.constraints.NotBlank;

public class CobrancaStatusRequestDTO {

    @NotBlank
    private String statusCobranca;

    public CobrancaStatusRequestDTO() {
    }

    public CobrancaStatusRequestDTO(String statusCobranca) {
        this.statusCobranca = statusCobranca;
    }

    public String getStatusCobranca() {
        return statusCobranca;
    }

    public void setStatusCobranca(String statusCobranca) {
        this.statusCobranca = statusCobranca;
    }
}