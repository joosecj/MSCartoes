package io.github.jooosecj.msavaliadorcredito.application;

import feign.FeignException;
import io.github.jooosecj.msavaliadorcredito.application.ex.DadosClienteNotFoundException;
import io.github.jooosecj.msavaliadorcredito.application.ex.ErroComunicacaoMicorservicesException;
import io.github.jooosecj.msavaliadorcredito.clients.CartoesResourceClient;
import io.github.jooosecj.msavaliadorcredito.clients.ClienteResourceClient;
import io.github.jooosecj.msavaliadorcredito.domain.model.CartaoCliente;
import io.github.jooosecj.msavaliadorcredito.domain.model.DadosCliente;
import io.github.jooosecj.msavaliadorcredito.domain.model.SituacaoCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clienteResourceClient;
    private final CartoesResourceClient cartoesResourceClient;

    public SituacaoCliente obterSituacaoCliente(String cpf)
            throws DadosClienteNotFoundException, ErroComunicacaoMicorservicesException{
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceClient.getCliente(cpf);
            ResponseEntity<List<CartaoCliente>> cartoesByCliente = cartoesResourceClient.getCartoesByCliente(cpf);
            return SituacaoCliente
                    .builder()
                    .cliente(dadosClienteResponse.getBody())
                    .cartoes(cartoesByCliente.getBody())
                    .build();
        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicorservicesException(e.getMessage(), status);
        }

    }

}
