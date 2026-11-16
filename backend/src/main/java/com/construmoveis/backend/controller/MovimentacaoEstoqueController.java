package com.construmoveis.backend.controller;

import com.construmoveis.backend.dto.movhistorico.MovimentacaoEstoqueRequestDTO;
import com.construmoveis.backend.dto.movhistorico.MovimentacaoEstoqueResponseDTO;
import com.construmoveis.backend.service.MovimentacaoEstoqueService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movimentacoes-estoque")
    public class MovimentacaoEstoqueController {

        private final MovimentacaoEstoqueService movimentacaoEstoqueService;
        public MovimentacaoEstoqueController(
                MovimentacaoEstoqueService movimentacaoEstoqueService
        ) {
            this.movimentacaoEstoqueService = movimentacaoEstoqueService;
        }

        @PostMapping
        public ResponseEntity<MovimentacaoEstoqueResponseDTO> registrar(
                @RequestBody @Valid MovimentacaoEstoqueRequestDTO dto
        ) {
            MovimentacaoEstoqueResponseDTO resposta =
                    movimentacaoEstoqueService.registrar(dto);

            return ResponseEntity.status(201).body(resposta);
        }
    }
