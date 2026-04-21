package org.acme.domain.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import org.acme.domain.enums.TipoConta;

@Entity
public class Conta extends PanacheEntity {
    public String numero;
    public Double saldo;
    public TipoConta tipo;

    @ManyToOne
    public Cliente cliente;
}
