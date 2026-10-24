package com.construmoveis.backend.controller;

import com.construmoveis.backend.dto.PecaRequestDTO;
import com.construmoveis.backend.dto.PecaResponseDTO;
import com.construmoveis.backend.entity.Peca;
import com.construmoveis.backend.mapper.PecaMapper;
import com.construmoveis.backend.repository.PecaRepository;
import com.construmoveis.backend.service.PecaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Peças Controller", description = "Operações sobre as peças cadastradas no estoque")
@RestController
@RequestMapping("/pecas")
public class PecaController {


    private final PecaService pecaService;

    private final PecaRepository pecaRepository;

    public PecaController(PecaService pecaService, PecaRepository pecaRepository) {
        this.pecaService = pecaService;

        this.pecaRepository = pecaRepository;
    }

    @Operation(
            summary = "Lista todas as peças",
            description = "Lista todas as peças cadastradas no estoque."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Peças encontradas"),
    })
    @GetMapping
    ResponseEntity<List<PecaResponseDTO>> listar(){
        List<Peca> pecas = pecaService.buscar();
        return ResponseEntity.ok(PecaMapper.toResponseDto(pecas));
    }

    @Operation(
            summary = "Cadastro de peças",
            description = "Cadastra peça no estoque a partir dos dados enviados no corpo da requisição."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Peça cadastrada com sucesso"),
    })
    @PostMapping
    public ResponseEntity<PecaResponseDTO> cadastrar(
            @Parameter(description = "Peça a ser cadastrada", example = "\"nome\": \"Peça X\",\n" +
                    "  \"valor\": 5.00,\n" +
                    "  \"descricao\": \"Peça de metal\",\n" +
                    "  \"categoria\": \"Brocas\",\n" +
                    "  \"limiteEstoque\": 10,\n" +
                    "  \"itemAtivo\": true")
            @Valid @RequestBody PecaRequestDTO dto
    ){
        Peca peca = PecaMapper.toEntity(dto);
        Peca pecaSalva = pecaService.cadastrarPeca(peca);
        return ResponseEntity.status(201).body(PecaMapper.toResponse(pecaSalva));
    }

    @Operation(
            summary = "Atualizar peça",
            description = "Atualiza a peça buscada através do ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Peça atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Peça não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PecaResponseDTO> atualizar(
            @Parameter(description = "Identificador da peça", example = "1")
            @PathVariable Integer id,
            @Parameter(description = "Atualização da peça", example = "\"nome\": \"Peça X\",\n" +
                    "  \"valor\": 5.00,\n" +
                    "  \"descricao\": \"Peça de metal\",\n" +
                    "  \"categoria\": \"Brocas\",\n" +
                    "  \"limiteEstoque\": 10,\n" +
                    "  \"itemAtivo\": true")
            @Valid @RequestBody PecaRequestDTO dto
    ) {
        Peca pecaAtualizada = PecaMapper.toEntity(dto);

        Peca pecaSalva = pecaService.atualizarPeca(id, pecaAtualizada);

        return ResponseEntity.ok(PecaMapper.toResponse(pecaSalva));
    }

    @Operation(
            summary = "Deletar peça",
            description = "Deleta a peça pesquisada através do ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Peça deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Peça não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @Parameter(description = "Identificador da peça", example = "1")
            @PathVariable Integer id) {
        pecaService.excluirPeca(id);
        return ResponseEntity.status(204).build();
    }

}
