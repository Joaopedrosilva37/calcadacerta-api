package br.com.fiap.calcadacerta.bo;

import br.com.fiap.calcadacerta.dao.LocalPublicoDao;
import br.com.fiap.calcadacerta.exception.RecursoNaoEncontradoException;
import br.com.fiap.calcadacerta.model.LocalPublico;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocalPublicoBo {

    private final LocalPublicoDao dao;

    public LocalPublicoBo(LocalPublicoDao dao) {
        this.dao = dao;
    }

    public List<LocalPublico> listar() {
        return dao.listar();
    }

    public LocalPublico buscar(long id) {
        return dao.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Local nao encontrado: " + id));
    }

    @Transactional
    public LocalPublico criar(LocalPublico l) {
        return dao.inserir(l);
    }

    @Transactional
    public LocalPublico atualizar(long id, LocalPublico l) {
        buscar(id);
        LocalPublico novo = l.comId(id);
        dao.atualizar(novo);
        return novo;
    }

    @Transactional
    public void excluir(long id) {
        buscar(id);
        dao.excluir(id);
    }
}
