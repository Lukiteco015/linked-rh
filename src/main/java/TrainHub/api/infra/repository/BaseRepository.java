package TrainHub.api.infra.repository;

import TrainHub.api.infra.exception.RecursoNaoEncontradoException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public abstract class BaseRepository<T> {

    protected final JdbcTemplate jdbcTemplate;
    private final String tableName;
    private final String idColumn;

    public BaseRepository(JdbcTemplate jdbcTemplate, String tableName, String idColumn) {
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = tableName;
        this.idColumn = idColumn;
    }

    protected abstract RowMapper<T> getRowMapper();


    public void excluir(Long id) {
        String sql = String.format("DELETE FROM %s WHERE %s = ?", tableName, idColumn);
        int linhasAfetadas = jdbcTemplate.update(sql, id);

        if (linhasAfetadas == 0) {
            throw new RecursoNaoEncontradoException(
                    String.format("Registro não encontrado na tabela %s para exclusão", tableName));
        }
    }


    public T buscarPorId(Long id) {
        String sql = String.format("SELECT * FROM %s WHERE %s = ?", tableName, idColumn);
        return jdbcTemplate.queryForObject(sql, getRowMapper(), id);
    }


    public Page<T> listarPaginado(Pageable paginacao, String orderBy) {
        String sqlCount = String.format("SELECT COUNT(*) FROM %s", tableName);
        Long total = jdbcTemplate.queryForObject(sqlCount, Long.class);

        String sqlData = String.format("SELECT * FROM %s ORDER BY %s LIMIT ? OFFSET ?", tableName, orderBy);
        List<T> lista = jdbcTemplate.query(sqlData, getRowMapper(),
                paginacao.getPageSize(),
                paginacao.getOffset());


        return new PageImpl<>(lista, paginacao, total);
    }
}