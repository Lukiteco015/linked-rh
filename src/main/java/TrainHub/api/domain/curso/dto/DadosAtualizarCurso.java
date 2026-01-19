package TrainHub.api.domain.curso.dto;

import jakarta.validation.constraints.Min;

public record DadosAtualizarCurso(
        String nome,
        String descricao,
        @Min(60)
        Integer duracao
) {}
