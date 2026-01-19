package TrainHub.api.service;

import TrainHub.api.domain.curso.CursoRepository;
import TrainHub.api.domain.turma.Turma;
import TrainHub.api.domain.turma.TurmaRepository;
import TrainHub.api.domain.turma.dto.DadosAtualizarTurma;
import TrainHub.api.domain.turma.dto.DadosCadastroTurma;
import TrainHub.api.domain.turma.dto.DadosDetalhamentoTurma;
import TrainHub.api.domain.turma.dto.DadosDetalhamentoTurmaComQtd;
import TrainHub.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class TurmaService {

    @Autowired
    private TurmaRepository repository;
    @Autowired
    private CursoRepository cursoRepository;

    @Transactional
    public DadosDetalhamentoTurma cadastrar(DadosCadastroTurma dto) {

        cursoRepository.buscarPorId(dto.curso_id());
        validarRegrasData(dto.inicio(), dto.fim());

        Long codigo = repository.cadastrar(dto);

        return new DadosDetalhamentoTurma(repository.buscarPorId(codigo));
    }

    @Transactional
    public DadosDetalhamentoTurma atualizar(DadosAtualizarTurma dto, Long codigo) {

        Turma turmaAtual = repository.buscarPorId(codigo);

        LocalDate novoInicio = dto.inicio() != null ? dto.inicio() : turmaAtual.getInicio();
        LocalDate novoFim = dto.fim() != null ? dto.fim() : turmaAtual.getFim();

        validarRegrasData(novoInicio, novoFim);

        var turma = repository.atualizar(codigo, dto);

        return new DadosDetalhamentoTurma(turma);

    }

    public Page<DadosDetalhamentoTurma> listarPaginado(Pageable paginacao) {
        return repository.listarPaginado(paginacao, "inicio").map(DadosDetalhamentoTurma::new);
    }

    public DadosDetalhamentoTurma listarPorCodigo(Long codigo) {
        return new DadosDetalhamentoTurma(repository.buscarPorId(codigo));
    }

    public List<DadosDetalhamentoTurmaComQtd> buscarTurmasPorCurso(Long cursoId) {
        cursoRepository.buscarPorId(cursoId);
        return repository.listarTurmasPorCursoComQtd(cursoId);
    }

    @Transactional
    public void excluir(Long codigo) {
        repository.excluir(codigo);
    }

    private void validarRegrasData(LocalDate inicio, LocalDate fim) {
        LocalDate hoje = LocalDate.now();

        if(inicio.isBefore(hoje)) {
            throw new ValidacaoException("A turma deve ser agendada para o ano corrente: " + hoje.getYear());
        }

        if (inicio.getYear() != hoje.getYear()) {
            throw new ValidacaoException("A turma deve ser agendada para o ano corrente: " + hoje.getYear());
        }

        if (inicio.getMonthValue() < hoje.getMonthValue()) {
            throw new ValidacaoException("A data de início deve ser no mês atual ou meses futuros deste ano.");
        }

        if (fim.isBefore(inicio)) {
            throw new ValidacaoException("A data de término não pode ser anterior à data de início.");
        }
    }
}
