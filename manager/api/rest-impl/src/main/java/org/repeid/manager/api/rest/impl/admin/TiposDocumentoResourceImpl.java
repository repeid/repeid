/*******************************************************************************
 * Repeid, Home of Professional Open Source
 *
 * Copyright 2015 Sistcoop, Inc. and/or its affiliates.
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.repeid.manager.api.rest.impl.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.repeid.manager.api.beans.representations.TipoDocumentoRepresentation;
import org.repeid.manager.api.beans.representations.search.SearchCriteriaRepresentation;
import org.repeid.manager.api.beans.representations.search.SearchResultsRepresentation;
import org.repeid.manager.api.model.TipoDocumentoModel;
import org.repeid.manager.api.model.enums.TipoPersona;
import org.repeid.manager.api.model.exceptions.ModelDuplicateException;
import org.repeid.manager.api.model.provider.KeycloakSession;
import org.repeid.manager.api.model.search.SearchCriteriaModel;
import org.repeid.manager.api.model.search.SearchResultsModel;
import org.repeid.manager.api.model.utils.ModelToRepresentation;
import org.repeid.manager.api.rest.ErrorResponse;
import org.repeid.manager.api.rest.admin.TipoDocumentoResource;
import org.repeid.manager.api.rest.admin.TiposDocumentoResource;
import org.repeid.manager.api.rest.services.ServicesLogger;
import org.repeid.manager.api.rest.util.SearchCriteriaUtil;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */

public class TiposDocumentoResourceImpl implements TiposDocumentoResource {

	protected static final ServicesLogger logger = ServicesLogger.ROOT_LOGGER;

	@Context
	protected KeycloakSession session;

	@Context
	private UriInfo uriInfo;

	@Override
	public TipoDocumentoResource tipoDocumento(String tipoDocumentoId) {
		return new TipoDocumentoResourceImpl(tipoDocumentoId);
	}

	@Override
	public Response create(TipoDocumentoRepresentation rep) {
		try {
			TipoDocumentoModel tipoDocumento = session.tipoDocumentos().create(rep.getAbreviatura(),
					rep.getDenominacion(), rep.getCantidadCaracteres(), TipoPersona.valueOf(rep.getTipoPersona()));

			if (session.getTransaction().isActive()) {
				session.getTransaction().commit();
			}

			return Response.created(uriInfo.getAbsolutePathBuilder().path(tipoDocumento.getId()).build()).build();
		} catch (ModelDuplicateException e) {
			if (session.getTransaction().isActive()) {
				session.getTransaction().setRollbackOnly();
			}
			return ErrorResponse.exists("TipoDocumento ya existente");
		}
	}

	@Override
	public List<TipoDocumentoRepresentation> search(String abreviatura, String denominacion, String tipoPersona,
			Boolean estado, String filterText, Integer firstResult, Integer maxResults) {

		firstResult = firstResult != null ? firstResult : -1;
		maxResults = maxResults != null ? maxResults : -1;

		List<TipoDocumentoModel> models;
		if (filterText != null) {
			models = session.tipoDocumentos().search(filterText.trim(), firstResult, maxResults);
		} else if (denominacion != null || abreviatura != null || tipoPersona != null || estado != null) {
			Map<String, Object> attributes = new HashMap<String, Object>();
			if (abreviatura != null) {
				attributes.put(TipoDocumentoModel.ABREVIATURA, abreviatura);
			}
			if (denominacion != null) {
				attributes.put(TipoDocumentoModel.DENOMINACION, denominacion);
			}
			if (tipoPersona != null) {
				attributes.put(TipoDocumentoModel.TIPO_PERSONA, tipoPersona);
			}
			if (estado != null) {
				attributes.put(TipoDocumentoModel.ESTADO, estado);
			}
			models = session.tipoDocumentos().searchByAttributes(attributes, firstResult, maxResults);
		} else {
			models = session.tipoDocumentos().getAll(firstResult, maxResults);
		}

		List<TipoDocumentoRepresentation> results = new ArrayList<>();
		for (TipoDocumentoModel model : models) {
			results.add(ModelToRepresentation.toRepresentation(model));
		}

		logger.debug(("search()"));
		return results;
	}

	@Override
	public SearchResultsRepresentation<TipoDocumentoRepresentation> search(SearchCriteriaRepresentation criteria) {
		SearchCriteriaModel criteriaModel = SearchCriteriaUtil.getSearchCriteriaModel(criteria);

		// extract filterText
		String filterText = criteria.getFilterText();

		// search
		SearchResultsModel<TipoDocumentoModel> models = null;
		if (filterText == null) {
			models = session.tipoDocumentos().search(criteriaModel);
		} else {
			models = session.tipoDocumentos().search(criteriaModel, filterText);
		}

		SearchResultsRepresentation<TipoDocumentoRepresentation> result = new SearchResultsRepresentation<>();
		List<TipoDocumentoRepresentation> items = new ArrayList<>();
		for (TipoDocumentoModel model : models.getModels()) {
			items.add(ModelToRepresentation.toRepresentation(model));
		}
		result.setItems(items);
		result.setTotalSize(models.getTotalSize());

		logger.debug(("search()"));
		return result;
	}

}