package org.acme.domain.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Funcionario extends PanacheEntity {

    @NotBlank(message = "O nome é obrigatório")
    public String nome;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "E-mail inválido")
    public String email;

    @NotBlank(message = "A senha é obrigatória")
    public String senha;

    public String cargo; // Aqui salvaremos "GERENTE" ou "ATENDENTE"

    public String matricula;
}
