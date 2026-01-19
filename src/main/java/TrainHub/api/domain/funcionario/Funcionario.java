package TrainHub.api.domain.funcionario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Funcionario {

    private Long codigo;
    private String nome;
    private String cpf;
    private LocalDate nascimento;
    private String cargo;
    private LocalDate admissao;
    private boolean status;
}
