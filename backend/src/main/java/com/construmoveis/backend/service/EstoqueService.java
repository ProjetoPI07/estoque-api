package com.construmoveis.backend.service;

import com.construmoveis.backend.entity.Estoque;
import com.construmoveis.backend.entity.Peca;
import com.construmoveis.backend.exception.PecaNaoExisteException;
import com.construmoveis.backend.repository.EstoqueRepository;
import com.construmoveis.backend.repository.PecaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EstoqueService {

    public final PecaRepository pecaRepository;

    public final EstoqueRepository estoqueRepository;

    public EstoqueService(PecaRepository pecaRepository, EstoqueRepository estoqueRepository) {
        this.pecaRepository = pecaRepository;
        this.estoqueRepository = estoqueRepository;
    }

    public Estoque cadastrarEstoque(Estoque estoqueCriado, Integer pecaId){
        Optional<Peca> pecaOptional = pecaRepository.findById(pecaId);

        if (pecaOptional.isEmpty()){
            throw new PecaNaoExisteException();
        }

        Peca peca = pecaOptional.get();
        estoqueCriado.setPeca(peca);
        return estoqueRepository.save(estoqueCriado);
    }
}
