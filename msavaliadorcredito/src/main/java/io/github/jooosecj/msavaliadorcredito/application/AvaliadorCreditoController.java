package io.github.jooosecj.msavaliadorcredito.application;

import io.github.jooosecj.msavaliadorcredito.application.ex.DadosClienteNotFoundException;
import io.github.jooosecj.msavaliadorcredito.application.ex.ErroComunicacaoMicorservicesException;
import io.github.jooosecj.msavaliadorcredito.domain.model.RetornoAvaliacaoCliente;
import io.github.jooosecj.msavaliadorcredito.domain.model.SituacaoCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("avaliacoes-credito")
@RequiredArgsConstructor
public class AvaliadorCreditoController {

    private final AvaliadorCreditoService avaliadorCreditoService;

    @GetMapping
    public String status() {
        return "Ok";
    }

    @GetMapping(value = "situacao-cliente", params = "cpf")
    public ResponseEntity getSituacaoCliente(@RequestParam("cpf") String cpf) {
        try {
            SituacaoCliente situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
            return ResponseEntity.ok(situacaoCliente);
        } catch (DadosClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMicorservicesException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity realizarAvaliacao(@RequestBody DadosAvaliacao dadosAvaliacao) {
        try {
            return ResponseEntity.ok(avaliadorCreditoService.realizarAvaliacao(dadosAvaliacao.getCpf(), dadosAvaliacao.getRenda()));
        } catch (DadosClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMicorservicesException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }

    }
}
