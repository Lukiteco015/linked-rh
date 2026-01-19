package TrainHub.api.domain.funcionario;

import TrainHub.api.infra.repository.BaseRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class FuncionarioRepository extends BaseRepository<Funcionario> {

    public FuncionarioRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, "funcionario", "codigo");
    }

    @Override
    protected RowMapper<Funcionario> getRowMapper() {
        return (rs, rowNum) -> new Funcionario(
                rs.getLong("codigo"),
                rs.getString("nome"),
                rs.getString("cpf"),
                rs.getObject("nascimento", LocalDate.class),
                rs.getString("cargo"),
                rs.getObject("admissao", LocalDate.class),
                rs.getBoolean("status")
        );
    }
}
