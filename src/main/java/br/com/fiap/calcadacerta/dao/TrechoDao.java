package br.com.fiap.calcadacerta.dao;

import br.com.fiap.calcadacerta.model.Trecho;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class TrechoDao {

    private static final String COLUNAS =
            "ID_TRECHO, ID_LOCAL_ORIGEM, ID_LOCAL_DESTINO, DISTANCIA_M, TEM_ESCADA, NIVEL_CALCADA, RISCO_ALAGAMENTO";

    private static final RowMapper<Trecho> MAPPER = (rs, n) -> new Trecho(
            rs.getLong("ID_TRECHO"),
            rs.getLong("ID_LOCAL_ORIGEM"),
            rs.getLong("ID_LOCAL_DESTINO"),
            rs.getInt("DISTANCIA_M"),
            "S".equals(rs.getString("TEM_ESCADA")),
            rs.getInt("NIVEL_CALCADA"),
            rs.getInt("RISCO_ALAGAMENTO"));

    private final JdbcTemplate jdbc;

    public TrechoDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Trecho> listar() {
        return jdbc.query("SELECT " + COLUNAS + " FROM T_CC_TRECHO ORDER BY ID_TRECHO", MAPPER);
    }

    public Optional<Trecho> buscarPorId(long id) {
        return jdbc.query("SELECT " + COLUNAS + " FROM T_CC_TRECHO WHERE ID_TRECHO = ?", MAPPER, id)
                .stream().findFirst();
    }

    public Trecho inserir(Trecho t) {
        Long id = jdbc.queryForObject("SELECT SEQ_CC_TRECHO.NEXTVAL FROM DUAL", Long.class);
        jdbc.update("INSERT INTO T_CC_TRECHO (" + COLUNAS + ") VALUES (?, ?, ?, ?, ?, ?, ?)",
                id, t.idLocalOrigem(), t.idLocalDestino(), t.distanciaM(),
                t.temEscada() ? "S" : "N", t.nivelCalcada(), t.riscoAlagamento());
        return t.comId(id);
    }

    public int atualizar(Trecho t) {
        return jdbc.update("UPDATE T_CC_TRECHO SET ID_LOCAL_ORIGEM = ?, ID_LOCAL_DESTINO = ?, DISTANCIA_M = ?, "
                        + "TEM_ESCADA = ?, NIVEL_CALCADA = ?, RISCO_ALAGAMENTO = ? WHERE ID_TRECHO = ?",
                t.idLocalOrigem(), t.idLocalDestino(), t.distanciaM(),
                t.temEscada() ? "S" : "N", t.nivelCalcada(), t.riscoAlagamento(), t.id());
    }

    public int excluir(long id) {
        return jdbc.update("DELETE FROM T_CC_TRECHO WHERE ID_TRECHO = ?", id);
    }
}
