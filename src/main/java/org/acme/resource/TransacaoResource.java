package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.domain.entity.Transacao;
import org.acme.service.TransacaoService;
import org.acme.service.TransacaoService;

import java.util.List;

@Path("/transacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransacaoResource {

    @Inject
    TransacaoService transacaoService;

    @GET
    public List<Transacao> listar() {
        return Transacao.listAll();
    }

    @POST
    public Transacao realizar (@Valid Transacao transacao) {
        return transacaoService.realizarTransacao(
                transacao.conta.id,
                transacao.valor,
                transacao.tipo,
                transacao.bancoDestino,
                transacao.contaDestino
                );
    }

    @GET
    @Path("/conta/{contaId}")
    public List<Transacao> extratoPorConta(@PathParam("contaId") Long contaId) {
        return Transacao.find("conta.id", contaId).list();
    }

}