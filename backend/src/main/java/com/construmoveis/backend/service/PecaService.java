package com.construmoveis.backend.service;

import com.construmoveis.backend.entity.Estoque;
import com.construmoveis.backend.entity.Peca;
import com.construmoveis.backend.exception.PecaNaoExisteException;
import com.construmoveis.backend.repository.EstoqueRepository;
import com.construmoveis.backend.repository.PecaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PecaService {
    private final EstoqueRepository estoqueRepository;
    private final PecaRepository pecaRepository;

    public PecaService(EstoqueRepository estoqueRepository, PecaRepository pecaRepository) {
        this.estoqueRepository = estoqueRepository;
        this.pecaRepository = pecaRepository;
    }

    public List<Peca> buscar(){
        return pecaRepository.findAll();
    }

    @Transactional
    public Peca cadastrarPeca(Peca pecaCadastrada) {

        Peca pecaSalva = pecaRepository.save(pecaCadastrada);

        Estoque estoque = new Estoque();
        estoque.setPeca(pecaSalva);
        estoque.setQuantidade(0);
        estoqueRepository.save(estoque);
        return pecaSalva;
    }


    public Peca atualizarPeca(Integer id, Peca pecaAtualizada) {
        Optional<Peca> pecaOptional = pecaRepository.findById(id);

        if (pecaOptional.isEmpty()) {
            throw new PecaNaoExisteException();
        }

        Peca pecaExistente = pecaOptional.get();

        pecaExistente.setNome(pecaAtualizada.getNome());
        pecaExistente.setValor(pecaAtualizada.getValor());
        pecaExistente.setDescricao(pecaAtualizada.getDescricao());
        pecaExistente.setCategoria(pecaAtualizada.getCategoria());
        pecaExistente.setLimiteEstoque(pecaAtualizada.getLimiteEstoque());
        pecaExistente.setItemAtivo(pecaAtualizada.getItemAtivo());

        return pecaRepository.save(pecaExistente);
    }


    public void excluirPeca(Integer id) {
        if (!pecaRepository.existsById(id)) {
            throw new PecaNaoExisteException();
        }

        pecaRepository.deleteById(id);
    }


}
