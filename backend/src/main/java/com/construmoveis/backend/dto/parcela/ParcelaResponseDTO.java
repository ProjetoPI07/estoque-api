package com.construmoveis.backend.dto.parcela;

import java.time.LocalDate;

public class ParcelaResponseDTO {

    private Integer id;
    private Integer idCobranca;
    private Integer numeroParcela;
    private Double valor;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
    private String formaPagamento;
    private String situacao;
    private String observacao;

    public ParcelaResponseDTO() {
    }

    public ParcelaResponseDTO(Integer id, Integer idCobranca, Integer numeroParcela, Double valor, LocalDate dataVencimento, LocalDate dataPagamento, String formaPagamento, String situacao, String observacao) {
        this.id = id;
        this.idCobranca = idCobranca;
        this.numeroParcela = numeroParcela;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
        this.formaPagamento = formaPagamento;
        this.situacao = situacao;
        this.observacao = observacao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCobranca() {
        return idCobranca;
    }

    public void setIdCobranca(Integer idCobranca) {
        this.idCobranca = idCobranca;
    }

    public Integer getNumeroParcela() {
        return numeroParcela;
    }

    public void setNumeroParcela(Integer numeroParcela) {
        this.numeroParcela = numeroParcela;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}