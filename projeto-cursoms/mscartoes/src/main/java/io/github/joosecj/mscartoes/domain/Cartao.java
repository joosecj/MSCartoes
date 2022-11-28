package io.github.joosecj.mscartoes.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
public class Cartao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private BandeiraCartao bandeira;
    private BigDecimal renda;
    private BigDecimal limeteBasico;

    public Cartao(String name,
                  BandeiraCartao bandeira,
                  BigDecimal renda,
                  BigDecimal limeteBasico) {
        this.name = name;
        this.bandeira = bandeira;
        this.renda = renda;
        this.limeteBasico = limeteBasico;
    }
}
