package br.com.fiap.calcadacerta.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record Relato(
        Long id,
        @NotNull(message = "idUsuario e obrigatorio")
        Long idUsuario,
        @NotNull(message = "idTrecho e obrigatorio")
        Long idTrecho,
        @NotBlank(message = "tipo e obrigatorio")
        @Pattern(regexp = "BURACO|SEM_RAMPA|ALAGAMENTO|ILUMINACAO|OBSTRUCAO",
                message = "tipo deve ser BURACO, SEM_RAMPA, ALAGAMENTO, ILUMINACAO ou OBSTRUCAO")
        String tipo,
        @NotBlank(message = "descricao e obrigatoria")
        @Size(max = 200, message = "descricao deve ter no maximo 200 caracteres")
        String descricao,
        @Pattern(regexp = "ABERTO|EM_ANALISE|RESOLVIDO",
                message = "status deve ser ABERTO, EM_ANALISE ou RESOLVIDO")
        String status,
        LocalDate dataRelato) {

    public Relato comId(Long novoId) {
        return new Relato(novoId, idUsuario, idTrecho, tipo, descricao, status, dataRelato);
    }
}
