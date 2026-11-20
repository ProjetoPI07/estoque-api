package com.construmoveis.backend.controller;

import com.construmoveis.backend.dto.peca.PecaRequestDTO;
import com.construmoveis.backend.dto.peca.PecaResponseDTO;
import com.construmoveis.backend.entity.Peca;
import com.construmoveis.backend.exception.PecaNaoExisteException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.List;
import java.util.Set;

@SpringBootTest
class PecaControllerTest {
    @Autowired
    private PecaController pecaController;
    @Autowired
    private Validator validator;

    @Test
    @DisplayName("Deve receber uma lista com as peças do pedido")
    void deveRetornarUmaLista(){
        Assertions.assertInstanceOf(List.class, pecaController.listar().getBody());
    }

    @Test
    @DisplayName("Deve receber receber status 200 mesmo se a lista vier vazia")
    void deveRetornar200mesmoListaVazia(){
        ResponseEntity<?> resposta = pecaController.listar();

        Assertions.assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }

    @Test
    @DisplayName("Deve receber corretamente os dados necessários para cadastro")
    void deveReceberDadosCorretosParaCadastro(){
        PecaRequestDTO pecaRequestDTOTeste = new PecaRequestDTO("Nome", 20.0, "Descriçao", "Categoria", 20, true);

        Assertions.assertInstanceOf(String.class, pecaRequestDTOTeste.getNome());
        Assertions.assertInstanceOf(Double.class, pecaRequestDTOTeste.getValor());
        Assertions.assertInstanceOf(String.class, pecaRequestDTOTeste.getCategoria());
        Assertions.assertInstanceOf(String.class, pecaRequestDTOTeste.getDescricao());
        Assertions.assertInstanceOf(Integer.class, pecaRequestDTOTeste.getLimiteEstoque());
        Assertions.assertInstanceOf(Boolean.class, pecaRequestDTOTeste.getItemAtivo());
    }

    @Test
    @DisplayName("Deve retornar 201 caso a peça seja cadastrada com sucesso")
    void retornar200casoCadastreComSucesso(){
        PecaRequestDTO pecaRequestDTOTeste = new PecaRequestDTO("Nome", 20.0, "Descriçao", "Categoria", 20, true);

        ResponseEntity<?> resposta = pecaController.cadastrar(pecaRequestDTOTeste);

        Assertions.assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
    }

    @Test
    @DisplayName("Nao deve cadastrar caso receba algum dado incorreto para cadastro da peça")
    void naoDeveCadastrarDadosIncorretos(){
        PecaRequestDTO nomeIncorreto = new PecaRequestDTO(null, 20.0, "Descriçao", "Categoria", 20, true);
        PecaRequestDTO valorIncorreto = new PecaRequestDTO("Nome", null, "Descriçao", "Categoria", 20, true);
        PecaRequestDTO descriIncorreto = new PecaRequestDTO("Nome", 20.0, null, "Categoria", 20, true);
        PecaRequestDTO categoriaIncorreto = new PecaRequestDTO("Nome", 20.0, "Decrição",null , 20, true);
        PecaRequestDTO estoqueIncorreto = new PecaRequestDTO("Nome", 20.0, "Descriçao", "Categoria", null, true);
        PecaRequestDTO itemAtivoIncorreto = new PecaRequestDTO("Nome", 20.0, "Descriçao", "Categoria", 20, null);

        Set<ConstraintViolation<PecaRequestDTO>> nomeIncorretoTeste = validator.validate(nomeIncorreto);
        Set<ConstraintViolation<PecaRequestDTO>> valorIncorretoTeste = validator.validate(valorIncorreto);
        Set<ConstraintViolation<PecaRequestDTO>> descriIncorretoTeste = validator.validate(descriIncorreto);
        Set<ConstraintViolation<PecaRequestDTO>> categoriaIncorretoTeste = validator.validate(categoriaIncorreto);
        Set<ConstraintViolation<PecaRequestDTO>> estoqueIncorretoTeste = validator.validate(estoqueIncorreto);
        Set<ConstraintViolation<PecaRequestDTO>> itemIncorretoTeste = validator.validate(itemAtivoIncorreto);

        Assertions.assertFalse(nomeIncorretoTeste.isEmpty());
        Assertions.assertFalse(valorIncorretoTeste.isEmpty());
        Assertions.assertFalse(descriIncorretoTeste.isEmpty());
        Assertions.assertFalse(categoriaIncorretoTeste.isEmpty());
        Assertions.assertFalse(estoqueIncorretoTeste.isEmpty());
        Assertions.assertFalse(itemIncorretoTeste.isEmpty());
    }

