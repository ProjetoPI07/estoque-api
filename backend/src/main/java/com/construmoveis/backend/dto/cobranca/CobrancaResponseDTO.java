package com.construmoveis.backend.dto.cobranca;

public class CobrancaResponseDTO {

    private Integer id;
    private Integer idPedido;
    private Double valorTotal;
    private String statusCobranca;

    public CobrancaResponseDTO() {
    }

    public CobrancaResponseDTO(Integer id, Integer idPedido, Double valorTotal, String statusCobranca) {
        this.id = id;
        this.idPedido = idPedido;
        this.valorTotal = valorTotal;
        this.statusCobranca = statusCobranca;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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