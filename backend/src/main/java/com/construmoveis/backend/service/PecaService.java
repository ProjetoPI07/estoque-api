package com.construmoveis.backend.service;

import com.construmoveis.backend.entity.Peca;
import com.construmoveis.backend.exception.PecaNaoExisteException;
import com.construmoveis.backend.repository.PecaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PecaService {

    private final PecaRepository pecaRepository;

    public PecaService(PecaRepository pecaRepository) {
        this.pecaRepository = pecaRepository;
    }

    public List<Peca> buscar(){
        return pecaRepository.findAll();
    }

    public Peca cadastrarPeca(Peca pecaCadastrada){
        return pecaRepository.save(pecaCadastrada);
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
