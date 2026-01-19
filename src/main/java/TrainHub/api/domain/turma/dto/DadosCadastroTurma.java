package TrainHub.api.domain.turma.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DadosCadastroTurma(

        @NotNull(message = "A data de abertura da turma é obrigatória")
        LocalDate inicio,
        @NotNull(message = "A data de termino da turma é obrigatória")
        LocalDate fim,
        @NotBlank(message = "O local endereçado para turma é obrigatório")
        String local,
        @NotNull(message = "O ID do curso é obrigatório")
        Long curso_id
) {
}
