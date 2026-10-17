package com.construmoveis.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Peca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private Double valor;
    private String descricao;
    private String categoria;
    private Integer limiteEstoque;
    private Boolean itemAtivo;

    public Peca() {
    }

    public Peca(Integer id, String nome, Double valor, String descricao, String categoria, Integer limiteEstoque, Boolean itemAtivo) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.descricao = descricao;
        this.categoria = categoria;
        this.limiteEstoque = limiteEstoque;
        this.itemAtivo = itemAtivo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getLimiteEstoque() {
        return limiteEstoque;
    }

    public void setLimiteEstoque(Integer limiteEstoque) {
        this.limiteEstoque = limiteEstoque;
    }

    public Boolean getItemAtivo() {
        return itemAtivo;
    }

    public void setItemAtivo(Boolean itemAtivo) {
        this.itemAtivo = itemAtivo;
    }
}
