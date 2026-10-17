package com.construmoveis.backend.controller;

import com.construmoveis.backend.dto.EstoqueRequestDTO;
import com.construmoveis.backend.dto.EstoqueResponseDTO;
import com.construmoveis.backend.entity.Estoque;
import com.construmoveis.backend.mapper.EstoqueMapper;
import com.construmoveis.backend.service.EstoqueService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estoques")

public class EstoqueController {

    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @PostMapping
    public ResponseEntity<EstoqueResponseDTO> cadastrar(
            @Valid @RequestBody EstoqueRequestDTO dto
    ) {
        Estoque estoque = new Estoque();

        estoque.setQuantidade(dto.getQuantidade());

        Estoque estoqueSalvo =
                estoqueService.cadastrarEstoque(
                        estoque,
                        dto.getIdPeca()
                );

        return ResponseEntity.status(201).body(EstoqueMapper.toResponse(estoqueSalvo));
    }
}


