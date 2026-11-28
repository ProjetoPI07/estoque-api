package com.construmoveis.backend.mapper;

import com.construmoveis.backend.dto.itempedido.ItemPedidoRequest;
import com.construmoveis.backend.dto.itempedido.ItemPedidoResponse;
import com.construmoveis.backend.entity.ItemPedido;
import com.construmoveis.backend.entity.Pedido;
import com.construmoveis.backend.entity.Peca;

public class ItemPedidoMapper {

        public static ItemPedido toEntity(
                ItemPedidoRequest dto,
                Pedido pedido,
                Peca peca
        ) {

            if (dto == null) {
                return null;
            }

            ItemPedido itemPedido = new ItemPedido();

            itemPedido.setPedido(pedido);
            itemPedido.setPeca(peca);
            itemPedido.setQuantidade(dto.getQuantidade());

            return itemPedido;
        }

        public static ItemPedidoResponse toResponse(ItemPedido itemPedido) {

            if (itemPedido == null) {
                return null;
            }

            ItemPedidoResponse response = new ItemPedidoResponse();

            response.setId(itemPedido.getId());
            response.setQuantidade(itemPedido.getQuantidade());
            response.setValorUnitario(itemPedido.getValorUnitario());

            if (itemPedido.getPedido() != null) {
                response.setIdPedido(Math.toIntExact(itemPedido.getPedido().getId()));
            }

            if (itemPedido.getPeca() != null) {
                response.setIdPeca(itemPedido.getPeca().getId());
            }

            return response;
        }
    }


