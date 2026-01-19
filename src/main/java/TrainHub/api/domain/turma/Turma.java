package TrainHub.api.domain.turma;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "codigo")
public class Turma {

    private Long codigo;
    private LocalDate inicio;
    private LocalDate fim;
    private String local;
    private Long curso_id;
}
