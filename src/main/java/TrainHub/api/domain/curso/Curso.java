package TrainHub.api.domain.curso;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "codigo")
public class Curso {

    private Long codigo;
    private String nome;
    private String descricao;
    private int duracao;
}
