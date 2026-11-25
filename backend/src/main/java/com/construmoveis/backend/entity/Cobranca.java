package com.construmoveis.backend.entity;

import jakarta.persistence.*;

@Entity
public class Cobranca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cobranca")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_idPedido")
    private Pedido pedido;

    private Double valorTotal;
    private String statusCobranca;

    public Cobranca() {
    }

    public Cobranca(Integer id, Pedido pedido, Double valorTotal, String statusCobranca) {
        this.id = id;
        this.pedido = pedido;
        this.valorTotal = valorTotal;
        this.statusCobranca = statusCobranca;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
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