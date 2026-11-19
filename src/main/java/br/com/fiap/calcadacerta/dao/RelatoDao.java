package br.com.fiap.calcadacerta.dao;

import br.com.fiap.calcadacerta.model.Relato;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class RelatoDao {

    private static final String COLUNAS =
            "ID_RELATO, ID_USUARIO, ID_TRECHO, TIPO, DESCRICAO, STATUS, DT_RELATO";

    private static final RowMapper<Relato> MAPPER = (rs, n) -> new Relato(
            rs.getLong("ID_RELATO"),
            rs.getLong("ID_USUARIO"),
            rs.getLong("ID_TRECHO"),
            rs.getString("TIPO"),
            rs.getString("DESCRICAO"),
            rs.getString("STATUS"),
            rs.getDate("DT_RELATO").toLocalDate());

    private final JdbcTemplate jdbc;

    public RelatoDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Relato> listar() {
        return jdbc.query("SELECT " + COLUNAS + " FROM T_CC_RELATO ORDER BY ID_RELATO", MAPPER);
    }

    public Optional<Relato> buscarPorId(long id) {
        return jdbc.query("SELECT " + COLUNAS + " FROM T_CC_RELATO WHERE ID_RELATO = ?", MAPPER, id)
                .stream().findFirst();
    }

    public Relato inserir(Relato r) {
        Long id = jdbc.queryForObject("SELECT SEQ_CC_RELATO.NEXTVAL FROM DUAL", Long.class);
        jdbc.update("INSERT INTO T_CC_RELATO (" + COLUNAS + ") VALUES (?, ?, ?, ?, ?, ?, ?)",
                id, r.idUsuario(), r.idTrecho(), r.tipo(), r.descricao(), r.status(),
                Date.valueOf(r.dataRelato()));
        return r.comId(id);
    }

    public int atualizar(Relato r) {
        return jdbc.update("UPDATE T_CC_RELATO SET ID_USUARIO = ?, ID_TRECHO = ?, TIPO = ?, DESCRICAO = ?, "
                        + "STATUS = ? WHERE ID_RELATO = ?",
                r.idUsuario(), r.idTrecho(), r.tipo(), r.descricao(), r.status(), r.id());
    }

    public int excluir(long id) {
        return jdbc.update("DELETE FROM T_CC_RELATO WHERE ID_RELATO = ?", id);
    }
}
