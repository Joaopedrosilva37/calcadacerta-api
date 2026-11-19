package br.com.fiap.calcadacerta.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record Usuario(
        Long id,
        @NotBlank(message = "nome e obrigatorio")
        @Size(max = 100, message = "nome deve ter no maximo 100 caracteres")
        String nome,
        @NotBlank(message = "email e obrigatorio")
        @Email(message = "email invalido")
        @Size(max = 120, message = "email deve ter no maximo 120 caracteres")
        String email,
        @NotBlank(message = "perfil e obrigatorio")
        @Pattern(regexp = "PADRAO|IDOSO|CADEIRANTE|GESTANTE|CARRINHO_BEBE",
                message = "perfil deve ser PADRAO, IDOSO, CADEIRANTE, GESTANTE ou CARRINHO_BEBE")
        String perfil,
        LocalDate dataCadastro) {

    public Usuario comId(Long novoId) {
        return new Usuario(novoId, nome, email, perfil, dataCadastro);
    }
}
