package com.construmoveis.backend.dto;

public class EstoqueResponseDTO {

    private Integer id;
    private Integer idPeca;
    private Integer quantidade;

    public EstoqueResponseDTO() {
    }

    public EstoqueResponseDTO(Integer id, Integer idPeca, Integer quantidade) {
        this.id = id;
        this.idPeca = idPeca;
        this.quantidade = quantidade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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