package com.construmoveis.backend.dto.cliente;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ClienteRequestDTO {

    @NotNull
    @NotBlank
    @Size(min = 2, max = 100)
    @Schema(description = "Nome do cliente", example = "Davi")
    private String nome;

    @NotNull
    @NotBlank(message = "Insira um email válido")
    @Email
    private String email;

    @NotNull
    @NotBlank(message = "O telefone é obrigatório")
    private String telefone;

    public ClienteRequestDTO(String nome, String email, String telefone) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
