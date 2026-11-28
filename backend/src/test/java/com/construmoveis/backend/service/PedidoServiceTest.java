package com.construmoveis.backend.service;

import com.construmoveis.backend.dto.pedido.PedidoRequest;
import com.construmoveis.backend.dto.pedido.PedidoResponse;
import com.construmoveis.backend.entity.Cliente;
import com.construmoveis.backend.entity.Funcionario;
import com.construmoveis.backend.entity.Pedido;
import com.construmoveis.backend.exception.ClienteNaoExisteException;
import com.construmoveis.backend.exception.FuncionarioNaoExiste;
import com.construmoveis.backend.exception.PedidoNaoExisteException;
import com.construmoveis.backend.repository.ClienteRepository;
import com.construmoveis.backend.repository.FuncionarioRepository;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @InjectMocks
    private PedidoService pedidoService;

    private static final Integer ID_PEDIDO = 1;
    private static final Long ID_CLIENTE = 5L;
    private static final Integer ID_FUNCIONARIO = 10;

    private PedidoRequest request;
    private Cliente cliente;
    private Funcionario funcionario;
    private Pedido pedido;

    @BeforeEach
    void setUp() {
        request = new PedidoRequest();

        cliente = new Cliente();
        cliente.setId(ID_CLIENTE);

        funcionario = new Funcionario();
        funcionario.setId(ID_FUNCIONARIO);

        pedido = new Pedido();
        pedido.setId(Long.valueOf(ID_PEDIDO));
        pedido.setCliente(cliente);
        pedido.setFuncionario(funcionario);
    }

    @Nested
    @DisplayName("Cenários método cadastrar")
    class CadastrarTeste {

        @Test
        @DisplayName("1. Cadastro com cliente e funcionário existentes")
        void deveCadastrarComSucesso() {
            Mockito.when(clienteRepository.findById(ID_CLIENTE)).thenReturn(Optional.of(cliente));
            Mockito.when(funcionarioRepository.findById(ID_FUNCIONARIO)).thenReturn(Optional.of(funcionario));
            Mockito.when(pedidoRepository.save(Mockito.any(Pedido.class))).thenReturn(pedido);

            PedidoResponse response = pedidoService.cadastrar(request, ID_CLIENTE, ID_FUNCIONARIO);

            assertNotNull(response);
        }

        @Test
        @DisplayName("2. Cliente inexistente lança exceção")
        void deveLancarExcecaoQuandoClienteInexistente() {
            Mockito.when(clienteRepository.findById(ID_CLIENTE)).thenReturn(Optional.empty());

            assertThrows(ClienteNaoExisteException.class,
                    () -> pedidoService.cadastrar(request, ID_CLIENTE, ID_FUNCIONARIO));
        }

        @Test
        @DisplayName("3. Funcionário inexistente lança exceção")
        void deveLancarExcecaoQuandoFuncionarioInexistente() {
            Mockito.when(clienteRepository.findById(ID_CLIENTE)).thenReturn(Optional.of(cliente));
            Mockito.when(funcionarioRepository.findById(ID_FUNCIONARIO)).thenReturn(Optional.empty());

            assertThrows(FuncionarioNaoExiste.class,
                    () -> pedidoService.cadastrar(request, ID_CLIENTE, ID_FUNCIONARIO));
        }

        @Test
        @DisplayName("4. DTO nulo (comportamento gerenciado pelo mapper/serviço)")
        void deveLancarExcecaoQuandoDtoNulo() {
            Mockito.when(clienteRepository.findById(ID_CLIENTE)).thenReturn(Optional.of(cliente));
            Mockito.when(funcionarioRepository.findById(ID_FUNCIONARIO)).thenReturn(Optional.of(funcionario));
            // Dependendo do Mapper, se o DTO for nulo ele pode lançar NullPointerException
            assertThrows(Exception.class,
                    () -> pedidoService.cadastrar(null, ID_CLIENTE, ID_FUNCIONARIO));
        }

        @Test
        @DisplayName("5 e 6. ID do cliente ou funcionário nulos")
        void deveLancarExcecaoQuandoIdsNulos() {
            Mockito.when(clienteRepository.findById(null)).thenReturn(Optional.empty());

            assertThrows(ClienteNaoExisteException.class,
                    () -> pedidoService.cadastrar(request, null, ID_FUNCIONARIO));
        }

        @Test
        @DisplayName("11. Verificar chamada do save")
        void deveVerificarChamadaDeSave() {
            Mockito.when(clienteRepository.findById(ID_CLIENTE)).thenReturn(Optional.of(cliente));
            Mockito.when(funcionarioRepository.findById(ID_FUNCIONARIO)).thenReturn(Optional.of(funcionario));
            Mockito.when(pedidoRepository.save(Mockito.any(Pedido.class))).thenReturn(pedido);

            pedidoService.cadastrar(request, ID_CLIENTE, ID_FUNCIONARIO);

            Mockito.verify(pedidoRepository, Mockito.times(1)).save(Mockito.any(Pedido.class));
        }

        @Test
        @DisplayName("12. Verificar ausência do save quando entidades não existem")
        void naoDeveSalvarQuandoEntidadeNaoExiste() {
            Mockito.when(clienteRepository.findById(ID_CLIENTE)).thenReturn(Optional.empty());

            assertThrows(ClienteNaoExisteException.class,
                    () -> pedidoService.cadastrar(request, ID_CLIENTE, ID_FUNCIONARIO));

            Mockito.verify(pedidoRepository, Mockito.never()).save(Mockito.any(Pedido.class));
        }
    }

    @Nested
    @DisplayName("Cenários método listar")
    class ListarTeste {

        @Test
        @DisplayName("1 e 2. Existem pedidos (vários ou um)")
        void deveRetornarListaDePedidos() {
            Mockito.when(pedidoRepository.findAll()).thenReturn(List.of(pedido));

            List<PedidoResponse> resultado = pedidoService.listar();

            assertNotNull(resultado);
            assertEquals(1, resultado.size());
        }

        @Test
        @DisplayName("3. Nenhum pedido cadastrado retorna lista vazia")
        void deveRetornarListaVazia() {
            Mockito.when(pedidoRepository.findAll()).thenReturn(List.of());

            List<PedidoResponse> resultado = pedidoService.listar();

            assertNotNull(resultado);
            assertTrue(resultado.isEmpty());
        }
    }

    @Nested
    @DisplayName("Cenários método buscarPorId")
    class BuscarPorIdTeste {

        @Test
        @DisplayName("1. ID existente retorna PedidoResponse")
        void deveRetornarPedidoQuandoIdExistir() {
            Mockito.when(pedidoRepository.findById(ID_PEDIDO)).thenReturn(Optional.of(pedido));

            PedidoResponse response = pedidoService.buscarPorId(ID_PEDIDO);

            assertNotNull(response);
        }

        @Test
        @DisplayName("2. ID inexistente lança PedidoNaoExisteException")
        void deveLancarExceptionQuandoIdNaoExistir() {
            Mockito.when(pedidoRepository.findById(ID_PEDIDO)).thenReturn(Optional.empty());

            assertThrows(PedidoNaoExisteException.class,
                    () -> pedidoService.buscarPorId(ID_PEDIDO));
        }

        @Test
        @DisplayName("3. ID nulo lida com ausência ou comportamento do repository")
        void deveLancarExceptionQuandoIdNulo() {
            Mockito.when(pedidoRepository.findById(null)).thenReturn(Optional.empty());

            assertThrows(PedidoNaoExisteException.class,
                    () -> pedidoService.buscarPorId(null));
        }
    }

    @Nested
    @DisplayName("Cenários método atualizar")
    class AtualizarTeste {

        @Test
        @DisplayName("1. Atualização com dados válidos")
        void deveAtualizarComSucesso() {
            Mockito.when(pedidoRepository.findById(ID_PEDIDO)).thenReturn(Optional.of(pedido));
            Mockito.when(clienteRepository.findById(ID_CLIENTE)).thenReturn(Optional.of(cliente));
            Mockito.when(funcionarioRepository.findById(ID_FUNCIONARIO)).thenReturn(Optional.of(funcionario));
            Mockito.when(pedidoRepository.save(Mockito.any(Pedido.class))).thenReturn(pedido);

            PedidoResponse response = pedidoService.atualizar(ID_PEDIDO, request, ID_CLIENTE, ID_FUNCIONARIO);

            assertNotNull(response);
            Mockito.verify(pedidoRepository, Mockito.times(1)).save(Mockito.any(Pedido.class));
        }

        @Test
        @DisplayName("2. Pedido inexistente na atualização")
        void deveLancarExceptionQuandoPedidoNaoExiste() {
            Mockito.when(pedidoRepository.findById(ID_PEDIDO)).thenReturn(Optional.empty());

            assertThrows(PedidoNaoExisteException.class,
                    () -> pedidoService.atualizar(ID_PEDIDO, request, ID_CLIENTE, ID_FUNCIONARIO));
        }

        @Test
        @DisplayName("3. Cliente inexistente na atualização")
        void deveLancarExceptionQuandoClienteNaoExisteNaAtualizacao() {
            Mockito.when(pedidoRepository.findById(ID_PEDIDO)).thenReturn(Optional.of(pedido));
            Mockito.when(clienteRepository.findById(ID_CLIENTE)).thenReturn(Optional.empty());

            assertThrows(ClienteNaoExisteException.class,
                    () -> pedidoService.atualizar(ID_PEDIDO, request, ID_CLIENTE, ID_FUNCIONARIO));
        }

        @Test
        @DisplayName("4. Funcionário inexistente na atualização")
        void deveLancarExceptionQuandoFuncionarioNaoExisteNaAtualizacao() {
            Mockito.when(pedidoRepository.findById(ID_PEDIDO)).thenReturn(Optional.of(pedido));
            Mockito.when(clienteRepository.findById(ID_CLIENTE)).thenReturn(Optional.of(cliente));
            Mockito.when(funcionarioRepository.findById(ID_FUNCIONARIO)).thenReturn(Optional.empty());

            assertThrows(FuncionarioNaoExiste.class,
                    () -> pedidoService.atualizar(ID_PEDIDO, request, ID_CLIENTE, ID_FUNCIONARIO));
        }

        @Test
        @DisplayName("15. Verificar ausência do save na atualização com erro")
        void naoDeveSalvarQuandoAtualizacaoFalha() {
            Mockito.when(pedidoRepository.findById(ID_PEDIDO)).thenReturn(Optional.empty());

            assertThrows(PedidoNaoExisteException.class,
                    () -> pedidoService.atualizar(ID_PEDIDO, request, ID_CLIENTE, ID_FUNCIONARIO));

            Mockito.verify(pedidoRepository, Mockito.never()).save(Mockito.any(Pedido.class));
        }
    }

    @Nested
    @DisplayName("Cenários método excluir")
    class ExcluirTeste {

        @Test
        @DisplayName("1. Exclusão com sucesso")
        void deveExcluirComSucesso() {
            Mockito.when(pedidoRepository.findById(ID_PEDIDO)).thenReturn(Optional.of(pedido));
            Mockito.doNothing().when(pedidoRepository).delete(pedido);

            assertDoesNotThrow(() -> pedidoService.excluir(ID_PEDIDO));
            Mockito.verify(pedidoRepository, Mockito.times(1)).delete(pedido);
        }

        @Test
        @DisplayName("2. Item inexistente ao excluir")
        void deveLancarExceptionQuandoExcluirInexistente() {
            Mockito.when(pedidoRepository.findById(ID_PEDIDO)).thenReturn(Optional.empty());

            assertThrows(PedidoNaoExisteException.class,
                    () -> pedidoService.excluir(ID_PEDIDO));

            Mockito.verify(pedidoRepository, Mockito.never()).delete(Mockito.any(Pedido.class));
        }
    }
}