package TrainHub.api.controller;

import TrainHub.api.domain.turmaparticipante.dto.DadosCadastroTurmaParticipante;
import TrainHub.api.domain.turmaparticipante.dto.DadosDetalhamentoTurmaParticipante;
import TrainHub.api.service.TurmaParticipanteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/turmasparticipantes")
public class TurmaParticipanteController {

    @Autowired
    private TurmaParticipanteService service;

    @PostMapping
    public ResponseEntity cadastrar(@Valid @RequestBody DadosCadastroTurmaParticipante dto, UriComponentsBuilder uriComponentsBuilder) {
        var detalhamento = service.cadastrar(dto);

        var uri = uriComponentsBuilder.path("/turmaparticipante/{codigo}").buildAndExpand(detalhamento.codigo()).toUri();

        return ResponseEntity.created(uri).body(detalhamento);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity excluir(@PathVariable Long codigo) {
        service.excluir(codigo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoTurmaParticipante>> listar(@PageableDefault(size = 10) Pageable pageable) {
        var page = service.listarPaginado(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity detalhar(@PathVariable Long codigo) {
        var detalhamento = service.listarPorCodigo(codigo);
        return ResponseEntity.ok(detalhamento);
    }
}
