package TrainHub.api.domain.turmaparticipante.dto;

import TrainHub.api.domain.turmaparticipante.TurmaParticipante;

public record DadosDetalhamentoTurmaParticipante(

        Long codigo,
        Long turma_id,
        Long funcionario_id
) {
    public DadosDetalhamentoTurmaParticipante(TurmaParticipante participante) {
        this(participante.getCodigo(), participante.getTurma_id(), participante.getFuncionario_id());
    }
}
