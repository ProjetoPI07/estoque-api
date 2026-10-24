
package com.construmoveis.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public class PecaRequestDTO {

    @NotBlank
    @Size(min = 2, max = 100)
    @Schema(description = "Nome da peça", example = "Brocas de metal")
    private String nome;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    @Schema(description = "Valor da peça", example = "10.00")
    private Double valor;

    @NotBlank
    @Size(max = 500)
    @Schema(description = "Descrição da peça", example = "Brocas afiadas de metal")
    private String descricao;

    @NotBlank
    @Size(min = 2, max = 100)
    @Schema(description = "Categoria da peça", example = "Brocas")
    private String categoria;

    @NotNull
    @Min(0)
    @Schema(description = "Limite do estoque", example = "10")
    private Integer limiteEstoque;

    @NotNull
    @Schema(description = "Identificação se o item está ativo ou não", example = "True/False")
    private Boolean itemAtivo;

    public PecaRequestDTO() {
    }

    public PecaRequestDTO(
            String nome,
            Double valor,
            String descricao,
            String categoria,
            Integer limiteEstoque,
            Boolean itemAtivo
    ) {
        this.nome = nome;
        this.valor = valor;
        this.descricao = descricao;
        this.categoria = categoria;
        this.limiteEstoque = limiteEstoque;
        this.itemAtivo = itemAtivo;
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
