package com.construmoveis.backend.dto.movhistorico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public class MovimentacaoEstoqueResponseDTO {
    private Integer id;
    private Integer pecaId;
    private String nomePeca;
    private String tipo;
    private Integer quantidade;
    private LocalDateTime dataMovimentacao;

    public MovimentacaoEstoqueResponseDTO() {
    }

    public MovimentacaoEstoqueResponseDTO(Integer id, Integer pecaId, String nomePeca, String tipo, Integer quantidade, LocalDateTime dataMovimentacao) {
        this.id = id;
        this.pecaId = pecaId;
        this.nomePeca = nomePeca;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.dataMovimentacao = dataMovimentacao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPecaId() {
        return pecaId;
    }

    public void setPecaId(Integer pecaId) {
        this.pecaId = pecaId;
    }

    public String getNomePeca() {
        return nomePeca;
    }

    public void setNomePeca(String nomePeca) {
        this.nomePeca = nomePeca;
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

    public LocalDateTime getDataMovimentacao() {
        return dataMovimentacao;
    }

    public void setDataMovimentacao(LocalDateTime dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
    }
}
