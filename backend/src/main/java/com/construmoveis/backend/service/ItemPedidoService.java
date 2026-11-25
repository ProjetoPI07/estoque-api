package com.construmoveis.backend.service;

import com.construmoveis.backend.dto.itempedido.ItemPedidoRequest;
import com.construmoveis.backend.dto.itempedido.ItemPedidoResponse;

import com.construmoveis.backend.entity.ItemPedido;
import com.construmoveis.backend.entity.Pedido;
import com.construmoveis.backend.entity.Peca;

import com.construmoveis.backend.exception.ItemPedidoNaoExisteException;
import com.construmoveis.backend.exception.PedidoNaoExisteException;
import com.construmoveis.backend.exception.PecaNaoExisteException;

import com.construmoveis.backend.mapper.ItemPedidoMapper;

import com.construmoveis.backend.repository.ItemPedidoRepository;
import com.construmoveis.backend.repository.PedidoRepository;
import com.construmoveis.backend.repository.PecaRepository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final PecaRepository pecaRepository;

    public ItemPedidoService(
            ItemPedidoRepository itemPedidoRepository,
            PedidoRepository pedidoRepository,
            PecaRepository pecaRepository
        ) {
            this.itemPedidoRepository = itemPedidoRepository;
            this.pedidoRepository = pedidoRepository;
            this.pecaRepository = pecaRepository;
        }


        @Transactional
        public ItemPedidoResponse cadastrar(
                ItemPedidoRequest dto,
                Integer idPedido,
                Integer idPeca
        ) {

            Optional<Pedido> pedidoOptional =
                    pedidoRepository.findById(idPedido);

            if (pedidoOptional.isEmpty()) {
                throw new PedidoNaoExisteException();
            }
            Pedido pedido = pedidoOptional.get();

            Optional<Peca> pecaOptional =
                    pecaRepository.findById(idPeca);
            if (pecaOptional.isEmpty()) {
                throw new PecaNaoExisteException();
            }

            Peca peca = pecaOptional.get();
            ItemPedido itemPedido =
                    ItemPedidoMapper.toEntity(dto, pedido, peca);

            itemPedido.setValorUnitario(
                    BigDecimal.valueOf(peca.getValor())
            );

            ItemPedido itemSalvo =
                    itemPedidoRepository.save(itemPedido);

            return ItemPedidoMapper.toResponse(itemSalvo);
        }

        public List<ItemPedidoResponse> listar() {
            List<ItemPedido> itens =
                    itemPedidoRepository.findAll();

            List<ItemPedidoResponse> respostas =
                    new ArrayList<>();
            for (ItemPedido item : itens) {
                ItemPedidoResponse response =
                        ItemPedidoMapper.toResponse(item);
                respostas.add(response);
            }

            return respostas;
        }

        public ItemPedidoResponse buscarPorId(Integer id) {
            Optional<ItemPedido> itemOptional =
                    itemPedidoRepository.findById(id);
            if (itemOptional.isEmpty()) {
                throw new ItemPedidoNaoExisteException();
            }
            ItemPedido item = itemOptional.get();
            return ItemPedidoMapper.toResponse(item);
        }

        @Transactional
        public ItemPedidoResponse atualizar(
                Integer id,
                ItemPedidoRequest dto,
                Integer idPedido,
                Integer idPeca
        ) {

            Optional<ItemPedido> itemOptional =
                    itemPedidoRepository.findById(id);
            if (itemOptional.isEmpty()) {
                throw new ItemPedidoNaoExisteException();
            }
            ItemPedido itemExistente = itemOptional.get();
            Optional<Pedido> pedidoOptional =
                    pedidoRepository.findById(idPedido);

            if (pedidoOptional.isEmpty()) {
                throw new PedidoNaoExisteException();
            }

            Pedido pedido = pedidoOptional.get();
            Optional<Peca> pecaOptional =
                    pecaRepository.findById(idPeca);

            if (pecaOptional.isEmpty()) {
                throw new PecaNaoExisteException();
            }

            Peca peca = pecaOptional.get();
            ItemPedido itemAtualizado =
                    ItemPedidoMapper.toEntity(dto, pedido, peca);

            itemAtualizado.setId(itemExistente.getId());
            itemAtualizado.setValorUnitario(
                    BigDecimal.valueOf(peca.getValor())
            );

            ItemPedido itemSalvo =
                    itemPedidoRepository.save(itemAtualizado);
            return ItemPedidoMapper.toResponse(itemSalvo);
        }

        @Transactional
        public void excluir(Integer id) {
            Optional<ItemPedido> itemOptional =
                    itemPedidoRepository.findById(id);

            if (itemOptional.isEmpty()) {
                throw new ItemPedidoNaoExisteException();
            }
            ItemPedido item = itemOptional.get();
            itemPedidoRepository.delete(item);
        }
    }


