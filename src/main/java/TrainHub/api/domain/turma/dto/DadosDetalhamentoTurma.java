package TrainHub.api.domain.turma.dto;

import TrainHub.api.domain.turma.Turma;

import java.time.LocalDate;

public record DadosDetalhamentoTurma(

        Long codigo,
        LocalDate inicio,
        LocalDate fim,
        String local,
        Long curso_id
) {
    public DadosDetalhamentoTurma(Turma turma) {
        this(turma.getCodigo(), turma.getInicio(), turma.getFim(), turma.getLocal(), turma.getCurso_id());
    }
}
