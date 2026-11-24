package com.construmoveis.backend.service;

import com.construmoveis.backend.dto.cobranca.CobrancaRequestDTO;
import com.construmoveis.backend.entity.Cobranca;
import com.construmoveis.backend.entity.Pedido;
import com.construmoveis.backend.exception.CobrancaNaoExisteException;
import com.construmoveis.backend.exception.CobrancaPossuiParcelasException;
import com.construmoveis.backend.exception.PedidoNaoExisteException;
import com.construmoveis.backend.mapper.CobrancaMapper;
import com.construmoveis.backend.repository.CobrancaRepository;
import com.construmoveis.backend.repository.PedidoRepository;
import com.construmoveis.backend.repository.ParcelaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CobrancaService {

    public final CobrancaRepository cobrancaRepository;
    public final PedidoRepository pedidoRepository;
    public final ParcelaRepository parcelaRepository;

    public CobrancaService(CobrancaRepository cobrancaRepository,
                           PedidoRepository pedidoRepository,
                           ParcelaRepository parcelaRepository) {
        this.cobrancaRepository = cobrancaRepository;
        this.pedidoRepository = pedidoRepository;
        this.parcelaRepository = parcelaRepository;
    }

    public Cobranca cadastrar(CobrancaRequestDTO request) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(request.getIdPedido());

        if (pedidoOptional.isEmpty()) {
            throw new PedidoNaoExisteException();
        }

        Pedido pedido = pedidoOptional.get();

        Cobranca cobranca = CobrancaMapper.toEntity(request, pedido);

        return cobrancaRepository.save(cobranca);
    }

    public Cobranca listarPorId(Integer id) {
        Optional<Cobranca> cobrancaOptional = cobrancaRepository.findById(id);

        if (cobrancaOptional.isEmpty()) {
            throw new CobrancaNaoExisteException();
        }

        return cobrancaOptional.get();
    }

    public List<Cobranca> listar() {
        return cobrancaRepository.findAll();
    }

    public List<Cobranca> listarPorClienteId(Integer clienteId) {
        return cobrancaRepository.findByPedido_Cliente_Id(clienteId);
    }

    public Cobranca atualizarStatus(Integer id, String statusCobranca) {
        Optional<Cobranca> cobrancaOptional = cobrancaRepository.findById(id);

        if (cobrancaOptional.isEmpty()) {
            throw new CobrancaNaoExisteException();
        }

        Cobranca cobranca = cobrancaOptional.get();

        cobranca.setStatusCobranca(statusCobranca);

        return cobrancaRepository.save(cobranca);
    }

    public void excluir(Integer id) {
        Optional<Cobranca> cobrancaOptional = cobrancaRepository.findById(id);

        if (cobrancaOptional.isEmpty()) {
            throw new CobrancaNaoExisteException();
        }

        if (parcelaRepository.existsByCobranca_Id(id)) {
            throw new CobrancaPossuiParcelasException();
        }

        cobrancaRepository.deleteById(id);
    }
}