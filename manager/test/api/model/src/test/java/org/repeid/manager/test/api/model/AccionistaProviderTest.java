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

import java.math.BigDecimal;
import java.util.Calendar;

import javax.inject.Inject;

import org.junit.Test;
import org.repeid.manager.api.beans.exceptions.StorageException;
import org.repeid.manager.api.model.AccionistaModel;
import org.repeid.manager.api.model.AccionistaProvider;
import org.repeid.manager.api.model.PersonaJuridicaModel;
import org.repeid.manager.api.model.PersonaJuridicaProvider;
import org.repeid.manager.api.model.PersonaNaturalModel;
import org.repeid.manager.api.model.PersonaNaturalProvider;
import org.repeid.manager.api.model.TipoDocumentoModel;
import org.repeid.manager.api.model.TipoDocumentoProvider;
import org.repeid.manager.api.model.enums.Sexo;
import org.repeid.manager.api.model.enums.TipoEmpresa;
import org.repeid.manager.api.model.enums.TipoPersona;
import org.repeid.manager.test.api.AbstractTest;

public class AccionistaProviderTest extends AbstractTest {

    @Inject
    private TipoDocumentoProvider tipoDocumentoProvider;

    @Inject
    private PersonaNaturalProvider personaNaturalProvider;

    @Inject
    private PersonaJuridicaProvider personaJuridicaProvider;

    @Inject
    private AccionistaProvider accionistaProvider;

    @Test
    public void create() throws StorageException {
        TipoDocumentoModel tipoDocumentoModel1 = tipoDocumentoProvider.create("DNI",
                "Documento nacional de identidad", 8, TipoPersona.NATURAL);
        TipoDocumentoModel tipoDocumentoModel2 = tipoDocumentoProvider.create("RUC",
                "Registro unico de contribuyente", 11, TipoPersona.JURIDICA);

        PersonaNaturalModel representanteLegalModel = personaNaturalProvider.create("PER",
                tipoDocumentoModel1, "12345678", "Flores", "Huertas", "Jhon wilber",
                Calendar.getInstance().getTime(), Sexo.MASCULINO);

        PersonaJuridicaModel personaJuridicaModel = personaJuridicaProvider.create(representanteLegalModel,
                "PER", tipoDocumentoModel2, "10467793549", "Softgreen S.A.C.",
                Calendar.getInstance().getTime(), TipoEmpresa.PRIVADA, true);

        AccionistaModel model = accionistaProvider.create(personaJuridicaModel, representanteLegalModel,
                BigDecimal.TEN);

        assertThat(model, is(notNullValue()));
        assertThat(model.getId(), is(notNullValue()));
        assertThat(model.getPersonaJuridica(), is(notNullValue()));
        assertThat(model.getPersonaJuridica(), is(equalTo(personaJuridicaModel)));
        assertThat(model.getPersonaNatural(), is(notNullValue()));
        assertThat(model.getPersonaNatural(), is(equalTo(representanteLegalModel)));
    }

    @Test
    public void findById() throws StorageException {
        TipoDocumentoModel tipoDocumentoModel1 = tipoDocumentoProvider.create("DNI",
                "Documento nacional de identidad", 8, TipoPersona.NATURAL);
        TipoDocumentoModel tipoDocumentoModel2 = tipoDocumentoProvider.create("RUC",
                "Registro unico de contribuyente", 11, TipoPersona.JURIDICA);

        PersonaNaturalModel representanteLegalModel = personaNaturalProvider.create("PER",
                tipoDocumentoModel1, "12345678", "Flores", "Huertas", "Jhon wilber",
                Calendar.getInstance().getTime(), Sexo.MASCULINO);

        PersonaJuridicaModel personaJuridicaModel = personaJuridicaProvider.create(representanteLegalModel,
                "PER", tipoDocumentoModel2, "10467793549", "Softgreen S.A.C.",
                Calendar.getInstance().getTime(), TipoEmpresa.PRIVADA, true);

        AccionistaModel model1 = accionistaProvider.create(personaJuridicaModel, representanteLegalModel,
                BigDecimal.TEN);

        String id = model1.getId();
        AccionistaModel model2 = accionistaProvider.findById(id);

        assertThat(model1, is(equalTo(model2)));
    }

    @Test
    public void remove() throws StorageException {
        TipoDocumentoModel tipoDocumentoModel1 = tipoDocumentoProvider.create("DNI",
                "Documento nacional de identidad", 8, TipoPersona.NATURAL);
        TipoDocumentoModel tipoDocumentoModel2 = tipoDocumentoProvider.create("RUC",
                "Registro unico de contribuyente", 11, TipoPersona.JURIDICA);

        PersonaNaturalModel representanteLegalModel = personaNaturalProvider.create("PER",
                tipoDocumentoModel1, "12345678", "Flores", "Huertas", "Jhon wilber",
                Calendar.getInstance().getTime(), Sexo.MASCULINO);

        PersonaJuridicaModel personaJuridicaModel = personaJuridicaProvider.create(representanteLegalModel,
                "PER", tipoDocumentoModel2, "10467793549", "Softgreen S.A.C.",
                Calendar.getInstance().getTime(), TipoEmpresa.PRIVADA, true);

        AccionistaModel model1 = accionistaProvider.create(personaJuridicaModel, representanteLegalModel,
                BigDecimal.TEN);

        String id = model1.getId();
        boolean result = accionistaProvider.remove(model1);

        AccionistaModel model2 = accionistaProvider.findById(id);

        assertThat(result, is(true));
        assertThat(model2, is(nullValue()));
    }

}
