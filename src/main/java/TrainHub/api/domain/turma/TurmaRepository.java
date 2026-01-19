package TrainHub.api.domain.turma;

import TrainHub.api.domain.turma.dto.DadosAtualizarTurma;
import TrainHub.api.domain.turma.dto.DadosCadastroTurma;
import TrainHub.api.domain.turma.dto.DadosDetalhamentoTurma;
import TrainHub.api.domain.turma.dto.DadosDetalhamentoTurmaComQtd;
import TrainHub.api.infra.exception.ValidacaoException;
import TrainHub.api.infra.repository.BaseRepository;
import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TurmaRepository extends BaseRepository<Turma> {

    public TurmaRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, "turma", "codigo");
    }

    @Override
    protected RowMapper<Turma> getRowMapper() {
        return (rs, rowNum) -> new Turma(
                rs.getLong("codigo"),
                rs.getObject("inicio", LocalDate.class),
                rs.getObject("fim", LocalDate.class),
                rs.getString("local"),
                rs.getLong("curso_id")
        );
    }

    public Long cadastrar(DadosCadastroTurma dto) {

        String sql = "INSERT INTO turma(inicio, fim, local, curso_id) VALUES (?,?,?,?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"codigo"});
            ps.setObject(1, dto.inicio());
            ps.setObject(2, dto.fim());
            ps.setString(3, dto.local());
            ps.setLong(4, dto.curso_id());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Turma atualizar(Long codigo, DadosAtualizarTurma dto) {

        List<Object> parametros = new ArrayList<>();
        List<String> colunas = new ArrayList<>();

        if(dto.inicio() != null) {
            colunas.add("inicio = ?");
            parametros.add(dto.inicio());
        }

        if(dto.fim() != null) {
            colunas.add("fim = ?");
            parametros.add(dto.fim());
        }

        if (StringUtils.hasText(dto.local())) {
            colunas.add("local = ?");
            parametros.add(dto.local());
        }

        if (colunas.isEmpty()) {
            throw new ValidacaoException("Nenhum dado enviado para atualização");
        }

        parametros.add(codigo);

        String sql = "UPDATE turma SET " + String.join(", ", colunas) +
                " WHERE codigo = ?" +
                " RETURNING *";

        return jdbcTemplate.queryForObject(sql, getRowMapper(), parametros.toArray());
    }

    public Page<Turma> listarTurmaPorCurso (Long curso_id, Pageable pageable) {
        String sqlCount = "SELECT COUNT(*) FROM turma WHERE curso_id = ?";

        Long total = jdbcTemplate.queryForObject(sqlCount, Long.class, curso_id);

        String sqlData = "SELECT * FROM turma WHERE curso_id = ? ORDER BY inicio LIMIT ? OFFSET ?";

        List<Turma> lista = jdbcTemplate.query(sqlData, getRowMapper(),
                curso_id,
                pageable.getPageSize(),
                pageable.getOffset());

        return new PageImpl<>(lista, pageable, total);
    }

    public List<DadosDetalhamentoTurmaComQtd> listarTurmasPorCursoComQtd(Long cursoId) {
        String sql = """
            SELECT t.*, COUNT(tp.funcionario_id) as quantidade_funcionarios
            FROM turma t
            LEFT JOIN turma_participante tp ON t.codigo = tp.turma_id
            WHERE t.curso_id = ?
            GROUP BY t.codigo
            ORDER BY t.inicio ASC, t.fim ASC
            """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new DadosDetalhamentoTurmaComQtd(rs), cursoId);
    }

}
