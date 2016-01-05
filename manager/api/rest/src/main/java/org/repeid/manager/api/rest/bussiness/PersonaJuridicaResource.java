package org.repeid.manager.api.rest.bussiness;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.repeid.manager.api.core.representations.idm.PersonaJuridicaRepresentation;
import org.repeid.manager.api.rest.contract.exceptions.NotAuthorizedException;
import org.repeid.manager.api.rest.contract.exceptions.PersonaJuridicaNotFoundException;

/**
 * PersonaJuridica API. Usado para administrar personaJuridicas. Nota: Las
 * personas juridicas representan personas con razon social o empresas.
 * 
 * @author carlosthe19916@gmail.com
 */
public interface PersonaJuridicaResource {

	/**
	 * Use este endpoint para obtener informacion hacerca de una PersonaJuridica
	 * por medio de su ID.
	 * 
	 * @summary Get a PersonaJuridica by ID
	 * 
	 * @statuscode 200 Si la personaJuridica fue retornada satisfactoriamente.
	 * @return Una personaJuridica.
	 * @throws PersonaJuridicaNotFoundException
	 *             cuando el request envia una personaJuridica que no existe.
	 * @throws NotAuthorizedException
	 *             cuando no esta autorizado para invocar este metodo.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public PersonaJuridicaRepresentation toRepresentation()
			throws PersonaJuridicaNotFoundException, NotAuthorizedException;

	/**
	 * Use este endpoint para actualizar la informacion relacionada a una
	 * personaJuridica. La personaJuridica es identificada por medio de su ID.
	 * 
	 * @summary Update a PersonaJuridica by ID
	 * @servicetag admin
	 * 
	 * @param rep
	 *            Informacion actualizada de la personaJuridica.
	 * @statuscode 204 Si la personaJuridica fue actualizada satisfactoriamente.
	 * @throws PersonaJuridicaNotFoundException
	 *             cuando el request envia una personaJuridica que no existe.
	 * @throws NotAuthorizedException
	 *             cuando no esta autorizado para invocar este metodo.
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void update(PersonaJuridicaRepresentation rep)
			throws PersonaJuridicaNotFoundException, NotAuthorizedException;

	/**
	 * Use este endpoint para eliminar una personaJuridica por medio de su ID.
	 * 
	 * @summary Delete a PersonaJuridica by ID
	 * @servicetag admin
	 * 
	 * @statuscode 204 Si la personaJuridica fue eliminada.
	 * @throws PersonaJuridicaNotFoundException
	 *             cuando el request envia un tipoDocumento que no existe.
	 * @throws NotAuthorizedException
	 *             cuando no esta autorizado para invocar este metodo.
	 */
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response remove() throws PersonaJuridicaNotFoundException, NotAuthorizedException;

	@Path("accionistas")
	public AccionistasResource accionistas();

}