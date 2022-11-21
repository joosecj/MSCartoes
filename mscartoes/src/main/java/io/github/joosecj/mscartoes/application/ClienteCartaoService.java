package io.github.joosecj.mscartoes.application;

import io.github.joosecj.mscartoes.domain.ClienteCartao;
import io.github.joosecj.mscartoes.infra.repositories.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {

    private final ClienteCartaoRepository clienteCartaoRepository;

    public List<ClienteCartao> listaCarteosByCpf(String cpf){
        return clienteCartaoRepository.findByCpf(cpf);
    }
}
