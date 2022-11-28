package io.github.joosecj.msclientes.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cpf;
    private String name;
    private Integer idade;

    public Cliente(String cpf, String name, Integer idade) {
        this.cpf = cpf;
        this.name = name;
        this.idade = idade;
    }
}
