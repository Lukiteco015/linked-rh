package TrainHub.api.domain.turmaparticipante;

import TrainHub.api.domain.turmaparticipante.dto.DadosCadastroTurmaParticipante;
import TrainHub.api.infra.repository.BaseRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class TurmaParticipanteRepository extends BaseRepository<TurmaParticipante> {

    public TurmaParticipanteRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, "turma_participante", "codigo");
    }

    @Override
    protected RowMapper<TurmaParticipante> getRowMapper() {
        return (rs, rowNum) -> new TurmaParticipante(
                rs.getLong("codigo"),
                rs.getLong("turma_id"),
                rs.getLong("funcionario_id")
        );
    }

    public Long cadastrar(DadosCadastroTurmaParticipante dto) {

        String sql = "INSERT INTO turma_participante(turma_id, funcionario_id) VALUES (?,?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"codigo"});
            ps.setLong(1, dto.turma_id());
            ps.setLong(2, dto.funcionario_id());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

}
