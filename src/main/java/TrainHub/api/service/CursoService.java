package TrainHub.api.service;

import TrainHub.api.domain.curso.CursoRepository;
import TrainHub.api.domain.curso.dto.DadosAtualizarCurso;
import TrainHub.api.domain.curso.dto.DadosDetalhamentoCurso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CursoService {

    @Autowired
    CursoRepository repository;

    public Page<DadosDetalhamentoCurso> listarPaginado(Pageable paginacao) {
        return repository.listarPaginado(paginacao, "nome").map(DadosDetalhamentoCurso::new);
    }

    public DadosDetalhamentoCurso listarPorCodigo(Long codigo) {
        return new DadosDetalhamentoCurso(repository.buscarPorId(codigo));
    }

    @Transactional
    public DadosDetalhamentoCurso atualizar(Long codigo, DadosAtualizarCurso dto) {
        var curso = repository.atualizar(codigo, dto);
        return new DadosDetalhamentoCurso(curso);
    }

    @Transactional
    public void excluir(Long codigo) {
        repository.excluirTurmasVinculadasAoCurso(codigo);
        repository.excluir(codigo);
    }
}
