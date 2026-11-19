package br.com.fiap.calcadacerta.bo;

import br.com.fiap.calcadacerta.dao.UsuarioDao;
import br.com.fiap.calcadacerta.exception.RecursoNaoEncontradoException;
import br.com.fiap.calcadacerta.exception.RegraNegocioException;
import br.com.fiap.calcadacerta.model.Usuario;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioBo {

    private final UsuarioDao dao;

    public UsuarioBo(UsuarioDao dao) {
        this.dao = dao;
    }

    public List<Usuario> listar() {
        return dao.listar();
    }

    public Usuario buscar(long id) {
        return dao.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuario nao encontrado: " + id));
    }

    @Transactional
    public Usuario criar(Usuario u) {
        validarEmail(u.email(), null);
        Usuario novo = new Usuario(null, u.nome(), u.email(), u.perfil(), LocalDate.now());
        return dao.inserir(novo);
    }

    @Transactional
    public Usuario atualizar(long id, Usuario u) {
        Usuario atual = buscar(id);
        validarEmail(u.email(), id);
        Usuario novo = new Usuario(id, u.nome(), u.email(), u.perfil(), atual.dataCadastro());
        dao.atualizar(novo);
        return novo;
    }

    @Transactional
    public void excluir(long id) {
        buscar(id);
        dao.excluir(id);
    }

    private void validarEmail(String email, Long idAtual) {
        dao.buscarPorEmail(email)
                .filter(existente -> !existente.id().equals(idAtual))
                .ifPresent(existente -> {
                    throw new RegraNegocioException("Email ja cadastrado: " + email);
                });
    }
}
