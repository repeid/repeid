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
package org.repeid.manager.api.jpa.models;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import org.repeid.manager.api.jpa.entities.AccionistaEntity;
import org.repeid.manager.api.model.AccionistaModel;
import org.repeid.manager.api.model.PersonaJuridicaModel;
import org.repeid.manager.api.model.PersonaNaturalModel;

/**
 * @author <a href="mailto:carlosthe19916@gmail.com">Carlos Feria</a>
 */

public class AccionistaAdapter implements AccionistaModel {

	private AccionistaEntity accionistaEntity;
	private EntityManager em;

	public AccionistaAdapter(EntityManager em, AccionistaEntity accionistaEntity) {
		this.em = em;
		this.accionistaEntity = accionistaEntity;
	}

	public AccionistaEntity getAccionistaEntity() {
		return accionistaEntity;
	}

	@Override
	public String getId() {
		return accionistaEntity.getId();
	}

	@Override
	public PersonaNaturalModel getPersonaNatural() {
		return new PersonaNaturalAdapter(em, accionistaEntity.getPersonaNatural());
	}

	@Override
	public PersonaJuridicaModel getPersonaJuridica() {
		return new PersonaJuridicaAdapter(em, accionistaEntity.getPersonaJuridica());
	}

	@Override
	public BigDecimal getPorcentajeParticipacion() {
		return accionistaEntity.getPorcentajeParticipacion();
	}

	@Override
	public void setPorcentajeParticipacion(BigDecimal porcentajeParticipacion) {
		accionistaEntity.setPorcentajeParticipacion(porcentajeParticipacion);
	}

	public static AccionistaEntity toAccionistaEntity(AccionistaModel model, EntityManager em) {
		if (model instanceof AccionistaAdapter) {
			return ((AccionistaAdapter) model).getAccionistaEntity();
		}
		return em.getReference(AccionistaEntity.class, model.getId());
	}

	@Override
	public void commit() {
		em.merge(accionistaEntity);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getPersonaJuridica() == null) ? 0 : getPersonaJuridica().hashCode());
		result = prime * result + ((getPersonaNatural() == null) ? 0 : getPersonaNatural().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AccionistaModel))
			return false;
		AccionistaModel other = (AccionistaModel) obj;
		if (getPersonaJuridica() == null) {
			if (other.getPersonaJuridica() != null)
				return false;
		} else if (!getPersonaJuridica().equals(other.getPersonaJuridica()))
			return false;
		if (getPersonaNatural() == null) {
			if (other.getPersonaNatural() != null)
				return false;
		} else if (!getPersonaNatural().equals(other.getPersonaNatural()))
			return false;
		return true;
	}

}
