package io.github.jooosecj.msavaliadorcredito.application;

import feign.FeignException;
import io.github.jooosecj.msavaliadorcredito.application.ex.DadosClienteNotFoundException;
import io.github.jooosecj.msavaliadorcredito.application.ex.ErroComunicacaoMicorservicesException;
import io.github.jooosecj.msavaliadorcredito.clients.CartoesResourceClient;
import io.github.jooosecj.msavaliadorcredito.clients.ClienteResourceClient;
import io.github.jooosecj.msavaliadorcredito.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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

    public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda) throws DadosClienteNotFoundException,
            ErroComunicacaoMicorservicesException {
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceClient.getCliente(cpf);
            ResponseEntity<List<Cartao>> cartoesResponse = cartoesResourceClient.getCartoesRendaAteh(renda);
            List<Cartao> cartaoList = cartoesResponse.getBody();
            var listaCartaoAprovados = cartaoList.stream().map(cartao -> {
                DadosCliente dadosCliente = dadosClienteResponse.getBody();
                BigDecimal limeteBasico = cartao.getLimeteBasico();
                BigDecimal rendaBD = BigDecimal.valueOf(renda);
                BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());
                var fator = idadeBD.divide(BigDecimal.valueOf(10));
                CartaoAprovado cartaoAprovado = new CartaoAprovado();
                BigDecimal limiteAprovado = fator.multiply(limeteBasico);
                cartaoAprovado.setCartao(cartao.getName());
                cartaoAprovado.setBandeira(cartao.getBandeira());
                cartaoAprovado.setLimiteAprovado(limiteAprovado);
                return cartaoAprovado;
            }).collect(Collectors.toList());

            return new RetornoAvaliacaoCliente(listaCartaoAprovados);
        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicorservicesException(e.getMessage(), status);
        }
    }

}
