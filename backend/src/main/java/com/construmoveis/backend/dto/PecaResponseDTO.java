package com.construmoveis.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class PecaResponseDTO {
    @Schema(description = "Identificador da peça", example = "1")
    private Integer id;
    @Schema(description = "Nome da peça", example = "Brocas de metal")
    private String nome;
    @Schema(description = "Valor da peça", example = "10.00")
    private Double valor;
    @Schema(description = "Descrição da peça", example = "Brocas afiadas de metal")
    private String descricao;
    @Schema(description = "Categoria da peça", example = "Brocas")
    private String categoria;
    @Schema(description = "Limite do estoque", example = "10")
    private Integer limiteEstoque;
    @Schema(description = "Identificação se o item está ativo ou não", example = "True/False")
    private Boolean itemAtivo;

    public PecaResponseDTO() {
    }

    public PecaResponseDTO(Integer id, String nome, Double valor, String descricao, String categoria, Integer limiteEstoque, Boolean itemAtivo) {
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
