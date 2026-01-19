package TrainHub.api.domain.curso.dto;

import TrainHub.api.domain.curso.Curso;

public record DadosDetalhamentoCurso(
        Long codigo,
        String nome,
        String descricao,
        int duracao
) {
    public DadosDetalhamentoCurso(Curso curso) {
        this(curso.getCodigo(), curso.getNome(), curso.getDescricao(), curso.getDuracao());
    }
}
