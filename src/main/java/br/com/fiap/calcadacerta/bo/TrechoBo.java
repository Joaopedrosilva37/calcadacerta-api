package br.com.fiap.calcadacerta.bo;

import br.com.fiap.calcadacerta.dao.LocalPublicoDao;
import br.com.fiap.calcadacerta.dao.TrechoDao;
import br.com.fiap.calcadacerta.exception.RecursoNaoEncontradoException;
import br.com.fiap.calcadacerta.exception.RegraNegocioException;
import br.com.fiap.calcadacerta.model.Trecho;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TrechoBo {

    private final TrechoDao dao;
    private final LocalPublicoDao localDao;

    public TrechoBo(TrechoDao dao, LocalPublicoDao localDao) {
        this.dao = dao;
        this.localDao = localDao;
    }

    public List<Trecho> listar() {
        return dao.listar();
    }

    public Trecho buscar(long id) {
        return dao.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Trecho nao encontrado: " + id));
    }

    @Transactional
    public Trecho criar(Trecho t) {
        validar(t);
        return dao.inserir(t);
    }

    @Transactional
    public Trecho atualizar(long id, Trecho t) {
        buscar(id);
        validar(t);
        Trecho novo = t.comId(id);
        dao.atualizar(novo);
        return novo;
    }

    @Transactional
    public void excluir(long id) {
        buscar(id);
        dao.excluir(id);
    }

    private void validar(Trecho t) {
        if (t.idLocalOrigem().equals(t.idLocalDestino())) {
            throw new RegraNegocioException("Origem e destino do trecho devem ser diferentes");
        }
        if (localDao.buscarPorId(t.idLocalOrigem()).isEmpty()) {
            throw new RegraNegocioException("Local de origem inexistente: " + t.idLocalOrigem());
        }
        if (localDao.buscarPorId(t.idLocalDestino()).isEmpty()) {
            throw new RegraNegocioException("Local de destino inexistente: " + t.idLocalDestino());
        }
    }
}
