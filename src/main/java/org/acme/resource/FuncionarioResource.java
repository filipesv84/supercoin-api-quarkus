package org.acme.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.domain.entity.Funcionario;

@Path("/funcionarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FuncionarioResource {

    @POST
    @RolesAllowed("GERENTE") // Apenas Gerentes podem cadastrar novos funcionários
    @Transactional
    public Response cadastrarAtendente(@Valid Funcionario funcionario) {
        // Por regra de negócio, cadastros via API são sempre Atendentes
        funcionario.cargo = "ATENDENTE";
        funcionario.persist();
        return Response.status(Response.Status.CREATED).entity(funcionario).build();
    }
}
