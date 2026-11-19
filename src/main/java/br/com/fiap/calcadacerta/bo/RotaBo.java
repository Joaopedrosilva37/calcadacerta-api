package br.com.fiap.calcadacerta.bo;

import br.com.fiap.calcadacerta.dao.LocalPublicoDao;
import br.com.fiap.calcadacerta.dao.TrechoDao;
import br.com.fiap.calcadacerta.exception.RecursoNaoEncontradoException;
import br.com.fiap.calcadacerta.exception.RegraNegocioException;
import br.com.fiap.calcadacerta.model.LocalPublico;
import br.com.fiap.calcadacerta.model.RotaResposta;
import br.com.fiap.calcadacerta.model.Trecho;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class RotaBo {

    private static final Set<String> PERFIS = Set.of("PADRAO", "IDOSO", "CADEIRANTE", "GESTANTE", "CARRINHO_BEBE");
    private static final double PENALIDADE_ESCADA = 200.0;

    private final TrechoDao trechoDao;
    private final LocalPublicoDao localDao;

    public RotaBo(TrechoDao trechoDao, LocalPublicoDao localDao) {
        this.trechoDao = trechoDao;
        this.localDao = localDao;
    }

    public RotaResposta calcular(long origem, long destino, String perfilInformado, boolean chuva) {
        String perfil = perfilInformado.toUpperCase();
        if (!PERFIS.contains(perfil)) {
            throw new RegraNegocioException("Perfil invalido: " + perfilInformado);
        }
        if (origem == destino) {
            throw new RegraNegocioException("Origem e destino devem ser diferentes");
        }
        localDao.buscarPorId(origem)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Local de origem nao encontrado: " + origem));
        localDao.buscarPorId(destino)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Local de destino nao encontrado: " + destino));

        Map<Long, List<Aresta>> grafo = montarGrafo(perfil, chuva);

        Map<Long, Double> custos = new HashMap<>();
        Map<Long, Integer> metros = new HashMap<>();
        Map<Long, Long> anterior = new HashMap<>();
        PriorityQueue<No> fila = new PriorityQueue<>(Comparator.comparingDouble(No::custo));

        custos.put(origem, 0.0);
        metros.put(origem, 0);
        fila.add(new No(origem, 0.0));

        while (!fila.isEmpty()) {
            No atual = fila.poll();
            if (atual.custo() > custos.getOrDefault(atual.id(), Double.MAX_VALUE)) {
                continue;
            }
            if (atual.id() == destino) {
                break;
            }
            for (Aresta a : grafo.getOrDefault(atual.id(), List.of())) {
                double novoCusto = atual.custo() + a.custo();
                if (novoCusto < custos.getOrDefault(a.destino(), Double.MAX_VALUE)) {
                    custos.put(a.destino(), novoCusto);
                    metros.put(a.destino(), metros.get(atual.id()) + a.metros());
                    anterior.put(a.destino(), atual.id());
                    fila.add(new No(a.destino(), novoCusto));
                }
            }
        }

        if (!custos.containsKey(destino)) {
            throw new RecursoNaoEncontradoException("Nenhuma rota possivel para o perfil informado");
        }

        List<Long> ids = new ArrayList<>();
        Long no = destino;
        ids.add(no);
        while (no != origem) {
            no = anterior.get(no);
            ids.add(no);
        }
        Collections.reverse(ids);

        Map<Long, String> nomes = new HashMap<>();
        for (LocalPublico l : localDao.listar()) {
            nomes.put(l.id(), l.nome());
        }
        List<String> nomesRota = ids.stream().map(nomes::get).toList();
        double custoTotal = Math.round(custos.get(destino) * 100.0) / 100.0;

        return new RotaResposta(perfil, chuva, ids, nomesRota, metros.get(destino), custoTotal);
    }

    private Map<Long, List<Aresta>> montarGrafo(String perfil, boolean chuva) {
        Map<Long, List<Aresta>> grafo = new HashMap<>();
        for (Trecho t : trechoDao.listar()) {
            Double custo = custoTrecho(t, perfil, chuva);
            if (custo == null) {
                continue;
            }
            grafo.computeIfAbsent(t.idLocalOrigem(), k -> new ArrayList<>())
                    .add(new Aresta(t.idLocalDestino(), t.distanciaM(), custo));
            grafo.computeIfAbsent(t.idLocalDestino(), k -> new ArrayList<>())
                    .add(new Aresta(t.idLocalOrigem(), t.distanciaM(), custo));
        }
        return grafo;
    }

    private Double custoTrecho(Trecho t, String perfil, boolean chuva) {
        if (perfil.equals("PADRAO")) {
            return (double) t.distanciaM();
        }
        boolean semEscada = perfil.equals("CADEIRANTE") || perfil.equals("CARRINHO_BEBE");
        if (semEscada && t.temEscada()) {
            return null;
        }
        double fator = 1 + 0.3 * t.nivelCalcada();
        if (chuva) {
            fator += 0.5 * t.riscoAlagamento();
        }
        double custo = t.distanciaM() * fator;
        if (t.temEscada()) {
            custo += PENALIDADE_ESCADA;
        }
        return custo;
    }

    private record Aresta(long destino, int metros, double custo) {
    }

    private record No(long id, double custo) {
    }
}
