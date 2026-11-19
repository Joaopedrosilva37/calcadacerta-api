package br.com.fiap.calcadacerta.model;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LocalPublico(
        Long id,
        @NotBlank(message = "nome e obrigatorio")
        @Size(max = 100, message = "nome deve ter no maximo 100 caracteres")
        String nome,
        @NotBlank(message = "tipo e obrigatorio")
        @Pattern(regexp = "ESTACAO|EDUCACAO|PONTO_ONIBUS|PRACA|SAUDE|COMERCIO",
                message = "tipo deve ser ESTACAO, EDUCACAO, PONTO_ONIBUS, PRACA, SAUDE ou COMERCIO")
        String tipo,
        @NotBlank(message = "bairro e obrigatorio")
        @Size(max = 80, message = "bairro deve ter no maximo 80 caracteres")
        String bairro,
        @NotNull(message = "latitude e obrigatoria")
        @DecimalMin(value = "-90.0", message = "latitude minima e -90")
        @DecimalMax(value = "90.0", message = "latitude maxima e 90")
        Double latitude,
        @NotNull(message = "longitude e obrigatoria")
        @DecimalMin(value = "-180.0", message = "longitude minima e -180")
        @DecimalMax(value = "180.0", message = "longitude maxima e 180")
        Double longitude) {

    public LocalPublico comId(Long novoId) {
        return new LocalPublico(novoId, nome, tipo, bairro, latitude, longitude);
    }
}
