package com.construmoveis.backend.controller;

import com.construmoveis.backend.dto.cliente.ClienteRequestDTO;
import com.construmoveis.backend.dto.cliente.ClienteResponseDTO;
import com.construmoveis.backend.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listar() {
        return ResponseEntity.ok(clienteService.listar());
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> cadastrar(@RequestBody @Valid ClienteRequestDTO clienteReq) {
        ClienteResponseDTO clienteCriado = clienteService.cadastrar(clienteReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid ClienteRequestDTO clienteReq) {
        ClienteResponseDTO clienteAtualizado = clienteService.atualizar(id, clienteReq);
        return ResponseEntity.ok(clienteAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        clienteService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}