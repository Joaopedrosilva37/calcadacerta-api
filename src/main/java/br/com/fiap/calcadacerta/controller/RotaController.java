package br.com.fiap.calcadacerta.controller;

import br.com.fiap.calcadacerta.bo.RotaBo;
import br.com.fiap.calcadacerta.model.RotaResposta;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rotas")
public class RotaController {

    private final RotaBo bo;

    public RotaController(RotaBo bo) {
        this.bo = bo;
    }

    @GetMapping
    public RotaResposta calcular(
            @RequestParam long origem,
            @RequestParam long destino,
            @RequestParam(defaultValue = "PADRAO") String perfil,
            @RequestParam(defaultValue = "false") boolean chuva) {
        return bo.calcular(origem, destino, perfil, chuva);
    }
}
