package io.github.joosecj.mscartoes.application;

import io.github.joosecj.mscartoes.application.DTO.CartaoSaveRequest;
import io.github.joosecj.mscartoes.application.DTO.CartoesPorClieteResponse;
import io.github.joosecj.mscartoes.domain.Cartao;
import io.github.joosecj.mscartoes.domain.ClienteCartao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "cartoes")
@RequiredArgsConstructor
public class CartoesController {

    private final CartaoService cartaoService;
    private final ClienteCartaoService clienteCartaoService;

    @GetMapping
    public String status(){
        return "Ok";
    }
    @PostMapping
    public ResponseEntity cadastrar(@RequestBody CartaoSaveRequest request) {
        Cartao cartao = request.toModel();
        cartaoService.save(cartao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAteh(@RequestParam ("renda") Long renda) {
        List<Cartao> cartaoList = cartaoService.getCartaosRendaMenorIgual(renda);
        return ResponseEntity.ok(cartaoList);
    }
    @GetMapping(params = "cpf")
    public ResponseEntity<List<CartoesPorClieteResponse>> getCartoesByCliente(@RequestParam("cpf") String cpf) {
        List<ClienteCartao> clienteCartaoList = clienteCartaoService.listaCarteosByCpf(cpf);
        List<CartoesPorClieteResponse> responseList = clienteCartaoList.stream()
                .map(CartoesPorClieteResponse::fromModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }
}
