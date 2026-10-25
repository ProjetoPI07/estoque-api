package com.construmoveis.backend.service;
import com.construmoveis.backend.dto.movhistorico.MovimentacaoEstoqueRequestDTO;
import com.construmoveis.backend.dto.movhistorico.MovimentacaoEstoqueResponseDTO;
import com.construmoveis.backend.entity.Estoque;
import com.construmoveis.backend.entity.MovimentacaoEstoque;
import com.construmoveis.backend.entity.Peca;
import com.construmoveis.backend.exception.EstoqueInsuficienteException;
import com.construmoveis.backend.exception.EstoqueNaoEncontradoException;
import com.construmoveis.backend.exception.PecaNaoExisteException;
import com.construmoveis.backend.exception.TipoMovimentacaoInvalidoException;
import com.construmoveis.backend.mapper.MovimentacaoEstoqueMapper;
import com.construmoveis.backend.repository.EstoqueRepository;
import com.construmoveis.backend.repository.MovimentacaoEstoqueRepository;
import com.construmoveis.backend.repository.PecaRepository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@Service
public class MovimentacaoEstoqueService {

    private final PecaRepository pecaRepository;
    private final EstoqueRepository estoqueRepository;
    private final MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;

    public MovimentacaoEstoqueService(
            PecaRepository pecaRepository,
            EstoqueRepository estoqueRepository,
            MovimentacaoEstoqueRepository movimentacaoEstoqueRepository
    ) {
        this.pecaRepository = pecaRepository;
        this.estoqueRepository = estoqueRepository;
        this.movimentacaoEstoqueRepository = movimentacaoEstoqueRepository;
    }

    @Transactional
    public MovimentacaoEstoqueResponseDTO registrar(
            MovimentacaoEstoqueRequestDTO dto
    ) {

        Optional<Peca> pecaOptional =
                pecaRepository.findById(dto.getPecaId());

        if (pecaOptional.isEmpty()) {
            throw new PecaNaoExisteException();
        }

        Peca peca = pecaOptional.get();

        String tipo = dto.getTipo().toUpperCase(Locale.ROOT);

        if (!tipo.equals("ENTRADA") && !tipo.equals("SAIDA")) {
            throw new TipoMovimentacaoInvalidoException();
        }

        Optional<Estoque> estoqueOptional =
                estoqueRepository.findByPeca_Id(peca.getId());

        if (estoqueOptional.isEmpty()) {
            throw new EstoqueNaoEncontradoException();
        }

        Estoque estoque = estoqueOptional.get();

        if (tipo.equals("ENTRADA")) {
            estoque.setQuantidade(
                    estoque.getQuantidade() + dto.getQuantidade()
            );

        } else {
            if (estoque.getQuantidade() < dto.getQuantidade()) {
                throw new EstoqueInsuficienteException();
            }
            estoque.setQuantidade(
                    estoque.getQuantidade() - dto.getQuantidade()
            );
        }

        estoqueRepository.save(estoque);
        MovimentacaoEstoque movimentacao =
                MovimentacaoEstoqueMapper.toEntity(dto, peca);

        movimentacao.setTipo(tipo);
        movimentacao.setDataMovimentacao(LocalDateTime.now());

        MovimentacaoEstoque movimentacaoSalva =
                movimentacaoEstoqueRepository.save(movimentacao);

        return MovimentacaoEstoqueMapper.toResponse(movimentacaoSalva);
    }
}
