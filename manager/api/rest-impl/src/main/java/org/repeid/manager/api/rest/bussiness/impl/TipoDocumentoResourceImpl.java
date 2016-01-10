package org.repeid.manager.api.rest.bussiness.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.repeid.manager.api.beans.exceptions.StorageException;
import org.repeid.manager.api.beans.representations.TipoDocumentoRepresentation;
import org.repeid.manager.api.beans.representations.security.PermissionType;
import org.repeid.manager.api.model.TipoDocumentoModel;
import org.repeid.manager.api.model.TipoDocumentoProvider;
import org.repeid.manager.api.model.utils.ModelToRepresentation;
import org.repeid.manager.api.rest.bussiness.TipoDocumentoResource;
import org.repeid.manager.api.rest.bussiness.TiposDocumentoResource;
import org.repeid.manager.api.rest.contract.exceptions.NotAuthorizedException;
import org.repeid.manager.api.rest.contract.exceptions.SystemErrorException;
import org.repeid.manager.api.rest.contract.exceptions.TipoDocumentoNotFoundException;
import org.repeid.manager.api.rest.impl.util.ExceptionFactory;
import org.repeid.manager.api.rest.managers.TipoDocumentoManager;
import org.repeid.manager.api.security.ISecurityContext;

@Stateless
public class TipoDocumentoResourceImpl implements TipoDocumentoResource {

    @PathParam(TiposDocumentoResource.TIPO_DOCUMENTO_ID)
    private String tipoDocumentoId;

    @Inject
    private TipoDocumentoProvider tipoDocumentoProvider;

    @Inject
    private TipoDocumentoManager tipoDocumentoManager;

    @Inject
    private ISecurityContext iSecurityContext;

    private TipoDocumentoModel getTipoDocumentoModel() throws StorageException {
        return tipoDocumentoProvider.findById(tipoDocumentoId);
    }

    @Override
    public TipoDocumentoRepresentation toRepresentation()
            throws TipoDocumentoNotFoundException, NotAuthorizedException {
        if (!iSecurityContext.hasPermission(PermissionType.tipoDocumentoView))
            throw ExceptionFactory.notAuthorizedException();

        try {
            TipoDocumentoModel tipoDocumento = getTipoDocumentoModel();
            if (tipoDocumento == null) {
                throw ExceptionFactory.tipoDocumentoNotFoundException(tipoDocumentoId);
            }
            return ModelToRepresentation.toRepresentation(tipoDocumento);
        } catch (StorageException e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public void update(TipoDocumentoRepresentation rep)
            throws TipoDocumentoNotFoundException, NotAuthorizedException {
        if (!iSecurityContext.hasPermission(PermissionType.tipoDocumentoEdit))
            throw ExceptionFactory.notAuthorizedException();

        try {
            TipoDocumentoModel tipoDocumento = getTipoDocumentoModel();
            if (tipoDocumento == null) {
                throw ExceptionFactory.tipoDocumentoNotFoundException(tipoDocumentoId);
            }
            tipoDocumentoManager.update(tipoDocumento, rep);
        } catch (StorageException e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public void enable() throws TipoDocumentoNotFoundException, NotAuthorizedException {
        if (!iSecurityContext.hasPermission(PermissionType.tipoDocumentoEdit))
            throw ExceptionFactory.notAuthorizedException();

        try {
            TipoDocumentoModel tipoDocumento = getTipoDocumentoModel();
            if (tipoDocumento == null) {
                throw ExceptionFactory.tipoDocumentoNotFoundException(tipoDocumentoId);
            }
            tipoDocumentoManager.enable(tipoDocumento);
        } catch (StorageException e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public void disable() throws TipoDocumentoNotFoundException, NotAuthorizedException {
        if (!iSecurityContext.hasPermission(PermissionType.tipoDocumentoEdit))
            throw ExceptionFactory.notAuthorizedException();

        try {
            TipoDocumentoModel tipoDocumento = getTipoDocumentoModel();
            if (tipoDocumento == null) {
                throw ExceptionFactory.tipoDocumentoNotFoundException(tipoDocumentoId);
            }
            tipoDocumentoManager.disable(tipoDocumento);
        } catch (StorageException e) {
            throw new SystemErrorException(e);
        }
    }

    @Override
    public Response remove() throws TipoDocumentoNotFoundException, NotAuthorizedException {
        if (!iSecurityContext.hasPermission(PermissionType.tipoDocumentoAdmin))
            throw ExceptionFactory.notAuthorizedException();

        try {
            TipoDocumentoModel tipoDocumento = getTipoDocumentoModel();
            if (tipoDocumento == null) {
                throw ExceptionFactory.tipoDocumentoNotFoundException(tipoDocumentoId);
            }

            boolean removed = tipoDocumentoProvider.remove(tipoDocumento);
            if (removed) {
                return Response.noContent().build();
            } else {
                throw ExceptionFactory.tipoDocumentoLockedException(tipoDocumentoId);
            }
        } catch (StorageException e) {
            throw new SystemErrorException(e);
        }
    }

}
