package com.construmoveis.backend.dto.itempedido;
import java.math.BigDecimal;

public class ItemPedidoResponse {
    private Integer id;
    private Integer idPedido;
    private Integer idPeca;
    private Integer quantidade;
    private BigDecimal valorUnitario;

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

        public BigDecimal getValorUnitario() {
            return valorUnitario;
        }

        public void setValorUnitario(BigDecimal valorUnitario) {
            this.valorUnitario = valorUnitario;
        }
    }
