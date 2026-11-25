
package com.construmoveis.backend.controller;

import com.construmoveis.backend.dto.itempedido.ItemPedidoRequest;
import com.construmoveis.backend.dto.itempedido.ItemPedidoResponse;
import com.construmoveis.backend.service.ItemPedidoService;

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
        name = "Itens de Pedido Controller",
        description = "Operações sobre os itens associados aos pedidos"
)
@RestController
@RequestMapping("/itens-pedido")
public class ItemPedidoController {

    private final ItemPedidoService itemPedidoService;

    public ItemPedidoController(ItemPedidoService itemPedidoService) {
        this.itemPedidoService = itemPedidoService;
    }

    @Operation(
            summary = "Lista todos os itens dos pedidos",
            description = "Lista todos os itens cadastrados nos pedidos"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Itens encontrados")
    })
    @GetMapping
    public ResponseEntity<List<ItemPedidoResponse>> listar() {

        List<ItemPedidoResponse> itens =
                itemPedidoService.listar();

        return ResponseEntity.ok(itens);
    }

    @Operation(
            summary = "Cadastrar item no pedido",
            description = "Adiciona uma peça a um pedido existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Item cadastrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido ou peça não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<ItemPedidoResponse> cadastrar(

            @Parameter(description = "Identificador do pedido", example = "1")
            @RequestParam Integer idPedido,
            @Parameter(description = "Identificador da peça", example = "2")
            @RequestParam Integer idPeca,
            @Valid @RequestBody ItemPedidoRequest dto
    ) {

        ItemPedidoResponse itemSalvo =
                itemPedidoService.cadastrar(dto, idPedido, idPeca);
        return ResponseEntity.status(201).body(itemSalvo);
    }

    @Operation(
            summary = "Buscar item por ID",
            description = "Busca um item específico através do seu identificador."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item encontrado"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ItemPedidoResponse> buscarPorId(

            @Parameter(description = "Identificador do item", example = "1")
            @PathVariable Integer id
    ) {

        ItemPedidoResponse item =
                itemPedidoService.buscarPorId(id);
        return ResponseEntity.ok(item);
    }

    @Operation(
            summary = "Atualizar item do pedido",
            description = "Atualiza a quantidade, pedido ou peça associada ao item."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item, pedido ou peça não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ItemPedidoResponse> atualizar(

            @Parameter(description = "Identificador do item", example = "1")
            @PathVariable Integer id,

            @Parameter(description = "Identificador do pedido", example = "1")
            @RequestParam Integer idPedido,

            @Parameter(description = "Identificador da peça", example = "2")
            @RequestParam Integer idPeca,

            @Valid @RequestBody ItemPedidoRequest dto
    ) {

        ItemPedidoResponse itemAtualizado =
                itemPedidoService.atualizar(id, dto, idPedido, idPeca);
        return ResponseEntity.ok(itemAtualizado);
    }

    @Operation(
            summary = "Deletar item do pedido",
            description = "Remove um item através do seu identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Item removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(

            @Parameter(description = "Identificador do item", example = "1")
            @PathVariable Integer id
    ) {
        itemPedidoService.excluir(id);
        return ResponseEntity.status(204).build();
    }
}
