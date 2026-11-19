package br.com.fiap.calcadacerta.controller;

import br.com.fiap.calcadacerta.bo.RelatoBo;
import br.com.fiap.calcadacerta.model.Relato;
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
@RequestMapping("/api/relatos")
public class RelatoController {

    private final RelatoBo bo;

    public RelatoController(RelatoBo bo) {
        this.bo = bo;
    }

    @GetMapping
    public List<Relato> listar() {
        return bo.listar();
    }

    @GetMapping("/{id}")
    public Relato buscar(@PathVariable long id) {
        return bo.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Relato> criar(@Valid @RequestBody Relato corpo) {
        Relato criado = bo.criar(corpo);
        return ResponseEntity.created(URI.create("/api/relatos/" + criado.id())).body(criado);
    }

    @PutMapping("/{id}")
    public Relato atualizar(@PathVariable long id, @Valid @RequestBody Relato corpo) {
        return bo.atualizar(id, corpo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable long id) {
        bo.excluir(id);
    }
}
