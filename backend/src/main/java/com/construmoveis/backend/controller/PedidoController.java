
package com.construmoveis.backend.controller;

import com.construmoveis.backend.dto.pedido.PedidoRequest;
import com.construmoveis.backend.dto.pedido.PedidoResponse;
import com.construmoveis.backend.service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Pedidos Controller",
        description = "Operações sobre os pedidos cadastrados no sistema"
)
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Operation(
            summary = "Lista todos os pedidos",
            description = "Lista todos os pedidos cadastrados no sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedidos encontrados")
    })
    @GetMapping
    public ResponseEntity<List<PedidoResponse>> listar() {

        List<PedidoResponse> pedidos = pedidoService.listar();

        return ResponseEntity.ok(pedidos);
    }

    @Operation(
            summary = "Cadastrar pedido",
            description = "Cadastra um pedido a partir dos dados enviados no corpo da requisição."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido cadastrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente ou funcionário não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<PedidoResponse> cadastrar(

            @Parameter(description = "Identificador do cliente", example = "1")
            @RequestParam Integer idCliente,

            @Parameter(description = "Identificador do funcionário", example = "2")
            @RequestParam Integer idFuncionario,

            @Parameter(description = "Dados do pedido")
            @Valid @RequestBody PedidoRequest dto
    ) {

        PedidoResponse pedidoSalvo =
                pedidoService.cadastrar(dto, idCliente, idFuncionario);

        return ResponseEntity.status(201).body(pedidoSalvo);
    }

    @Operation(
            summary = "Buscar pedido por ID",
            description = "Busca um pedido específico através do seu identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> buscarPorId(

            @Parameter(description = "Identificador do pedido", example = "1")
            @PathVariable Integer id
    ) {

        PedidoResponse pedido = pedidoService.buscarPorId(id);

        return ResponseEntity.ok(pedido);
    }

    @Operation(
            summary = "Atualizar pedido",
            description = "Atualiza os dados de um pedido através do seu identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido, cliente ou funcionário não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponse> atualizar(

            @Parameter(description = "Identificador do pedido", example = "1")
            @PathVariable Integer id,

            @Parameter(description = "Identificador do cliente", example = "1")
            @RequestParam Integer idCliente,

            @Parameter(description = "Identificador do funcionário", example = "2")
            @RequestParam Integer idFuncionario,

            @Parameter(description = "Dados atualizados do pedido")
            @Valid @RequestBody PedidoRequest dto
    ) {

        PedidoResponse pedidoAtualizado =
                pedidoService.atualizar(id, dto, idCliente, idFuncionario);

        return ResponseEntity.ok(pedidoAtualizado);
    }

    @Operation(
            summary = "Deletar pedido",
            description = "Deleta um pedido através do seu identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pedido deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(

            @Parameter(description = "Identificador do pedido", example = "1")
            @PathVariable Integer id
    ) {

        pedidoService.excluir(id);

        return ResponseEntity.status(204).build();
    }
}
