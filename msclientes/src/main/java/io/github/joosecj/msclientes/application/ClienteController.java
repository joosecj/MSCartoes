package io.github.joosecj.msclientes.application;

import io.github.joosecj.msclientes.application.representation.ClienteSaveRequest;
import io.github.joosecj.msclientes.domain.Cliente;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "clientes")
@RequiredArgsConstructor
@Slf4j
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public String status(){
        log.info("Obtendo o status do microservice de cliente");
        return "Ok";
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ClienteSaveRequest request) {
        Cliente cliente = request.toModel();
        clienteService.save(cliente);
        URI headerLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .query("cpf={cpf}")
                .buildAndExpand(cliente.getCpf())
                .toUri();
        return ResponseEntity.created(headerLocation).build();
    }

    @GetMapping(params = "cpf")
    public ResponseEntity getCliente(String cpf) {
        var cliente = clienteService.getByCPF(cpf);
        if(cliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }
 }