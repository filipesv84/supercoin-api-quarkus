package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.domain.entity.Conta;
import org.acme.service.TransacaoService;

import java.util.List;

@Path("/contas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ContaResource {
    @GET
    public List<Conta> listar() {
        return Conta.listAll();
    }

    @POST
    @Transactional
    public Response criar(Conta conta) {
        conta.persist();
        return Response.status(Response.Status.CREATED).entity(conta).build();
    }

    @Inject
    TransacaoService transacaoService;

    @POST
    @Path("/{id}/deposito")
    public Response depositar(@PathParam("id") Long id, Double valor) {
        transacaoService.realizarDeposito(id, valor);
        return Response.ok("Depósito realizado com sucesso!").build();
    }

    @POST
    @Path("/{id}/saque")
    public Response sacar(@PathParam("id") Long id, Double valor) {
        transacaoService.realizarSaque(id, valor);
        return Response.ok("Saque realizado com sucesso!").build();
    }

}
