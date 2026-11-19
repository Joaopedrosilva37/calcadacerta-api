package br.com.fiap.calcadacerta.controller;

import br.com.fiap.calcadacerta.bo.TrechoBo;
import br.com.fiap.calcadacerta.model.Trecho;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trechos")
public class TrechoController {

    private final TrechoBo bo;

    public TrechoController(TrechoBo bo) {
        this.bo = bo;
    }

    @GetMapping
    public List<Trecho> listar() {
        return bo.listar();
    }

    @GetMapping("/{id}")
    public Trecho buscar(@PathVariable long id) {
        return bo.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Trecho> criar(@Valid @RequestBody Trecho corpo) {
        Trecho criado = bo.criar(corpo);
        return ResponseEntity.created(URI.create("/api/trechos/" + criado.id())).body(criado);
    }

    @PutMapping("/{id}")
    public Trecho atualizar(@PathVariable long id, @Valid @RequestBody Trecho corpo) {
        return bo.atualizar(id, corpo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable long id) {
        bo.excluir(id);
    }
}
