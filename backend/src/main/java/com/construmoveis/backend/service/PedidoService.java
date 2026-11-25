
package com.construmoveis.backend.service;

import com.construmoveis.backend.dto.pedido.PedidoRequest;
import com.construmoveis.backend.dto.pedido.PedidoResponse;
import com.construmoveis.backend.entity.Pedido;
import com.construmoveis.backend.entity.Cliente;
import com.construmoveis.backend.entity.Funcionario;

import com.construmoveis.backend.exception.FuncionarioNaoExiste;
import com.construmoveis.backend.exception.PedidoNaoExisteException;
import com.construmoveis.backend.exception.ClienteNaoExisteException;
import com.construmoveis.backend.exception.FuncionarioNaoExisteException;

import com.construmoveis.backend.mapper.PedidoMapper;

import com.construmoveis.backend.repository.PedidoRepository;
import com.construmoveis.backend.repository.ClienteRepository;
import com.construmoveis.backend.repository.FuncionarioRepository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final FuncionarioRepository funcionarioRepository;

    public PedidoService(
            PedidoRepository pedidoRepository,
            ClienteRepository clienteRepository,
            FuncionarioRepository funcionarioRepository
    ) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    // Cadastrar pedido
    @Transactional
    public PedidoResponse cadastrar(
            PedidoRequest dto,
            Integer idCliente,
            Integer idFuncionario
    ) {

        Optional<Cliente> clienteOptional =
                clienteRepository.findById(idCliente);

        if (clienteOptional.isEmpty()) {
            throw new ClienteNaoExisteException();
        }

        Cliente cliente = clienteOptional.get();

        Optional<Funcionario> funcionarioOptional =
                funcionarioRepository.findById(idFuncionario);

        if (funcionarioOptional.isEmpty()) {
            throw new FuncionarioNaoExisteException();
        }

        Funcionario funcionario = funcionarioOptional.get();
        Pedido pedido = PedidoMapper.toEntity(dto, cliente);
        pedido.setFuncionario(funcionario);
        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        return PedidoMapper.toResponse(pedidoSalvo);
    }

    @Transactional
    public List<PedidoResponse> listar() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        List<PedidoResponse> respostas = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            PedidoResponse response =
                    PedidoMapper.toResponse(pedido);
            respostas.add(response);
        }
        return respostas;
    }

    @Transactional
    public PedidoResponse buscarPorId(Integer id) {
        Optional<Pedido> pedidoOptional =
                pedidoRepository.findById(id);
        if (pedidoOptional.isEmpty()) {
            throw new PedidoNaoExisteException();
        }
        Pedido pedido = pedidoOptional.get();
        return PedidoMapper.toResponse(pedido);
    }

    @Transactional
    public PedidoResponse atualizar(
            Integer id,
            PedidoRequest dto,
            Integer idCliente,
            Integer idFuncionario
    ) {

        Optional<Pedido> pedidoOptional =
                pedidoRepository.findById(id);
        if (pedidoOptional.isEmpty()) {
            throw new PedidoNaoExisteException();
        }

        Pedido pedidoExistente = pedidoOptional.get();
        Optional<Cliente> clienteOptional =
                clienteRepository.findById(idCliente);

        if (clienteOptional.isEmpty()) {
            throw new ClienteNaoExisteException();
        }

        Cliente cliente = clienteOptional.get();
        Optional<Funcionario> funcionarioOptional =
                funcionarioRepository.findById(idFuncionario);
        if (funcionarioOptional.isEmpty()) {
            throw new FuncionarioNaoExiste();
        }

        Funcionario funcionario = funcionarioOptional.get();
        Pedido pedidoAtualizado =
                PedidoMapper.toEntity(dto, cliente);
        pedidoAtualizado.setId(pedidoExistente.getId());
        pedidoAtualizado.setFuncionario(funcionario);

        Pedido pedidoSalvo =
                pedidoRepository.save(pedidoAtualizado);
        return PedidoMapper.toResponse(pedidoSalvo);
    }

    @Transactional
    public void excluir(Integer id) {
        Optional<Pedido> pedidoOptional =
                pedidoRepository.findById(id);
        if (pedidoOptional.isEmpty()) {
            throw new PedidoNaoExisteException();
        }

        Pedido pedido = pedidoOptional.get();
        pedidoRepository.delete(pedido);
    }
}
