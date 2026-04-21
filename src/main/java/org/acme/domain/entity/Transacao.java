package org.acme.domain.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import org.acme.domain.enums.TipoTransacao;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

@Entity
public class Transacao extends PanacheEntity {

    @Positive(message = "O valor deve ser maior que zero")
    public Double valor;

    public Double tarifa;
    public LocalDateTime dataHora;
    public TipoTransacao tipo;

    public String bancoDestino;
    public String contaDestino;
    public String chavePix;

    @ManyToOne
    public Conta conta;
}
