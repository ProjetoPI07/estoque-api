package com.construmoveis.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class MovimentacaoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_id_peca")
    private Peca peca;

    private String tipo;
    private Integer quantidade;
    private LocalDateTime dataMovimentacao;

    public MovimentacaoEstoque() {
    }

    public MovimentacaoEstoque(Integer id, Peca peca, String tipo, Integer quantidade, LocalDateTime dataMovimentacao) {
        this.id = id;
        this.peca = peca;
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

    public Peca getPeca() {
        return peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
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
