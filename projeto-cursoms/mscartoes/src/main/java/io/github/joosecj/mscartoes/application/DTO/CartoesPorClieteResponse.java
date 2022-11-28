package io.github.joosecj.mscartoes.application.DTO;

import io.github.joosecj.mscartoes.domain.ClienteCartao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartoesPorClieteResponse {

    private String nome;
    private String bandeira;
    private BigDecimal limiteLiberado;

    public static CartoesPorClieteResponse fromModel(ClienteCartao model) {
        return new CartoesPorClieteResponse(
                model.getCartao().getName(),
                model.getCartao().getBandeira().toString(),
                model.getLimite()
        );
    }

}
