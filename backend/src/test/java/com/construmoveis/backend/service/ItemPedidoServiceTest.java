package com.construmoveis.backend.service;

import com.construmoveis.backend.dto.itempedido.ItemPedidoRequest;
import com.construmoveis.backend.dto.itempedido.ItemPedidoResponse;
import com.construmoveis.backend.entity.ItemPedido;
import com.construmoveis.backend.entity.Peca;
import com.construmoveis.backend.entity.Pedido;
import com.construmoveis.backend.exception.PecaNaoExisteException;
import com.construmoveis.backend.exception.PedidoNaoExisteException;
import com.construmoveis.backend.repository.ItemPedidoRepository;
import com.construmoveis.backend.repository.PecaRepository;
import com.construmoveis.backend.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ItemPedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private PecaRepository pecaRepository;

    @Mock
    private ItemPedidoRepository itemPedidoRepository;

    @InjectMocks
    private ItemPedidoService itemPedidoService;

    private static final Integer ID_PEDIDO = 1;
    private static final Integer ID_PECA = 10;
    private static final Integer ID_ITEM = 100;

    private ItemPedidoRequest request;
    private Pedido pedido;
    private Peca peca;

    @BeforeEach
    void setUp() {
        request = new ItemPedidoRequest();
        request.setQuantidade(2);

        pedido = new Pedido();
        pedido.setId(Long.valueOf(ID_PEDIDO));

        peca = new Peca();
        peca.setId(ID_PECA);
        peca.setValor(50.0);
    }

    @Nested
    @DisplayName("Cenários método cadastrar")
    class CadastrarTeste {

        @Test
        @DisplayName("Deve cadastrar item do pedido com sucesso")
        void deveCadastrarItemComSucesso() {
            ItemPedido itemSalvo = new ItemPedido();
            itemSalvo.setId(ID_ITEM);
            itemSalvo.setPedido(pedido);
            itemSalvo.setPeca(peca);
            itemSalvo.setQuantidade(2);
            itemSalvo.setValorUnitario(BigDecimal.valueOf(50.0));

            Mockito.when(pedidoRepository.findById(ID_PEDIDO)).thenReturn(Optional.of(pedido));
            Mockito.when(pecaRepository.findById(ID_PECA)).thenReturn(Optional.of(peca));
            Mockito.when(itemPedidoRepository.save(Mockito.any(ItemPedido.class))).thenReturn(itemSalvo);

            ItemPedidoResponse response = itemPedidoService.cadastrar(request, ID_PEDIDO, ID_PECA);

            assertNotNull(response);
            assertEquals(ID_ITEM, response.getId());
            assertEquals(2, response.getQuantidade());
        }

        @Test
        @DisplayName("Deve lançar exceção quando pedido não for encontrado")
        void deveLancarExceptionQuandoPedidoNaoExiste() {
            Mockito.when(pedidoRepository.findById(ID_PEDIDO)).thenReturn(Optional.empty());

            assertThrows(PedidoNaoExisteException.class,
                    () -> itemPedidoService.cadastrar(request, ID_PEDIDO, ID_PECA));
        }

        @Test
        @DisplayName("Deve lançar exceção quando peça não for encontrada")
        void deveLancarExceptionQuandoPecaNaoExiste() {
            Mockito.when(pedidoRepository.findById(ID_PEDIDO)).thenReturn(Optional.of(pedido));
            Mockito.when(pecaRepository.findById(ID_PECA)).thenReturn(Optional.empty());

            assertThrows(PecaNaoExisteException.class,
                    () -> itemPedidoService.cadastrar(request, ID_PEDIDO, ID_PECA));
        }

        @Test
        @DisplayName("Deve lançar exceção quando DTO for nulo")
        void deveLancarExceptionQuandoDtoNulo() {
            assertThrows(IllegalArgumentException.class,
                    () -> itemPedidoService.cadastrar(null, ID_PEDIDO, ID_PECA));
        }

        @Test
        @DisplayName("Deve lançar exceção para quantidade nula, zero ou negativa")
        void deveLancarExceptionParaQuantidadeInvalida() {
            request.setQuantidade(null);
            assertThrows(IllegalArgumentException.class, () -> itemPedidoService.cadastrar(request, ID_PEDIDO, ID_PECA));

            request.setQuantidade(0);
            assertThrows(IllegalArgumentException.class, () -> itemPedidoService.cadastrar(request, ID_PEDIDO, ID_PECA));

            request.setQuantidade(-1);
            assertThrows(IllegalArgumentException.class, () -> itemPedidoService.cadastrar(request, ID_PEDIDO, ID_PECA));
        }

        @Test
        @DisplayName("Deve lançar exceção quando valor da peça for nulo, zero ou negativo")
        void deveLancarExceptionParaValorPecaInvalido() {
            Mockito.when(pedidoRepository.findById(ID_PEDIDO)).thenReturn(Optional.of(pedido));

            peca.setValor(null);
            Mockito.when(pecaRepository.findById(ID_PECA)).thenReturn(Optional.of(peca));
            assertThrows(IllegalArgumentException.class, () -> itemPedidoService.cadastrar(request, ID_PEDIDO, ID_PECA));

            peca.setValor(0.0);
            assertThrows(IllegalArgumentException.class, () -> itemPedidoService.cadastrar(request, ID_PEDIDO, ID_PECA));

            peca.setValor(-10.0);
            assertThrows(IllegalArgumentException.class, () -> itemPedidoService.cadastrar(request, ID_PEDIDO, ID_PECA));
        }

        @Test
        @DisplayName("Deve verificar se o método save do repository foi chamado")
        void deveVerificarChamadaDeSave() {
            ItemPedido itemSalvo = new ItemPedido();
            itemSalvo.setId(ID_ITEM);

            Mockito.when(pedidoRepository.findById(ID_PEDIDO)).thenReturn(Optional.of(pedido));
            Mockito.when(pecaRepository.findById(ID_PECA)).thenReturn(Optional.of(peca));
            Mockito.when(itemPedidoRepository.save(Mockito.any(ItemPedido.class))).thenReturn(itemSalvo);

            itemPedidoService.cadastrar(request, ID_PEDIDO, ID_PECA);

            Mockito.verify(itemPedidoRepository, Mockito.times(1)).save(Mockito.any(ItemPedido.class));
        }
    }

    @Nested
    @DisplayName("Cenários método listar")
    class ListarTeste {

        @Test
        @DisplayName("Deve retornar lista com vários itens")
        void deveRetornarListaComVariosItens() {
            Mockito.when(itemPedidoRepository.findAll()).thenReturn(List.of(new ItemPedido(), new ItemPedido()));

            List<ItemPedidoResponse> resultado = itemPedidoService.listar();

            assertNotNull(resultado);
            assertEquals(2, resultado.size());
        }

        @Test
        @DisplayName("Deve retornar lista com um item")
        void deveRetornarListaComUmItem() {
            Mockito.when(itemPedidoRepository.findAll()).thenReturn(List.of(new ItemPedido()));

            List<ItemPedidoResponse> resultado = itemPedidoService.listar();

            assertNotNull(resultado);
            assertEquals(1, resultado.size());
        }

        @Test
        @DisplayName("Deve retornar lista vazia")
        void deveRetornarListaVazia() {
            Mockito.when(itemPedidoRepository.findAll()).thenReturn(List.of());

            List<ItemPedidoResponse> resultado = itemPedidoService.listar();

            assertNotNull(resultado);
            assertTrue(resultado.isEmpty());
        }
    }

    @Nested
    @DisplayName("Cenários método buscar por ID")
    class BuscarPorIdTeste {

        @Test
        @DisplayName("Deve retornar item quando ID existir")
        void deveRetornarItemQuandoIdExistir() {
            ItemPedido item = new ItemPedido();
            item.setId(ID_ITEM);

            Mockito.when(itemPedidoRepository.findById(ID_ITEM)).thenReturn(Optional.of(item));

            ItemPedidoResponse response = itemPedidoService.buscarPorId(ID_ITEM);

            assertNotNull(response);
            assertEquals(ID_ITEM, response.getId());
        }

        @Test
        @DisplayName("Deve lançar exceção quando ID não existir")
        void deveLancarExceptionQuandoIdNaoExistir() {
            Mockito.when(itemPedidoRepository.findById(ID_ITEM)).thenReturn(Optional.empty());

            assertThrows(RuntimeException.class, () -> itemPedidoService.buscarPorId(ID_ITEM));
        }

        @Test
        @DisplayName("Deve lançar exceção quando ID for nulo")
        void deveLancarExceptionQuandoIdNulo() {
            assertThrows(IllegalArgumentException.class, () -> itemPedidoService.buscarPorId(null));
        }
    }

    @Nested
    @DisplayName("Cenários método atualizar")
    class AtualizarTeste {

        @Test
        @DisplayName("Deve atualizar com sucesso preservando o ID original")
        void deveAtualizarComSucessoPreservandoId() {
            ItemPedido itemExistente = new ItemPedido();
            itemExistente.setId(ID_ITEM);

            Mockito.when(itemPedidoRepository.findById(ID_ITEM)).thenReturn(Optional.of(itemExistente));
            Mockito.when(pedidoRepository.findById(ID_PEDIDO)).thenReturn(Optional.of(pedido));
            Mockito.when(pecaRepository.findById(ID_PECA)).thenReturn(Optional.of(peca));
            Mockito.when(itemPedidoRepository.save(Mockito.any(ItemPedido.class))).thenAnswer(inv -> inv.getArgument(0));

            ItemPedidoResponse response = itemPedidoService.atualizar(ID_ITEM, request, ID_PEDIDO, ID_PECA);

            assertNotNull(response);
            assertEquals(ID_ITEM, response.getId());
        }

        @Test
        @DisplayName("Deve lançar exceção quando item a atualizar não existir")
        void deveLancarExceptionQuandoItemNaoExistir() {
            Mockito.when(itemPedidoRepository.findById(ID_ITEM)).thenReturn(Optional.empty());

            assertThrows(RuntimeException.class, () -> itemPedidoService.atualizar(ID_ITEM, request, ID_PEDIDO, ID_PECA));
        }

        @Test
        @DisplayName("Deve lançar exceção quando pedido da atualização não existir")
        void deveLancarExceptionQuandoPedidoAtualizacaoNaoExistir() {
            ItemPedido itemExistente = new ItemPedido();
            itemExistente.setId(ID_ITEM);

            Mockito.when(itemPedidoRepository.findById(ID_ITEM)).thenReturn(Optional.of(itemExistente));
            Mockito.when(pedidoRepository.findById(ID_PEDIDO)).thenReturn(Optional.empty());

            assertThrows(PedidoNaoExisteException.class, () -> itemPedidoService.atualizar(ID_ITEM, request, ID_PEDIDO, ID_PECA));
        }

        @Test
        @DisplayName("Deve lançar exceção quando peça da atualização não existir")
        void deveLancarExceptionQuandoPecaAtualizacaoNaoExistir() {
            ItemPedido itemExistente = new ItemPedido();
            itemExistente.setId(ID_ITEM);

            Mockito.when(itemPedidoRepository.findById(ID_ITEM)).thenReturn(Optional.of(itemExistente));
            Mockito.when(pedidoRepository.findById(ID_PEDIDO)).thenReturn(Optional.of(pedido));
            Mockito.when(pecaRepository.findById(ID_PECA)).thenReturn(Optional.empty());

            assertThrows(PecaNaoExisteException.class, () -> itemPedidoService.atualizar(ID_ITEM, request, ID_PEDIDO, ID_PECA));
        }

        @Test
        @DisplayName("Deve lançar exceção quando DTO de atualização for nulo")
        void deveLancarExceptionQuandoDtoAtualizacaoNulo() {
            assertThrows(IllegalArgumentException.class, () -> itemPedidoService.atualizar(ID_ITEM, null, ID_PEDIDO, ID_PECA));
        }

        @Test
        @DisplayName("Deve validar quantidade nula, zero ou negativa na atualização")
        void deveValidarQuantidadeAtualizacao() {
            ItemPedido itemExistente = new ItemPedido();
            itemExistente.setId(ID_ITEM);
            Mockito.when(itemPedidoRepository.findById(ID_ITEM)).thenReturn(Optional.of(itemExistente));
            Mockito.when(pedidoRepository.findById(ID_PEDIDO)).thenReturn(Optional.of(pedido));
            Mockito.when(pecaRepository.findById(ID_PECA)).thenReturn(Optional.of(peca));

            request.setQuantidade(null);
            assertThrows(IllegalArgumentException.class, () -> itemPedidoService.atualizar(ID_ITEM, request, ID_PEDIDO, ID_PECA));

            request.setQuantidade(0);
            assertThrows(IllegalArgumentException.class, () -> itemPedidoService.atualizar(ID_ITEM, request, ID_PEDIDO, ID_PECA));
        }

        @Test
        @DisplayName("Deve validar valor da peça nulo ou inválido na atualização")
        void deveValidarValorPecaAtualizacao() {
            ItemPedido itemExistente = new ItemPedido();
            itemExistente.setId(ID_ITEM);
            Mockito.when(itemPedidoRepository.findById(ID_ITEM)).thenReturn(Optional.of(itemExistente));
            Mockito.when(pedidoRepository.findById(ID_PEDIDO)).thenReturn(Optional.of(pedido));

            peca.setValor(null);
            Mockito.when(pecaRepository.findById(ID_PECA)).thenReturn(Optional.of(peca));

            assertThrows(IllegalArgumentException.class, () -> itemPedidoService.atualizar(ID_ITEM, request, ID_PEDIDO, ID_PECA));
        }
    }

    @Nested
    @DisplayName("Cenários método excluir")
    class ExcluirTeste {

        @Test
        @DisplayName("Deve excluir com sucesso quando ID existir")
        void deveExcluirComSucesso() {
            ItemPedido item = new ItemPedido();
            item.setId(ID_ITEM);

            Mockito.when(itemPedidoRepository.findById(ID_ITEM)).thenReturn(Optional.of(item));
            Mockito.doNothing().when(itemPedidoRepository).delete(item);

            assertDoesNotThrow(() -> itemPedidoService.excluir(ID_ITEM));
            Mockito.verify(itemPedidoRepository, Mockito.times(1)).delete(item);
        }

        @Test
        @DisplayName("Deve lançar exceção quando item a excluir não existir")
        void deveLancarExceptionQuandoItemExclusaoNaoExistir() {
            Mockito.when(itemPedidoRepository.findById(ID_ITEM)).thenReturn(Optional.empty());

            assertThrows(RuntimeException.class, () -> itemPedidoService.excluir(ID_ITEM));
        }

        @Test
        @DisplayName("Deve lançar exceção quando ID de exclusão for nulo")
        void deveLancarExceptionQuandoIdExclusaoNulo() {
            assertThrows(IllegalArgumentException.class, () -> itemPedidoService.excluir(null));
        }

        @Test
        @DisplayName("Deve verificar chamada de delete do repository")
        void deveVerificarChamadaDeDelete() {
            ItemPedido item = new ItemPedido();
            item.setId(ID_ITEM);

            Mockito.when(itemPedidoRepository.findById(ID_ITEM)).thenReturn(Optional.of(item));

            itemPedidoService.excluir(ID_ITEM);

            Mockito.verify(itemPedidoRepository, Mockito.times(1)).delete(item);
        }
    }
}