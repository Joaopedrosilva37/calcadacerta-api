package br.com.fiap.calcadacerta.dao;

import br.com.fiap.calcadacerta.model.LocalPublico;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class LocalPublicoDao {

    private static final String COLUNAS = "ID_LOCAL, NOME, TIPO, BAIRRO, LATITUDE, LONGITUDE";

    private static final RowMapper<LocalPublico> MAPPER = (rs, n) -> new LocalPublico(
            rs.getLong("ID_LOCAL"),
            rs.getString("NOME"),
            rs.getString("TIPO"),
            rs.getString("BAIRRO"),
            rs.getBigDecimal("LATITUDE").doubleValue(),
            rs.getBigDecimal("LONGITUDE").doubleValue());

    private final JdbcTemplate jdbc;

    public LocalPublicoDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<LocalPublico> listar() {
        return jdbc.query("SELECT " + COLUNAS + " FROM T_CC_LOCAL ORDER BY ID_LOCAL", MAPPER);
    }

    public Optional<LocalPublico> buscarPorId(long id) {
        return jdbc.query("SELECT " + COLUNAS + " FROM T_CC_LOCAL WHERE ID_LOCAL = ?", MAPPER, id)
                .stream().findFirst();
    }

    public LocalPublico inserir(LocalPublico l) {
        Long id = jdbc.queryForObject("SELECT SEQ_CC_LOCAL.NEXTVAL FROM DUAL", Long.class);
        jdbc.update("INSERT INTO T_CC_LOCAL (" + COLUNAS + ") VALUES (?, ?, ?, ?, ?, ?)",
                id, l.nome(), l.tipo(), l.bairro(), l.latitude(), l.longitude());
        return l.comId(id);
    }

    public int atualizar(LocalPublico l) {
        return jdbc.update(
                "UPDATE T_CC_LOCAL SET NOME = ?, TIPO = ?, BAIRRO = ?, LATITUDE = ?, LONGITUDE = ? WHERE ID_LOCAL = ?",
                l.nome(), l.tipo(), l.bairro(), l.latitude(), l.longitude(), l.id());
    }

    public int excluir(long id) {
        return jdbc.update("DELETE FROM T_CC_LOCAL WHERE ID_LOCAL = ?", id);
    }
}
