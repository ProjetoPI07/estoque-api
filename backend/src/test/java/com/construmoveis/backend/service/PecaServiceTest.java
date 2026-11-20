package com.construmoveis.backend.service;

import com.construmoveis.backend.entity.Estoque;
import com.construmoveis.backend.entity.Peca;
import com.construmoveis.backend.exception.PecaNaoExisteException;
import com.construmoveis.backend.repository.EstoqueRepository;
import com.construmoveis.backend.repository.PecaRepository;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PecaServiceTest {
    @Mock
    private EstoqueRepository estoqueRepository;

    @Mock
    private PecaRepository pecaRepository;

    @InjectMocks
    private PecaService pecaService;

    @Nested
    @DisplayName("Método Buscar Peça")
    class Buscar {
        @Test
        @DisplayName("Deve retornar uma lista de objeto Peca")
        void deveRetornarUmaLista(){

            Peca peca1 = new Peca(1, "Broca", 10.0, "Brocas de metal", "Brocas", 10, true);
            Peca peca2 = new Peca(1, "Broca", 10.0, "Brocas de metal", "Brocas", 10, true);
            Peca peca3 = new Peca(1, "Broca", 10.0, "Brocas de metal", "Brocas", 10, true);

            List<Peca> pecas = new ArrayList<>();
            pecas.add(peca1);
            pecas.add(peca2);
            pecas.add(peca3);

            Mockito.when(pecaRepository.findAll()).thenReturn(pecas);

            List<Peca> listaResultado = pecaService.buscar();

            for (Peca peca : listaResultado) {
                Assertions.assertInstanceOf(Peca.class, peca);
            }
        }

        @Test
        @DisplayName("Deve retornar uma lista vazia quando não houver nenhum objeto cadastrado")
        void deveRetornarListaVazia(){
            List<Peca> pecas = new ArrayList<>();

            Mockito.when(pecaRepository.findAll()).thenReturn(pecas);

            List<Peca> resultado = pecaService.buscar();

            assertTrue(resultado.isEmpty());
        }

    }

    @Nested
    @DisplayName("Método Cadastrar Peça")
    class cadastrar{

        @Test
        @DisplayName("Deve cadastrar Peca com sucesso")
        void deveCadastrarPecaComSucesso(){
            Peca peca = new Peca();
            peca.setNome("Peca X");
            peca.setDescricao("Peca x teste");
            peca.setCategoria("Brocas");
            peca.setValor(10.0);
            peca.setLimiteEstoque(10);
            peca.setItemAtivo(true);

            Mockito.when(pecaRepository.save(peca)).thenReturn(peca);

            Peca resultado = pecaService.cadastrarPeca(peca);

            Estoque estoque = new Estoque();
            estoque.setPeca(peca);
            estoque.setQuantidade(10);

            Assertions.assertEquals("Peca X", resultado.getNome());
            Assertions.assertEquals("Peca x teste", resultado.getDescricao());
            Assertions.assertEquals("Brocas", resultado.getCategoria());
            Assertions.assertEquals(10.0, resultado.getValor());
            Assertions.assertEquals(10, resultado.getLimiteEstoque());
            Assertions.assertTrue(peca.getItemAtivo());

            Assertions.assertInstanceOf(Peca.class, estoque.getPeca());

        }

    }

    @Nested
    @DisplayName("Método Atualizar Peça")
    class atualizar{
        @Test
        @DisplayName("Deve atualizar Peca com sucesso")
        void deveAtualizarPecaComSucesso(){
            Integer id = 1;

            Peca pecaAtualizada = new Peca(id, "Peça y",10.0, "Peça atualizada", "Brocas", 10, true);

            Peca pecaExistente = new Peca(id, "Peça yyy",15.0, "Peça para atualizar", "Brocas", 10, true);

            Optional<Peca> pecaOptional = Optional.of(pecaExistente);

            Mockito.when(pecaRepository.findById(id)).thenReturn(pecaOptional);

            Mockito.when(pecaRepository.save(Mockito.any(Peca.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));

            Peca resultado = pecaService.atualizarPeca(id, pecaAtualizada);

            Assertions.assertEquals(pecaAtualizada.getNome(), resultado.getNome());
            Assertions.assertEquals(pecaAtualizada.getValor(), resultado.getValor());
            Assertions.assertEquals(pecaAtualizada.getDescricao(), resultado.getDescricao());
            Assertions.assertEquals(pecaAtualizada.getCategoria(), resultado.getCategoria());
            Assertions.assertEquals(pecaAtualizada.getLimiteEstoque(), resultado.getLimiteEstoque());
            Assertions.assertEquals(pecaAtualizada.getItemAtivo(), resultado.getItemAtivo());

        }

        @Test
        @DisplayName("Deve retornar PecaNaoExisteException quando peça para atualização não for encontrada")
        void deveRetornarPecaNaoExisteExceptionQuandoIdNaoEncontrado(){
            Integer id = 1;

            Optional<Peca> pecaOptional = Optional.empty();

            Mockito.when(pecaRepository.findById(id)).thenReturn(pecaOptional);

            Peca pecaAtualizada = new Peca(id, "Peça y",10.0, "Peça atualizada", "Brocas", 10, true);

            Assertions.assertThrows(
                    PecaNaoExisteException.class,
                    () -> pecaService.atualizarPeca(id, pecaAtualizada)
            );
        }
    }

    @Nested
    @DisplayName("Método Excluir Peça")
    class Excluir{
        @Test
        @DisplayName("Deve excluir peça com sucesso")
        void deveExcluirComSucesso(){
            Integer id = 1;

            Mockito.when(pecaRepository.existsById(id)).thenReturn(true);

            Assertions.assertDoesNotThrow(
                    () -> pecaService.excluirPeca(id)
            );

            Mockito.verify(pecaRepository, Mockito.times(1)).deleteById(id);
        }

        @Test
        @DisplayName("Deve retornar PecaNaoExisteException quando peça para atualização não for encontrada")
        void deveRetornarPecaNaoExisteExceptionQuandoIdNaoEncontrado(){
            Integer id = 1;

            Mockito.when(pecaRepository.existsById(id)).thenReturn(false);

            Assertions.assertThrows(
                    PecaNaoExisteException.class,
                    () -> pecaService.excluirPeca(id)
            );
        }
    }

}