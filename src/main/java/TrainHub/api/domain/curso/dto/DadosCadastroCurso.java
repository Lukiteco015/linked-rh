package TrainHub.api.domain.curso.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroCurso(

    @NotBlank(message = "O nome é obrigatório")
    String nome,

    @NotBlank(message = "A descrição é obrigatória")
    String descricao,

    @NotNull(message = "O campo não pode ser nulo")
    @Min(value = 60, message = "A duração mínima deve ser de 60")
    Integer duracao
) {}
