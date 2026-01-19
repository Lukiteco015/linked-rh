package TrainHub.api.domain.turma.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public record DadosDetalhamentoTurmaComQtd(
        Long codigo,
        LocalDate inicio,
        LocalDate fim,
        String local,
        Long curso_id,
        Integer quantidadeFuncionarios
) {
    public DadosDetalhamentoTurmaComQtd(ResultSet rs) throws SQLException {
        this(
                rs.getLong("codigo"),
                rs.getObject("inicio", LocalDate.class),
                rs.getObject("fim", LocalDate.class),
                rs.getString("local"),
                rs.getLong("curso_id"),
                rs.getInt("quantidade_funcionarios")
        );
    }
}
