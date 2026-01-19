package TrainHub.api.controller;

import TrainHub.api.domain.curso.CursoRepository;
import TrainHub.api.domain.curso.dto.DadosAtualizarCurso;
import TrainHub.api.domain.curso.dto.DadosCadastroCurso;
import TrainHub.api.domain.curso.dto.DadosDetalhamentoCurso;
import TrainHub.api.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService service;

    @Autowired
    private CursoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastroCurso(@Valid @RequestBody DadosCadastroCurso dto, UriComponentsBuilder uriComponentsBuilder) {

        Long codigo = repository.cadastrar(dto);

        var detalhamento = new DadosDetalhamentoCurso(codigo, dto.nome(), dto.descricao(), dto.duracao());

        var uri = uriComponentsBuilder.path("/cursos/{codigo}").buildAndExpand(codigo).toUri();

        return ResponseEntity.created(uri).body(detalhamento);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity atualizar(@PathVariable Long codigo, @Valid @RequestBody DadosAtualizarCurso dto) {
        var detalhamento = service.atualizar(codigo, dto);
        return ResponseEntity.ok(detalhamento);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity excluir(@PathVariable Long codigo) {
        service.excluir(codigo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoCurso>> listar(@PageableDefault(size = 10) Pageable pageable) {
        var page = service.listarPaginado(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity detalhar(@PathVariable Long codigo) {
        var detalhamento = service.listarPorCodigo(codigo);
        return ResponseEntity.ok(detalhamento);
    }
}
