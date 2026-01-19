package TrainHub.api.domain.curso;

import TrainHub.api.domain.curso.dto.DadosAtualizarCurso;
import TrainHub.api.domain.curso.dto.DadosCadastroCurso;
import TrainHub.api.infra.exception.ValidacaoException;
import TrainHub.api.infra.repository.BaseRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CursoRepository extends BaseRepository<Curso> {

    public CursoRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, "curso", "codigo");
    }

    @Override
    protected RowMapper<Curso> getRowMapper() {
        return (rs, rowNum) -> new Curso(
                rs.getLong("codigo"),
                rs.getString("nome"),
                rs.getString("descricao"),
                rs.getInt("duracao")
        );
    }

    public Long cadastrar(DadosCadastroCurso dto) {
        String sql = "INSERT INTO curso (nome, descricao, duracao) VALUES (?, ?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"codigo"});
            ps.setString(1, dto.nome());
            ps.setString(2, dto.descricao());
            ps.setInt(3, dto.duracao());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public Curso atualizar(Long codigo, DadosAtualizarCurso dto) {

        List<Object> parametros = new ArrayList<>();
        List<String> colunas = new ArrayList<>();

        if (StringUtils.hasText(dto.nome())) {
            colunas.add("nome = ?");
            parametros.add(dto.nome());
        }

        if (StringUtils.hasText(dto.descricao())) {
            colunas.add("descricao = ?");
            parametros.add(dto.descricao());
        }

        if (dto.duracao() != null) {
            colunas.add("duracao = ?");
            parametros.add(dto.duracao());
        }

        if (colunas.isEmpty()) {
            throw new ValidacaoException("Nenhum dado enviado para atualização");
        }

        parametros.add(codigo);

        String sql = "UPDATE curso SET " + String.join(", ", colunas) +
                " WHERE codigo = ?" +
                " RETURNING *";

        return jdbcTemplate.queryForObject(sql, getRowMapper(), parametros.toArray());
    }

    public void excluirTurmasVinculadasAoCurso(Long codigoCurso) {
        String sql = "DELETE FROM turma WHERE curso_id = ?";
        jdbcTemplate.update(sql, codigoCurso);
    }
}
