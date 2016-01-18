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
package org.repeid.manager.test.api.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.repeid.manager.api.beans.exceptions.StorageException;
import org.repeid.manager.api.model.TipoDocumentoModel;
import org.repeid.manager.api.model.TipoDocumentoProvider;
import org.repeid.manager.api.model.enums.TipoPersona;
import org.repeid.manager.api.model.search.SearchCriteriaFilterOperator;
import org.repeid.manager.api.model.search.SearchCriteriaModel;
import org.repeid.manager.api.model.search.SearchResultsModel;
import org.repeid.manager.test.api.AbstractTest;

public class TipoDocumentoProviderTest extends AbstractTest {

    @Inject
    private TipoDocumentoProvider tipoDocumentoProvider;

    @Test
    public void findByAbreviatura() throws StorageException {
        TipoDocumentoModel model1 = tipoDocumentoProvider.create("DNI", "Documento nacional de identidad", 8,
                TipoPersona.NATURAL);

        String abreviatura = model1.getAbreviatura();
        TipoDocumentoModel model2 = tipoDocumentoProvider.findByAbreviatura(abreviatura);

        assertThat("model1 debe ser igual a model2", model1, is(equalTo(model2)));
    }

    @Test
    public void create() throws StorageException {
        TipoDocumentoModel model = tipoDocumentoProvider.create("DNI", "Documento nacional de identidad", 8,
                TipoPersona.NATURAL);

        assertThat("model no debe ser null", model, is(notNullValue()));
        assertThat("estado debe ser true", model.getEstado(), is(true));
    }

    @Test
    public void remove() throws StorageException {
        TipoDocumentoModel model1 = tipoDocumentoProvider.create("DNI", "Documento nacional de identidad", 8,
                TipoPersona.NATURAL);

        String abreviatura = model1.getAbreviatura();
        boolean result = tipoDocumentoProvider.remove(model1);

        TipoDocumentoModel model2 = tipoDocumentoProvider.findByAbreviatura(abreviatura);

        assertThat("result false", result, is(true));
        assertThat("model2 is notNull", model2, is(nullValue()));
    }

    @Test
    public void search1() throws StorageException {
        @SuppressWarnings("unused")
        TipoDocumentoModel model1 = tipoDocumentoProvider.create("DNI", "Documento nacional de identidad", 8,
                TipoPersona.NATURAL);
        @SuppressWarnings("unused")
        TipoDocumentoModel model2 = tipoDocumentoProvider.create("P.NAC", "Partida de nacimiento", 11,
                TipoPersona.NATURAL);

        List<TipoDocumentoModel> searched = tipoDocumentoProvider.getAll();

        assertThat("searched is Null", searched, is(notNullValue()));
        assertThat("searched.getTotalSize() is not 2", searched.size(), is(2));
    }

    @Test
    public void search2() throws StorageException {
        TipoDocumentoModel model1 = tipoDocumentoProvider.create("DNI", "Documento nacional de identidad", 8,
                TipoPersona.NATURAL);
        @SuppressWarnings("unused")
        TipoDocumentoModel model2 = tipoDocumentoProvider.create("P.NAC", "Partida de nacimiento", 11,
                TipoPersona.NATURAL);

        model1.setEstado(false);
        model1.commit();

        List<TipoDocumentoModel> searched = tipoDocumentoProvider.getAll();

        assertThat("searched is Null", searched, is(notNullValue()));
        assertThat("searched.getTotalSize() is not 1", searched.size(), is(2));
    }

    @Test
    public void searchCriteria1() throws StorageException {
        TipoDocumentoModel model1 = tipoDocumentoProvider.create("DNI", "Documento nacional de identidad", 8,
                TipoPersona.NATURAL);
        @SuppressWarnings("unused")
        TipoDocumentoModel model2 = tipoDocumentoProvider.create("P.NAC", "Partida de nacimiento", 11,
                TipoPersona.NATURAL);
        @SuppressWarnings("unused")
        TipoDocumentoModel model3 = tipoDocumentoProvider.create("RUC", "Registro unico de contribuyente", 11,
                TipoPersona.JURIDICA);

        model1.setEstado(false);
        model1.commit();

        // add filters
        SearchCriteriaModel criteria = new SearchCriteriaModel();
        criteria.addFilter("tipoPersona", TipoPersona.NATURAL.toString(), SearchCriteriaFilterOperator.eq);

        SearchResultsModel<TipoDocumentoModel> searched = tipoDocumentoProvider.search(criteria);

        assertThat("searched is Null", searched, is(notNullValue()));
        assertThat("searched.getTotalSize() is not 2", searched.getTotalSize(), is(2));
        assertThat("searched.getModels() is not 2", searched.getModels().size(), is(2));
    }

    @Test
    public void searchCriteria2() throws StorageException {
        TipoDocumentoModel model1 = tipoDocumentoProvider.create("DNI", "Documento nacional de identidad", 8,
                TipoPersona.NATURAL);
        @SuppressWarnings("unused")
        TipoDocumentoModel model2 = tipoDocumentoProvider.create("P.NAC", "Partida de nacimiento", 11,
                TipoPersona.NATURAL);
        @SuppressWarnings("unused")
        TipoDocumentoModel model3 = tipoDocumentoProvider.create("RUC", "Registro unico de contribuyente", 11,
                TipoPersona.JURIDICA);

        model1.setEstado(false);
        model1.commit();

        // add filters
        SearchCriteriaModel criteria = new SearchCriteriaModel();
        criteria.addFilter("estado", true, SearchCriteriaFilterOperator.bool_eq);

        SearchResultsModel<TipoDocumentoModel> searched = tipoDocumentoProvider.search(criteria);

        assertThat("searched is Null", searched, is(notNullValue()));
        assertThat("searched.getTotalSize() is not 2", searched.getTotalSize(), is(2));
        assertThat("searched.getModels() is not 2", searched.getModels().size(), is(2));
    }

