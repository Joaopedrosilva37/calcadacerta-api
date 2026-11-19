package br.com.fiap.calcadacerta.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record Trecho(
        Long id,
        @NotNull(message = "idLocalOrigem e obrigatorio")
        Long idLocalOrigem,
        @NotNull(message = "idLocalDestino e obrigatorio")
        Long idLocalDestino,
        @NotNull(message = "distanciaM e obrigatoria")
        @Positive(message = "distanciaM deve ser maior que zero")
        Integer distanciaM,
        @NotNull(message = "temEscada e obrigatorio")
        Boolean temEscada,
        @NotNull(message = "nivelCalcada e obrigatorio")
        @Min(value = 0, message = "nivelCalcada minimo e 0")
        @Max(value = 3, message = "nivelCalcada maximo e 3")
        Integer nivelCalcada,
        @NotNull(message = "riscoAlagamento e obrigatorio")
        @Min(value = 0, message = "riscoAlagamento minimo e 0")
        @Max(value = 3, message = "riscoAlagamento maximo e 3")
        Integer riscoAlagamento) {

    public Trecho comId(Long novoId) {
        return new Trecho(novoId, idLocalOrigem, idLocalDestino, distanciaM, temEscada, nivelCalcada, riscoAlagamento);
    }
}