    @Test
    @DisplayName("Deve receber corretamente os dados necessários para atualização da peça")
    void deveReceberDadosCorretosParaAtualizacao(){
        PecaRequestDTO pecaRequestDTOTeste = new PecaRequestDTO("Nome", 20.0, "Descriçao", "Categoria", 20, true);

        Assertions.assertInstanceOf(String.class, pecaRequestDTOTeste.getNome());
        Assertions.assertInstanceOf(Double.class, pecaRequestDTOTeste.getValor());
        Assertions.assertInstanceOf(String.class, pecaRequestDTOTeste.getCategoria());
        Assertions.assertInstanceOf(String.class, pecaRequestDTOTeste.getDescricao());
        Assertions.assertInstanceOf(Integer.class, pecaRequestDTOTeste.getLimiteEstoque());
        Assertions.assertInstanceOf(Boolean.class, pecaRequestDTOTeste.getItemAtivo());
    }

    @Test
    @DisplayName("Não deve atualizar caso receba algum dado incorreto para atualização da peça")
    void naoDeveAtualizarCasoRecebeAlgumDadoIncorreto(){
        PecaRequestDTO pecaRequestDTOTeste = new PecaRequestDTO("Nome", 20.0, "Descriçao", "Categoria", 20, true);
        ResponseEntity<PecaResponseDTO> pecaResponseDTO = pecaController.cadastrar(pecaRequestDTOTeste);

        PecaRequestDTO nomeIncorreto = new PecaRequestDTO(null, 20.0, "Descriçao", "Categoria", 20, true);
        PecaRequestDTO valorIncorreto = new PecaRequestDTO("Nome", null, "Descriçao", "Categoria", 20, true);
        PecaRequestDTO descriIncorreto = new PecaRequestDTO("Nome", 20.0, null, "Categoria", 20, true);
        PecaRequestDTO categoriaIncorreto = new PecaRequestDTO("Nome", 20.0, "Decrição",null , 20, true);
        PecaRequestDTO estoqueIncorreto = new PecaRequestDTO("Nome", 20.0, "Descriçao", "Categoria", null, true);
        PecaRequestDTO itemAtivoIncorreto = new PecaRequestDTO("Nome", 20.0, "Descriçao", "Categoria", 20, null);

        Set<ConstraintViolation<PecaRequestDTO>> nomeIncorretoTeste = validator.validate(nomeIncorreto);
        Set<ConstraintViolation<PecaRequestDTO>> valorIncorretoTeste = validator.validate(valorIncorreto);
        Set<ConstraintViolation<PecaRequestDTO>> descriIncorretoTeste = validator.validate(descriIncorreto);
        Set<ConstraintViolation<PecaRequestDTO>> categoriaIncorretoTeste = validator.validate(categoriaIncorreto);
        Set<ConstraintViolation<PecaRequestDTO>> estoqueIncorretoTeste = validator.validate(estoqueIncorreto);
        Set<ConstraintViolation<PecaRequestDTO>> itemIncorretoTeste = validator.validate(itemAtivoIncorreto);

        Assertions.assertFalse(nomeIncorretoTeste.isEmpty());
        Assertions.assertFalse(valorIncorretoTeste.isEmpty());
        Assertions.assertFalse(descriIncorretoTeste.isEmpty());
        Assertions.assertFalse(categoriaIncorretoTeste.isEmpty());
        Assertions.assertFalse(estoqueIncorretoTeste.isEmpty());
        Assertions.assertFalse(itemIncorretoTeste.isEmpty());
    }

    @Test
    @DisplayName("Deve retornar PecaNaoExisteException caso tente atualizar uma peça que não existe")
    void retornarPecaNaoExisteExceptionCasoAtualizePecaInexistente(){
        PecaRequestDTO pecaRequestDTO = new PecaRequestDTO("Nome", 20.0, "Descriçao", "Categoria", 20, true);

        Assertions.assertThrows(
                PecaNaoExisteException.class,
                () -> pecaController.atualizar(343434, pecaRequestDTO)
        );
    }

    @Test
    @DisplayName("Deve retornar status 204 quando a peça for excluída com sucesso")
    void deveRetornar204QuandoExcluidoComSucesse(){
        Peca peca = new Peca(1000000000,"Nome", 20.0, "Descriçao", "Categoria", 20, true);

        ResponseEntity<?> resposta = pecaController.excluir(1000000000);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, resposta.getStatusCode());
    }

    @Test
    @DisplayName("Deve retornar PecaNaoExisteException caso a peça que será excluída não for encontrada.")
    void deveRetornarPecaNaoExisteExceptionQuandoPecaExcluidaNaoEncontrado(){
        Assertions.assertThrows(
                PecaNaoExisteException.class,
                () -> pecaController.excluir(2433)
        );
    }
}