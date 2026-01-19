package TrainHub.api.domain.turma.dto;

import java.time.LocalDate;

public record DadosAtualizarTurma(

   LocalDate inicio,
   LocalDate fim,
   String local
) {}
