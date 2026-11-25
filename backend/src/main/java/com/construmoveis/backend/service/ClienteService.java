package com.construmoveis.backend.service;

import com.construmoveis.backend.dto.cliente.ClienteRequestDTO;
import com.construmoveis.backend.dto.cliente.ClienteResponseDTO;
import com.construmoveis.backend.entity.Cliente;
import com.construmoveis.backend.exception.ClienteNaoEncontradoException;
import com.construmoveis.backend.exception.EmailJaCadastradoException;
import com.construmoveis.backend.mapper.ClienteMapper;
import com.construmoveis.backend.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<ClienteResponseDTO> listar() {
        List<Cliente> clientes = clienteRepository.findAll();
        return ClienteMapper.toResponseDtoList(clientes);
    }

    public ClienteResponseDTO cadastrar(ClienteRequestDTO dto) {
        if (clienteRepository.existsByEmail(dto.getEmail())) {
            throw new EmailJaCadastradoException();
        }

        Cliente cliente = ClienteMapper.toEntity(dto);
        Cliente clienteSalvo = clienteRepository.save(cliente);

        return ClienteMapper.toResponseDto(clienteSalvo);
    }

    public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO dto) {
        if (!clienteRepository.existsById(id)) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado");
        }

        if (clienteRepository.existsByEmailAndIdNot(dto.getEmail(), id)) {
            throw new EmailJaCadastradoException();
        }

        Cliente cliente = ClienteMapper.toEntity(dto);
        cliente.setId(id);

        Cliente clienteAtualizado = clienteRepository.save(cliente);
        return ClienteMapper.toResponseDto(clienteAtualizado);
    }

    public void excluir(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado para deletar");
        }

        clienteRepository.deleteById(id);
    }
}