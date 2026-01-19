package TrainHub.api.controller;

import TrainHub.api.domain.turma.dto.DadosAtualizarTurma;
import TrainHub.api.domain.turma.dto.DadosCadastroTurma;
import TrainHub.api.domain.turma.dto.DadosDetalhamentoTurma;
import TrainHub.api.domain.turma.dto.DadosDetalhamentoTurmaComQtd;
import TrainHub.api.service.TurmaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/turmas")
public class TurmaController {

    @Autowired
    private TurmaService service;


    @PostMapping
    public ResponseEntity cadastrar(@Valid @RequestBody DadosCadastroTurma dto, UriComponentsBuilder uriBuilder) {
        var detalhamento = service.cadastrar(dto);

        var uri = uriBuilder.path("/turmas/{codigo}").buildAndExpand(detalhamento.codigo()).toUri();

        return ResponseEntity.created(uri).body(detalhamento);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity atualizar(@PathVariable Long codigo, @Valid @RequestBody DadosAtualizarTurma dto) {
        var detalhamento = service.atualizar(dto, codigo);
        return ResponseEntity.ok(detalhamento);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity excluir(@PathVariable Long codigo) {
        service.excluir(codigo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoTurma>> listar(@PageableDefault(size = 10) Pageable pageable) {
        var page = service.listarPaginado(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity detalhar(@PathVariable Long codigo) {
        var detalhamento = service.listarPorCodigo(codigo);
        return ResponseEntity.ok(detalhamento);
    }

    @GetMapping("/por-curso/{curso_id}")
    public ResponseEntity<List<DadosDetalhamentoTurmaComQtd>> listarTurmaPorCurso(@PathVariable Long curso_id) {
        var lista = service.buscarTurmasPorCurso(curso_id);
        return ResponseEntity.ok(lista);
    }

}
