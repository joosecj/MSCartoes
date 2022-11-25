package io.github.jooosecj.msavaliadorcredito.application.ex;

import lombok.Getter;

public class ErroComunicacaoMicorservicesException extends Exception{
    @Getter
    private Integer status;

    public ErroComunicacaoMicorservicesException(String msg, Integer status) {
        super(msg);
        this.status = status;
    }
}
