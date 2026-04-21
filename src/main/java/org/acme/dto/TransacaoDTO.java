package org.acme.dto;

import org.acme.domain.enums.TipoTransacao;

public class TransacaoDTO {
    public Long contaOrigemId;
    public Long contaDestinoId;
    public Double valor;
    public TipoTransacao tipo;
    public String chavePix;

}
