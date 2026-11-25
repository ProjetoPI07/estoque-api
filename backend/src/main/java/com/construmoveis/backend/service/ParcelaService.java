package com.construmoveis.backend.service;

import com.construmoveis.backend.dto.parcela.ParcelaRequestDTO;
import com.construmoveis.backend.entity.Cobranca;
import com.construmoveis.backend.entity.Parcela;
import com.construmoveis.backend.exception.CobrancaNaoExisteException;
import com.construmoveis.backend.exception.ParcelaNaoExisteException;
import com.construmoveis.backend.mapper.ParcelaMapper;
import com.construmoveis.backend.repository.CobrancaRepository;
import com.construmoveis.backend.repository.ParcelaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParcelaService {

    public final ParcelaRepository parcelaRepository;

    public final CobrancaRepository cobrancaRepository;

    public ParcelaService(ParcelaRepository parcelaRepository, CobrancaRepository cobrancaRepository) {
        this.parcelaRepository = parcelaRepository;
        this.cobrancaRepository = cobrancaRepository;
    }

    public Parcela cadastrar(ParcelaRequestDTO request) {
        Optional<Cobranca> cobrancaOptional = cobrancaRepository.findById(request.getIdCobranca());

        if (cobrancaOptional.isEmpty()) {
            throw new CobrancaNaoExisteException();
        }

        Cobranca cobranca = cobrancaOptional.get();

        Parcela parcela = ParcelaMapper.toEntity(request, cobranca);

        return parcelaRepository.save(parcela);
    }

    public Parcela listarPorId(Integer id) {
        Optional<Parcela> parcelaOptional = parcelaRepository.findById(id);

        if (parcelaOptional.isEmpty()) {
            throw new ParcelaNaoExisteException();
        }

        return parcelaOptional.get();
    }

    public List<Parcela> listar() {
        return parcelaRepository.findAll();
    }

    public List<Parcela> listarPorCobrancaId(Integer cobrancaId) {
        return parcelaRepository.findByCobranca_Id(cobrancaId);
    }

    public Parcela atualizar(Integer id, ParcelaRequestDTO request) {
        Optional<Parcela> parcelaOptional = parcelaRepository.findById(id);

        if (parcelaOptional.isEmpty()) {
            throw new ParcelaNaoExisteException();
        }

        Optional<Cobranca> cobrancaOptional = cobrancaRepository.findById(request.getIdCobranca());

        if (cobrancaOptional.isEmpty()) {
            throw new CobrancaNaoExisteException();
        }

        Parcela parcela = parcelaOptional.get();
        Cobranca cobranca = cobrancaOptional.get();

        parcela.setCobranca(cobranca);
        parcela.setNumeroParcela(request.getNumeroParcela());
        parcela.setValor(request.getValor());
        parcela.setDataVencimento(request.getDataVencimento());
        parcela.setDataPagamento(request.getDataPagamento());
        parcela.setFormaPagamento(request.getFormaPagamento());
        parcela.setSituacao(request.getSituacao());
        parcela.setObservacao(request.getObservacao());

        return parcelaRepository.save(parcela);
    }

    public void excluir(Integer id) {
        Optional<Parcela> parcelaOptional = parcelaRepository.findById(id);

        if (parcelaOptional.isEmpty()) {
            throw new ParcelaNaoExisteException();
        }

        parcelaRepository.deleteById(id);
    }
}