package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.auth.TokenUtils;
import org.acme.domain.entity.Cliente;
import org.acme.domain.entity.Funcionario;

@Path("/auth")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    TokenUtils tokenUtils;

    @POST
    @Path("/login")
    public Response login(Cliente loginDados) {
        // 1. Busca em Funcionário (Gerente/Atendente)
        Funcionario func = Funcionario.find("email", loginDados.email).firstResult();
        if (func != null && loginDados.senha != null && loginDados.senha.equals(func.senha)) {
            String token = tokenUtils.gerarToken(func.email, func.cargo);
            return Response.ok(token).build();
        }

        // 2. Busca em Cliente
        Cliente cliente = Cliente.find("email", loginDados.email).firstResult();
        if (cliente != null && loginDados.senha != null && loginDados.senha.equals(cliente.senha)) {
            String token = tokenUtils.gerarToken(cliente.email, "CLIENTE");
            return Response.ok(token).build();
        }

        // 3. Caso não encontre nenhum ou senha errada
        return Response.status(Response.Status.UNAUTHORIZED).entity("Email ou senha inválidos").build();
    }
}
