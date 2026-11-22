package com.construmoveis.backend.mapper;

import com.construmoveis.backend.dto.cliente.ClienteRequestDTO;
import com.construmoveis.backend.dto.cliente.ClienteResponseDTO;
import com.construmoveis.backend.entity.Cliente;

public class ClienteMapper {

    public static Cliente toEntity(ClienteRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefone(dto.getTelefone());

        return cliente;
    }

    public static ClienteResponseDTO toResponseDto(Cliente entity) {
        if (entity == null) {
            return null;
        }

        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO();
        clienteResponseDTO.setId(entity.getId());
        clienteResponseDTO.setNome(entity.getNome());
        clienteResponseDTO.setEmail(entity.getEmail());
        clienteResponseDTO.setTelefone(entity.getTelefone());

        return clienteResponseDTO;
    }
}
