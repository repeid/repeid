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
package org.repeid.manager.api.rest.bussiness.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.repeid.manager.api.model.enums.EstadoCivil;
import org.repeid.manager.api.model.enums.Sexo;
import org.repeid.manager.api.model.enums.TipoEmpresa;
import org.repeid.manager.api.model.enums.TipoPersona;
import org.repeid.manager.api.rest.bussiness.MaestroResource;

@Stateless
public class MaestroResourceImpl implements MaestroResource {

    @Override
    public List<String> getAllTipoPersonas() {
        TipoPersona[] enums = TipoPersona.values();

        List<String> representations = new ArrayList<>();
        for (int i = 0; i < enums.length; i++) {
            representations.add(enums[i].toString());
        }
        return representations;
    }

    @Override
    public List<String> getAllEstadosCiviles() {
        EstadoCivil[] enums = EstadoCivil.values();

        List<String> representations = new ArrayList<>();
        for (int i = 0; i < enums.length; i++) {
            representations.add(enums[i].toString());
        }
        return representations;
    }

    @Override
    public List<String> getAllSexos() {
        Sexo[] enums = Sexo.values();

        List<String> representations = new ArrayList<>();
        for (int i = 0; i < enums.length; i++) {
            representations.add(enums[i].toString());
        }
        return representations;
    }

    @Override
    public List<String> getAllTiposEmpresa() {
        TipoEmpresa[] enums = TipoEmpresa.values();

        List<String> representations = new ArrayList<>();
        for (int i = 0; i < enums.length; i++) {
            representations.add(enums[i].toString());
        }
        return representations;
    }

}
