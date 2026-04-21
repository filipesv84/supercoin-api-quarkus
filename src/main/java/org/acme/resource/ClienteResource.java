
package org.acme.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import jakarta.ws.rs.PathParam;
import org.acme.domain.entity.Cliente;


@Path("/clientes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @GET
    @RolesAllowed("GERENTE")
    public List<Cliente> listar() {
        return Cliente.listAll();
    }

    @POST
    @Transactional
    public Response criar(@Valid Cliente cliente) {
        cliente.persist();
        return Response.status(201).entity(cliente).build();
    }


    @GET
    @Path("/{id}")
    public Cliente buscarPorId(@PathParam("id") Long id) {
        return Cliente.findById(id);
    }


    @PUT
    @Path("/{id}")
    @RolesAllowed("GERENTE")
    @Transactional
    public Response atualizar(@PathParam("id") Long id, Cliente dados) {
        // 1. Busca o cliente original no banco
        Cliente entity = Cliente.findById(id);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Cliente não encontrado").build();
        }

        // 2. Atualiza manualmente apenas o Nome e Email
        // O CPF guardado no banco (que é válido) é mantido intacto
        if (dados.nome != null) entity.nome = dados.nome;
        if (dados.email != null) entity.email = dados.email;

        // 3. Ao persistir sem mexer no CPF, o Hibernate não deve reclamar
        entity.persist();

        return Response.ok(entity).build();
    }


    @DELETE
    @Path("/{id}")
    @RolesAllowed("GERENTE")
    @Transactional
    public Response deletar(@PathParam("id") Long id) {
        Cliente entity = Cliente.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Cliente não encontrado", 404);
        }
        entity.delete();
        return Response.noContent().build(); // Retorna 204 (Sucesso, sem conteúdo)
    }


}
