package org.repeid.manager.api.rest.bussiness.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.repeid.manager.api.core.representations.idm.AccionistaRepresentation;
import org.repeid.manager.api.model.AccionistaModel;
import org.repeid.manager.api.model.AccionistaProvider;
import org.repeid.manager.api.model.utils.ModelToRepresentation;
import org.repeid.manager.api.rest.bussiness.AccionistaResource;

@Stateless
public class AccionistaResourceImpl implements AccionistaResource {

    @PathParam("idAccionista")
    private String idAccionista;

    @Inject
    private AccionistaProvider accionistaProvider;

    // @Inject
    // private AccionistaManager accionistaManager;

    private AccionistaModel getAccionistaModel() {
        return accionistaProvider.findById(idAccionista);
    }

    @Override
    public AccionistaRepresentation toRepresentation() {
        AccionistaRepresentation rep = ModelToRepresentation.toRepresentation(getAccionistaModel());
        if (rep != null) {
            return rep;
        } else {
            throw new NotFoundException("Accionista no encontrado");
        }
    }

    @Override
    public void update(AccionistaRepresentation rep) {
        // accionistaManager.update(getAccionistaModel(), rep);
    }

    @Override
    public Response remove() {
        /*
         * AccionistaModel accionistaModel = getAccionistaModel(); if
         * (accionistaModel == null) { throw new NotFoundException(
         * "Accionista no encontrado"); } boolean removed =
         * accionistaProvider.remove(accionistaModel); if (removed) { return
         * Response.noContent().build(); } else { return ErrorResponse.error(
         * "Accionista no pudo ser eliminado", Response.Status.BAD_REQUEST); }
         */
        return null;
    }

}
