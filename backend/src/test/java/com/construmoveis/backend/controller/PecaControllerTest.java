package com.construmoveis.backend.controller;

import com.construmoveis.backend.dto.peca.PecaRequestDTO;
import com.construmoveis.backend.dto.peca.PecaResponseDTO;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@SpringBootTest
@Transactional
class PecaControllerTest {

    @Autowired
    private PecaController pecaController;

    @Autowired
    private Validator validator;

    @Test
    @DisplayName("Deve retornar status 200 e uma lista ao listar peças")
    void deveListarPecasComStatus200() {
        ResponseEntity<List<PecaResponseDTO>> resposta = pecaController.listar();

        Assertions.assertEquals(HttpStatus.OK, resposta.getStatusCode());
        Assertions.assertInstanceOf(List.class, resposta.getBody());
    }

    @Test
    @DisplayName("Deve retornar 201 caso a peça seja cadastrada com sucesso")
    void deveRetornar201CasoCadastreComSucesso() {
        PecaRequestDTO dto = new PecaRequestDTO("Nome", 20.0, "Descrição", "Categoria", 20, true);

        ResponseEntity<PecaResponseDTO> resposta = pecaController.cadastrar(dto);

        Assertions.assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
        Assertions.assertNotNull(resposta.getBody());
        Assertions.assertEquals("Nome", resposta.getBody().getNome());
    }

    @Test
    @DisplayName("Não deve validar DTO caso receba algum campo nulo")
    void naoDeveValidarDadosIncorretos() {
        PecaRequestDTO nomeIncorreto = new PecaRequestDTO(null, 20.0, "Descrição", "Categoria", 20, true);
        PecaRequestDTO valorIncorreto = new PecaRequestDTO("Nome", null, "Descrição", "Categoria", 20, true);
        PecaRequestDTO descriIncorreto = new PecaRequestDTO("Nome", 20.0, null, "Categoria", 20, true);
        PecaRequestDTO categoriaIncorreto = new PecaRequestDTO("Nome", 20.0, "Descrição", null, 20, true);
        PecaRequestDTO estoqueIncorreto = new PecaRequestDTO("Nome", 20.0, "Descrição", "Categoria", null, true);
        PecaRequestDTO itemAtivoIncorreto = new PecaRequestDTO("Nome", 20.0, "Descrição", "Categoria", 20, null);

        Set<ConstraintViolation<PecaRequestDTO>> erroNome = validator.validate(nomeIncorreto);
        Set<ConstraintViolation<PecaRequestDTO>> erroValor = validator.validate(valorIncorreto);
        Set<ConstraintViolation<PecaRequestDTO>> erroDescricao = validator.validate(descriIncorreto);
        Set<ConstraintViolation<PecaRequestDTO>> erroCategoria = validator.validate(categoriaIncorreto);
        Set<ConstraintViolation<PecaRequestDTO>> erroEstoque = validator.validate(estoqueIncorreto);
        Set<ConstraintViolation<PecaRequestDTO>> erroItemAtivo = validator.validate(itemAtivoIncorreto);

        Assertions.assertFalse(erroNome.isEmpty());
        Assertions.assertFalse(erroValor.isEmpty());
        Assertions.assertFalse(erroDescricao.isEmpty());
        Assertions.assertFalse(erroCategoria.isEmpty());
        Assertions.assertFalse(erroEstoque.isEmpty());
        Assertions.assertFalse(erroItemAtivo.isEmpty());
    }

    @Test
    @DisplayName("Deve retornar status 200 ao atualizar uma peça existente")
    void deveAtualizarPecaComSucesso() {
        PecaRequestDTO dtoOriginal = new PecaRequestDTO("Nome", 20.0, "Descrição", "Categoria", 20, true);
        PecaResponseDTO criada = pecaController.cadastrar(dtoOriginal).getBody();
        Assertions.assertNotNull(criada);

        PecaRequestDTO dtoAtualizado = new PecaRequestDTO("Nome Novo", 35.0, "Nova Desc", "Categoria", 10, true);
        ResponseEntity<PecaResponseDTO> resposta = pecaController.atualizar(criada.getId(), dtoAtualizado);

        Assertions.assertEquals(HttpStatus.OK, resposta.getStatusCode());
        Assertions.assertNotNull(resposta.getBody());
        Assertions.assertEquals("Nome Novo", resposta.getBody().getNome());
        Assertions.assertEquals(35.0, resposta.getBody().getValor());
    }

    @Test
    @DisplayName("Deve lançar PecaNaoExisteException caso tente atualizar uma peça que não existe")
    void deveLancarExcecaoCasoAtualizePecaInexistente() {
        PecaRequestDTO dto = new PecaRequestDTO("Nome", 20.0, "Descrição", "Categoria", 20, true);

        Assertions.assertThrows(
                PecaNaoExisteException.class,
                () -> pecaController.atualizar(343434, dto)
        );
    }

    @Test
    @DisplayName("Deve retornar status 204 quando a peça for excluída com sucesso")
    void deveRetornar204QuandoExcluidaComSucesso() {
        PecaRequestDTO dto = new PecaRequestDTO("Nome", 20.0, "Descrição", "Categoria", 20, true);
        PecaResponseDTO criada = pecaController.cadastrar(dto).getBody();
        Assertions.assertNotNull(criada);

        ResponseEntity<Void> resposta = pecaController.excluir(criada.getId());

        Assertions.assertEquals(HttpStatus.NO_CONTENT, resposta.getStatusCode());
    }

    @Test
    @DisplayName("Deve lançar PecaNaoExisteException caso a peça excluída não seja encontrada")
    void deveLancarExcecaoQuandoPecaExcluidaNaoForEncontrada() {
        Assertions.assertThrows(
                PecaNaoExisteException.class,
                () -> pecaController.excluir(2433)
        );
    }
}