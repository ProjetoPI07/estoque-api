package com.construmoveis.backend.controller;

import com.construmoveis.backend.dto.cobranca.CobrancaRequestDTO;
import com.construmoveis.backend.dto.cobranca.CobrancaResponseDTO;
import com.construmoveis.backend.dto.cobranca.CobrancaStatusRequestDTO;
import com.construmoveis.backend.mapper.CobrancaMapper;
import com.construmoveis.backend.service.CobrancaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cobrancas")
public class CobrancaController {

    private final CobrancaService cobrancaService;

    public CobrancaController(CobrancaService cobrancaService) {
        this.cobrancaService = cobrancaService;
    }

    @PostMapping
    public ResponseEntity<CobrancaResponseDTO> cadastrar(@Valid @RequestBody CobrancaRequestDTO dto) {
        return ResponseEntity.status(200).body(CobrancaMapper.toResponse(cobrancaService.cadastrar(dto)));
    }

    @GetMapping
    public ResponseEntity<List<CobrancaResponseDTO>> listar() {
        return ResponseEntity.status(200).body(CobrancaMapper.toResponseList(cobrancaService.listar()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CobrancaResponseDTO> listarPorId(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(CobrancaMapper.toResponse(cobrancaService.listarPorId(id)));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<CobrancaResponseDTO>> listarPorClienteId(@PathVariable Integer clienteId) {
        return ResponseEntity.status(200).body(CobrancaMapper.toResponseList(cobrancaService.listarPorClienteId(clienteId)));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<CobrancaResponseDTO> atualizarStatus(
            @PathVariable Integer id,
            @Valid @RequestBody CobrancaStatusRequestDTO dto) {
        return ResponseEntity.status(200).body(CobrancaMapper.toResponse(
                cobrancaService.atualizarStatus(id, dto.getStatusCobranca())
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        cobrancaService.excluir(id);
        return ResponseEntity.status(200).build();
    }
}