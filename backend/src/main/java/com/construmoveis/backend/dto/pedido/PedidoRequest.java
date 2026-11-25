package com.construmoveis.backend.dto.pedido;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class PedidoRequest {

    @NotNull
    @FutureOrPresent(message = "A data do pedido não pode ser anterior à data atual")
    private LocalDateTime dataPedido;

    @NotNull
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    private Double desconto;

    @NotNull
    @Positive
    private Double valorTotal;

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
