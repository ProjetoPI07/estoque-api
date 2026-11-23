
package com.construmoveis.backend.mapper;

import com.construmoveis.backend.dto.pedido.PedidoRequest;
import com.construmoveis.backend.dto.pedido.PedidoResponse;
import com.construmoveis.backend.entity.Pedido;
import com.construmoveis.backend.entity.Cliente;

public class PedidoMapper {

    public static Pedido toEntity(PedidoRequest dto, Cliente cliente) {
        if (dto == null) {
            return null;
        }

        Pedido pedido = new Pedido();

        pedido.setDataPedido(dto.getDataPedido());
        pedido.setDesconto(dto.getDesconto());
        pedido.setValorTotal(dto.getValorTotal());
        pedido.setCliente(cliente);

        return pedido;
    }

    public static PedidoResponse toResponse(Pedido pedido) {
        if (pedido == null) {
            return null;
        }

        PedidoResponse response = new PedidoResponse();

        response.setId(pedido.getId());
        response.setDataPedido(pedido.getDataPedido());
        response.setDesconto(pedido.getDesconto());
        response.setValorTotal(pedido.getValorTotal());

        if (pedido.getCliente() != null) {
            response.setIdCliente(pedido.getCliente().getId());
        }

        if (pedido.getFuncionario() != null) {
            response.setIdFuncionario(pedido.getFuncionario().getId());
        }

        return response;
    }
}
