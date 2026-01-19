package TrainHub.api.service;

import TrainHub.api.domain.funcionario.FuncionarioRepository;
import TrainHub.api.domain.turma.TurmaRepository;
import TrainHub.api.domain.turma.dto.DadosCadastroTurma;
import TrainHub.api.domain.turma.dto.DadosDetalhamentoTurma;
import TrainHub.api.domain.turmaparticipante.TurmaParticipanteRepository;
import TrainHub.api.domain.turmaparticipante.dto.DadosCadastroTurmaParticipante;
import TrainHub.api.domain.turmaparticipante.dto.DadosDetalhamentoTurmaParticipante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TurmaParticipanteService {

    @Autowired
    TurmaParticipanteRepository repository;
    @Autowired
    TurmaRepository turmaRepository;
    @Autowired
    FuncionarioRepository funcionarioRepository;

    @Transactional
    public DadosDetalhamentoTurmaParticipante cadastrar(DadosCadastroTurmaParticipante dto) {

        turmaRepository.buscarPorId(dto.turma_id());
        funcionarioRepository.buscarPorId(dto.funcionario_id());

        Long codigo = repository.cadastrar(dto);

        return new DadosDetalhamentoTurmaParticipante(repository.buscarPorId(codigo));
    }

    public Page<DadosDetalhamentoTurmaParticipante> listarPaginado(Pageable paginacao) {
        return repository.listarPaginado(paginacao, "codigo").map(DadosDetalhamentoTurmaParticipante::new);
    }

    public DadosDetalhamentoTurmaParticipante listarPorCodigo(Long codigo) {
        return new DadosDetalhamentoTurmaParticipante(repository.buscarPorId(codigo));
    }

    @Transactional
    public void excluir(Long codigo) {
        repository.excluir(codigo);
    }
}
