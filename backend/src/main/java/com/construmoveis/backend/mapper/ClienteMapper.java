package com.construmoveis.backend.mapper;

import com.construmoveis.backend.dto.cliente.ClienteRequestDTO;
import com.construmoveis.backend.dto.cliente.ClienteResponseDTO;
import com.construmoveis.backend.entity.Cliente;

import java.util.ArrayList;
import java.util.List;

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

        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setEmail(entity.getEmail());
        dto.setTelefone(entity.getTelefone());

        return dto;
    }

    public static List<ClienteResponseDTO> toResponseDtoList(List<Cliente> entities) {
        if (entities == null) {
            return null;
        }

        List<ClienteResponseDTO> lista = new ArrayList<>();
        for (Cliente entity : entities) {
            lista.add(toResponseDto(entity));
        }

        return lista;
    }
}