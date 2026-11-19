package br.com.fiap.calcadacerta.controller;

import br.com.fiap.calcadacerta.bo.LocalPublicoBo;
import br.com.fiap.calcadacerta.model.LocalPublico;
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
@RequestMapping("/api/locais")
public class LocalPublicoController {

    private final LocalPublicoBo bo;

    public LocalPublicoController(LocalPublicoBo bo) {
        this.bo = bo;
    }

    @GetMapping
    public List<LocalPublico> listar() {
        return bo.listar();
    }

    @GetMapping("/{id}")
    public LocalPublico buscar(@PathVariable long id) {
        return bo.buscar(id);
    }

    @PostMapping
    public ResponseEntity<LocalPublico> criar(@Valid @RequestBody LocalPublico corpo) {
        LocalPublico criado = bo.criar(corpo);
        return ResponseEntity.created(URI.create("/api/locais/" + criado.id())).body(criado);
    }

    @PutMapping("/{id}")
    public LocalPublico atualizar(@PathVariable long id, @Valid @RequestBody LocalPublico corpo) {
        return bo.atualizar(id, corpo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable long id) {
        bo.excluir(id);
    }
}
