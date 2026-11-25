package com.construmoveis.backend.controller;

import com.construmoveis.backend.dto.parcela.ParcelaRequestDTO;
import com.construmoveis.backend.dto.parcela.ParcelaResponseDTO;
import com.construmoveis.backend.mapper.ParcelaMapper;
import com.construmoveis.backend.service.ParcelaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parcelas")
public class ParcelaController {

    private final ParcelaService parcelaService;

    public ParcelaController(ParcelaService parcelaService) {
        this.parcelaService = parcelaService;
    }

    @PostMapping
    public ResponseEntity<ParcelaResponseDTO> cadastrar(@Valid @RequestBody ParcelaRequestDTO dto) {
        return ResponseEntity.status(200).body(ParcelaMapper.toResponse(parcelaService.cadastrar(dto)));
    }

    @GetMapping
    public ResponseEntity<List<ParcelaResponseDTO>> listar() {
        return ResponseEntity.status(200).body(ParcelaMapper.toResponseList(parcelaService.listar()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParcelaResponseDTO> listarPorId(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(ParcelaMapper.toResponse(parcelaService.listarPorId(id)));
    }

    @GetMapping("/cobranca/{cobrancaId}")
    public ResponseEntity<List<ParcelaResponseDTO>> listarPorCobrancaId(@PathVariable Integer cobrancaId) {
        return ResponseEntity.status(200).body(ParcelaMapper.toResponseList(parcelaService.listarPorCobrancaId(cobrancaId)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParcelaResponseDTO> atualizar(@PathVariable Integer id, @Valid @RequestBody ParcelaRequestDTO dto) {
        return ResponseEntity.status(200).body(ParcelaMapper.toResponse(parcelaService.atualizar(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        parcelaService.excluir(id);
        return ResponseEntity.status(200).build();
    }
}