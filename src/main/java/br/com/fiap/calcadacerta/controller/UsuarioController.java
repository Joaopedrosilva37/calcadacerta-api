package br.com.fiap.calcadacerta.controller;

import br.com.fiap.calcadacerta.bo.UsuarioBo;
import br.com.fiap.calcadacerta.model.Usuario;
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
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioBo bo;

    public UsuarioController(UsuarioBo bo) {
        this.bo = bo;
    }

    @GetMapping
    public List<Usuario> listar() {
        return bo.listar();
    }

    @GetMapping("/{id}")
    public Usuario buscar(@PathVariable long id) {
        return bo.buscar(id);
    }

    @PostMapping
    public ResponseEntity<Usuario> criar(@Valid @RequestBody Usuario corpo) {
        Usuario criado = bo.criar(corpo);
        return ResponseEntity.created(URI.create("/api/usuarios/" + criado.id())).body(criado);
    }

    @PutMapping("/{id}")
    public Usuario atualizar(@PathVariable long id, @Valid @RequestBody Usuario corpo) {
        return bo.atualizar(id, corpo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable long id) {
        bo.excluir(id);
    }
}
