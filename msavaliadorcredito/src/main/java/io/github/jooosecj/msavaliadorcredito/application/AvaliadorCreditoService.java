package io.github.jooosecj.msavaliadorcredito.application;

import io.github.jooosecj.msavaliadorcredito.clients.ClienteResourceClient;
import io.github.jooosecj.msavaliadorcredito.domain.model.DadosCliente;
import io.github.jooosecj.msavaliadorcredito.domain.model.SituacaoCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clienteResourceClient;

    public SituacaoCliente obterSituacaoCliente(String cpf) {
        ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceClient.getCliente(cpf);
        return SituacaoCliente
                .builder()
                .cliente(dadosClienteResponse.getBody())
                .build();
    }

}
