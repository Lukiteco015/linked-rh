package TrainHub.api.domain.turmaparticipante.dto;

import jakarta.validation.constraints.NotNull;

public record DadosCadastroTurmaParticipante(

        @NotNull(message = "O ID da turma é obrigatório")
        Long turma_id,
        @NotNull(message = "O ID do funcionário é obrigatório")
        Long funcionario_id
) {}