    @Test
    public void searchCriteria3() throws StorageException {
        TipoDocumentoModel model1 = tipoDocumentoProvider.create("DNI", "Documento nacional de identidad", 8,
                TipoPersona.NATURAL);
        @SuppressWarnings("unused")
        TipoDocumentoModel model2 = tipoDocumentoProvider.create("P.NAC", "Partida de nacimiento", 11,
                TipoPersona.NATURAL);
        @SuppressWarnings("unused")
        TipoDocumentoModel model3 = tipoDocumentoProvider.create("RUC", "Registro unico de contribuyente", 11,
                TipoPersona.JURIDICA);

        model1.setEstado(false);
        model1.commit();

        // add filters
        SearchCriteriaModel criteria = new SearchCriteriaModel();
        criteria.addFilter("tipoPersona", TipoPersona.NATURAL.toString(), SearchCriteriaFilterOperator.eq);
        criteria.addFilter("estado", true, SearchCriteriaFilterOperator.bool_eq);

        SearchResultsModel<TipoDocumentoModel> searched = tipoDocumentoProvider.search(criteria);

        assertThat("searched is Null", searched, is(notNullValue()));
        assertThat("searched.getTotalSize() is not 1", searched.getTotalSize(), is(1));
        assertThat("searched.getModels() is not 1", searched.getModels().size(), is(1));
    }

    @Test
    public void searchCriteriaFiltertext1() throws StorageException {
        @SuppressWarnings("unused")
        TipoDocumentoModel model1 = tipoDocumentoProvider.create("DNI", "Documento nacional de identidad", 8,
                TipoPersona.NATURAL);
        @SuppressWarnings("unused")
        TipoDocumentoModel model2 = tipoDocumentoProvider.create("P.NAC", "Partida de nacimiento", 11,
                TipoPersona.NATURAL);
        @SuppressWarnings("unused")
        TipoDocumentoModel model3 = tipoDocumentoProvider.create("Pasaporte", "Pasaporte", 11,
                TipoPersona.NATURAL);
        @SuppressWarnings("unused")
        TipoDocumentoModel model4 = tipoDocumentoProvider.create("RUC", "Registro unico de contribuyente", 11,
                TipoPersona.JURIDICA);

        SearchCriteriaModel criteria = new SearchCriteriaModel();
        SearchResultsModel<TipoDocumentoModel> searched = tipoDocumentoProvider.search(criteria, "ruc");

        assertThat("searched is Null", searched, is(notNullValue()));
        assertThat("searched.getTotalSize() is not 1", searched.getTotalSize(), is(1));
        assertThat("searched.getModels() is not 1", searched.getModels().size(), is(1));
    }

    @Test
    public void searchCriteriaFiltertext2() throws StorageException {
        TipoDocumentoModel model1 = tipoDocumentoProvider.create("DNI", "Documento nacional de identidad", 8,
                TipoPersona.NATURAL);
        @SuppressWarnings("unused")
        TipoDocumentoModel model2 = tipoDocumentoProvider.create("P.NAC", "Partida de nacimiento", 11,
                TipoPersona.NATURAL);
        @SuppressWarnings("unused")
        TipoDocumentoModel model3 = tipoDocumentoProvider.create("Pasaporte", "Pasaporte", 11,
                TipoPersona.NATURAL);
        @SuppressWarnings("unused")
        TipoDocumentoModel model4 = tipoDocumentoProvider.create("RUC", "Registro unico de contribuyente", 11,
                TipoPersona.JURIDICA);

        model1.setEstado(false);
        model1.commit();

        // add filters
        SearchCriteriaModel criteria = new SearchCriteriaModel();
        criteria.addFilter("estado", true, SearchCriteriaFilterOperator.bool_eq);

        SearchResultsModel<TipoDocumentoModel> searched = tipoDocumentoProvider.search(criteria, "dni");

        assertThat(searched, is(notNullValue()));
        assertThat(searched.getTotalSize(), is(0));
        assertThat(searched.getModels().size(), is(0));
    }

    @Test
    public void searchCriteriaFiltertext3() throws StorageException {
        @SuppressWarnings("unused")
        TipoDocumentoModel model1 = tipoDocumentoProvider.create("DNI", "Documento nacional de identidad", 8,
                TipoPersona.NATURAL);
        TipoDocumentoModel model2 = tipoDocumentoProvider.create("P.NAC", "Partida de nacimiento", 11,
                TipoPersona.NATURAL);
        @SuppressWarnings("unused")
        TipoDocumentoModel model3 = tipoDocumentoProvider.create("Pasaporte", "Pasaporte", 11,
                TipoPersona.NATURAL);
        @SuppressWarnings("unused")
        TipoDocumentoModel model4 = tipoDocumentoProvider.create("RUC", "Registro unico de contribuyente", 11,
                TipoPersona.JURIDICA);

        model2.setEstado(false);
        model2.commit();

        // add filters
        SearchCriteriaModel criteria = new SearchCriteriaModel();
        criteria.addFilter("tipoPersona", TipoPersona.NATURAL.toString(), SearchCriteriaFilterOperator.eq);
        criteria.addFilter("estado", true, SearchCriteriaFilterOperator.bool_eq);

        SearchResultsModel<TipoDocumentoModel> searched = tipoDocumentoProvider.search(criteria, "dni");

        assertThat(searched, is(notNullValue()));
        assertThat(searched.getTotalSize(), is(1));
        assertThat(searched.getModels().size(), is(1));
    }

}
