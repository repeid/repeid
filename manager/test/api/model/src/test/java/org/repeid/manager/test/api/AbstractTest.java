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
package org.repeid.manager.test.api;

import java.io.File;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.runner.RunWith;
import org.repeid.common.util.SystemEnvProperties;
import org.repeid.manager.api.beans.exceptions.StorageException;
import org.repeid.manager.api.beans.representations.security.PermissionType;
import org.repeid.manager.api.core.config.RepeidApplication;
import org.repeid.manager.api.jpa.AbstractJpaStorage;
import org.repeid.manager.api.jpa.entities.TipoDocumentoEntity;
import org.repeid.manager.api.jpa.entities.security.UserEntity;
import org.repeid.manager.api.jpa.models.JpaTipoDocumentoProvider;
import org.repeid.manager.api.jpa.models.security.JpaUserProvider;
import org.repeid.manager.api.model.Model;
import org.repeid.manager.api.model.box.DropboxProvider;
import org.repeid.manager.api.model.enums.TipoPersona;
import org.repeid.manager.api.model.exceptions.ModelException;
import org.repeid.manager.api.model.provider.Provider;
import org.repeid.manager.api.model.search.SearchCriteriaModel;
import org.repeid.manager.api.model.security.UserModel;
import org.repeid.manager.api.model.system.RepeidTransaction;
import org.repeid.manager.api.mongo.AbstractMongoStorage;
import org.repeid.manager.api.mongo.entities.MongoTipoDocumentoEntity;
import org.repeid.manager.api.mongo.entities.security.MongoUserEntity;
import org.repeid.manager.api.mongo.models.MongoTipoDocumentoProvider;
import org.repeid.manager.api.mongo.models.security.MongoUserProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Arquillian.class)
@UsingDataSet("empty.xml")
public abstract class AbstractTest {

    protected Logger log = LoggerFactory.getLogger(AbstractTest.class);

    @Deployment
    public static WebArchive createDeployment() {
    	File[] dependencies = new File[9];    
    	dependencies[0] = Maven.resolver().resolve("org.slf4j:slf4j-simple:1.7.10").withoutTransitivity().asSingleFile();
    	dependencies[1] = Maven.resolver().resolve("com.dropbox.core:dropbox-core-sdk:1.8.2").withoutTransitivity().asSingleFile();
    	dependencies[2] = Maven.resolver().resolve("com.google.apis:google-api-services-drive:v2-rev173-1.20.0").withoutTransitivity().asSingleFile();
    	dependencies[3] = Maven.resolver().resolve("com.google.oauth-client:google-oauth-client:1.21.0").withoutTransitivity().asSingleFile();
    	dependencies[4] = Maven.resolver().resolve("com.google.oauth-client:google-oauth-client-java6:1.20.0").withoutTransitivity().asSingleFile();
    	dependencies[5] = Maven.resolver().resolve("com.google.oauth-client:google-oauth-client-jetty:1.20.0").withoutTransitivity().asSingleFile();    	
    	dependencies[6] = Maven.resolver().resolve("com.google.http-client:google-http-client:1.21.0").withoutTransitivity().asSingleFile();
    	dependencies[7] = Maven.resolver().resolve("com.google.http-client:google-http-client-jackson2:1.21.0").withoutTransitivity().asSingleFile();    	    	
    	dependencies[8] = Maven.resolver().resolve("com.google.api-client:google-api-client:1.21.0").withoutTransitivity().asSingleFile();

        WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war")
                
                .addClass(AbstractTest.class)
                .addClass(CdiFactoryTest.class)                             
                
                /**common-utils*/
                .addPackage(SystemEnvProperties.class.getPackage())
                
                /**core*/
                .addPackage(RepeidApplication.class.getPackage())
                
                /**beans*/
                .addPackage(PermissionType.class.getPackage())
                .addPackage(StorageException.class.getPackage())
                
                /** model-api **/
                .addPackage(Model.class.getPackage())                
                .addPackage(Provider.class.getPackage())
                .addPackage(ModelException.class.getPackage())                
                .addPackage(SearchCriteriaModel.class.getPackage())
                
                .addPackage(UserModel.class.getPackage())
                .addPackage(RepeidTransaction.class.getPackage())
                
                .addPackage(TipoPersona.class.getPackage())
                
                .addPackage(DropboxProvider.class.getPackage())

                /** model-jpa **/
                .addPackage(AbstractJpaStorage.class.getPackage())
                
                .addPackage(TipoDocumentoEntity.class.getPackage())
                .addPackage(JpaTipoDocumentoProvider.class.getPackage())
                
                .addPackage(UserEntity.class.getPackage())                
                .addPackage(JpaUserProvider.class.getPackage())
                
                /** model-jpa **/
                .addPackage(AbstractMongoStorage.class.getPackage())
                
                .addPackage(MongoTipoDocumentoEntity.class.getPackage())
                .addPackage(MongoTipoDocumentoProvider.class.getPackage())
                
                .addPackage(MongoUserEntity.class.getPackage())                
                .addPackage(MongoUserProvider.class.getPackage())
                
                /**Config**/
                .addAsResource("META-INF/repeid-server.json", "META-INF/repeid-server.json")
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("test-ds.xml");

		war.addAsLibraries( dependencies );

        return war;
    }    
    
}
