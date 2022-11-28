package io.github.joosecj.msclientes.application;

import io.github.joosecj.msclientes.domain.Cliente;
import io.github.joosecj.msclientes.infra.repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Transactional
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> getByCPF(String cpf) {
        return clienteRepository.findByCpf(cpf);
    }
}
