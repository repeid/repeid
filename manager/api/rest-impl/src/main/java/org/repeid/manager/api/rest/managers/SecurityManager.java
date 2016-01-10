package org.repeid.manager.api.rest.managers;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.repeid.manager.api.beans.exceptions.StorageException;
import org.repeid.manager.api.beans.representations.security.RoleRepresentation;
import org.repeid.manager.api.beans.representations.security.UserRepresentation;
import org.repeid.manager.api.model.security.RoleModel;
import org.repeid.manager.api.model.security.UserModel;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SecurityManager {

    public void update(UserModel model, UserRepresentation rep) throws StorageException {
        model.setFullName(rep.getFullName());
        model.setEmail(rep.getEmail());
        model.commit();
    }

    public void update(RoleModel model, RoleRepresentation rep) throws StorageException {
        model.setDescription(rep.getDescription());
        model.setAutoGrant(rep.getAutoGrant());
        model.setPermissions(rep.getPermissions());
        model.commit();
    }

}