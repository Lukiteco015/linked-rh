package TrainHub.api.domain.turmaparticipante;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurmaParticipante {

    private Long codigo;
    private Long turma_id;
    private Long funcionario_id;
}
