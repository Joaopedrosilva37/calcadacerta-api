package br.com.fiap.calcadacerta.model;

import java.util.List;

public record RotaResposta(
        String perfil,
        boolean chuva,
        List<Long> idsLocais,
        List<String> nomesLocais,
        int distanciaTotalM,
        double custoTotal) {
}
