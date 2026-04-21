package org.acme.domain.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Cliente extends PanacheEntity {

    @NotBlank(message = "O nome não pode estar em branco")
    public String nome;

    @NotBlank(message = "O CPF é obrigatório")
    @Size(min = 11, max = 11, message = "O CPF deve ter 11 dígitos")
    @jakarta.persistence.Column(updatable = false)   // Impede que o Hibernate tente mexer no CPF em comandos de Update
    public String cpf;


    @Email(message = "O e-mail deve ser válido")
    public String email;

    public String senha;
}
