package br.com.fiap.calcadacerta.bo;

import br.com.fiap.calcadacerta.dao.RelatoDao;
import br.com.fiap.calcadacerta.dao.TrechoDao;
import br.com.fiap.calcadacerta.dao.UsuarioDao;
import br.com.fiap.calcadacerta.exception.RecursoNaoEncontradoException;
import br.com.fiap.calcadacerta.exception.RegraNegocioException;
import br.com.fiap.calcadacerta.model.Relato;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RelatoBo {

    private static final String STATUS_INICIAL = "ABERTO";

    private final RelatoDao dao;
    private final UsuarioDao usuarioDao;
    private final TrechoDao trechoDao;

    public RelatoBo(RelatoDao dao, UsuarioDao usuarioDao, TrechoDao trechoDao) {
        this.dao = dao;
        this.usuarioDao = usuarioDao;
        this.trechoDao = trechoDao;
    }

    public List<Relato> listar() {
        return dao.listar();
    }

    public Relato buscar(long id) {
        return dao.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Relato nao encontrado: " + id));
    }

    @Transactional
    public Relato criar(Relato r) {
        validarReferencias(r);
        String status = r.status() == null ? STATUS_INICIAL : r.status();
        Relato novo = new Relato(null, r.idUsuario(), r.idTrecho(), r.tipo(), r.descricao(),
                status, LocalDate.now());
        return dao.inserir(novo);
    }

    @Transactional
    public Relato atualizar(long id, Relato r) {
        Relato atual = buscar(id);
        validarReferencias(r);
        String status = r.status() == null ? atual.status() : r.status();
        Relato novo = new Relato(id, r.idUsuario(), r.idTrecho(), r.tipo(), r.descricao(),
                status, atual.dataRelato());
        dao.atualizar(novo);
        return novo;
    }

    @Transactional
    public void excluir(long id) {
        buscar(id);
        dao.excluir(id);
    }

    private void validarReferencias(Relato r) {
        if (usuarioDao.buscarPorId(r.idUsuario()).isEmpty()) {
            throw new RegraNegocioException("Usuario inexistente: " + r.idUsuario());
        }
        if (trechoDao.buscarPorId(r.idTrecho()).isEmpty()) {
            throw new RegraNegocioException("Trecho inexistente: " + r.idTrecho());
        }
    }
}
