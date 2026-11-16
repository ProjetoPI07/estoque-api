package com.construmoveis.backend.dto.movhistorico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class MovimentacaoEstoqueRequestDTO {
    @NotNull
    private Integer pecaId;

    @NotBlank
    @Pattern(regexp = "(?i)ENTRADA|SAIDA")
    private String tipo;

    @NotNull
    @Positive
    private Integer quantidade;

    public MovimentacaoEstoqueRequestDTO() {
    }

    public MovimentacaoEstoqueRequestDTO(Integer pecaId, String tipo, Integer quantidade) {
        this.pecaId = pecaId;
        this.tipo = tipo;
        this.quantidade = quantidade;
    }

    public Integer getPecaId() {
        return pecaId;
    }

    public void setPecaId(Integer pecaId) {
        this.pecaId = pecaId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
