package br.com.fiap.calcadacerta.dao;

import br.com.fiap.calcadacerta.model.Usuario;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioDao {

    private static final String COLUNAS = "ID_USUARIO, NOME, EMAIL, PERFIL, DT_CADASTRO";

    private static final RowMapper<Usuario> MAPPER = (rs, n) -> new Usuario(
            rs.getLong("ID_USUARIO"),
            rs.getString("NOME"),
            rs.getString("EMAIL"),
            rs.getString("PERFIL"),
            rs.getDate("DT_CADASTRO").toLocalDate());

    private final JdbcTemplate jdbc;

    public UsuarioDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Usuario> listar() {
        return jdbc.query("SELECT " + COLUNAS + " FROM T_CC_USUARIO ORDER BY ID_USUARIO", MAPPER);
    }

    public Optional<Usuario> buscarPorId(long id) {
        return jdbc.query("SELECT " + COLUNAS + " FROM T_CC_USUARIO WHERE ID_USUARIO = ?", MAPPER, id)
                .stream().findFirst();
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return jdbc.query("SELECT " + COLUNAS + " FROM T_CC_USUARIO WHERE LOWER(EMAIL) = LOWER(?)", MAPPER, email)
                .stream().findFirst();
    }

    public Usuario inserir(Usuario u) {
        Long id = jdbc.queryForObject("SELECT SEQ_CC_USUARIO.NEXTVAL FROM DUAL", Long.class);
        jdbc.update("INSERT INTO T_CC_USUARIO (" + COLUNAS + ") VALUES (?, ?, ?, ?, ?)",
                id, u.nome(), u.email(), u.perfil(), Date.valueOf(u.dataCadastro()));
        return u.comId(id);
    }

    public int atualizar(Usuario u) {
        return jdbc.update("UPDATE T_CC_USUARIO SET NOME = ?, EMAIL = ?, PERFIL = ? WHERE ID_USUARIO = ?",
                u.nome(), u.email(), u.perfil(), u.id());
    }

    public int excluir(long id) {
        return jdbc.update("DELETE FROM T_CC_USUARIO WHERE ID_USUARIO = ?", id);
    }
}
