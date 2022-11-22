package io.github.jooosecj.msavaliadorcredito.application;

import io.github.jooosecj.msavaliadorcredito.clients.CartoesResourceClient;
import io.github.jooosecj.msavaliadorcredito.clients.ClienteResourceClient;
import io.github.jooosecj.msavaliadorcredito.domain.model.CartaoCliente;
import io.github.jooosecj.msavaliadorcredito.domain.model.DadosCliente;
import io.github.jooosecj.msavaliadorcredito.domain.model.SituacaoCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clienteResourceClient;
    private final CartoesResourceClient cartoesResourceClient;

    public SituacaoCliente obterSituacaoCliente(String cpf) {
        ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceClient.getCliente(cpf);
        ResponseEntity<List<CartaoCliente>> cartoesByCliente = cartoesResourceClient.getCartoesByCliente(cpf);
        return SituacaoCliente
                .builder()
                .cliente(dadosClienteResponse.getBody())
                .cartoes(cartoesByCliente.getBody())
                .build();
    }

}
