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
package org.repeid.manager.api.mongo.models;

import java.util.Locale;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.repeid.manager.api.model.StoreConfigurationModel;
import org.repeid.manager.api.model.StoredFileModel;
import org.repeid.manager.api.model.StoredFileProvider;
import org.repeid.manager.api.model.enums.StoreConfigurationType;
import org.repeid.manager.api.model.provider.ProviderFactory;
import org.repeid.manager.api.model.provider.ProviderType;
import org.repeid.manager.api.mongo.AbstractMongoStorage;
import org.repeid.manager.api.mongo.entities.MongoFileEntity;
import org.repeid.manager.api.mongo.entities.MongoStoreConfigurationEntity;
import org.repeid.manager.api.mongo.entities.MongoStoredFileEntity;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxRequestConfig;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@ProviderFactory(ProviderType.MONGO)
public class MongoStoredFileProvider extends AbstractMongoStorage implements StoredFileProvider {

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public StoredFileModel findById(String id) {
		MongoStoredFileEntity storedFileEntity = getEntityManager().find(MongoStoredFileEntity.class, id);
		return storedFileEntity != null ? new StoredFileAdapter(getEntityManager(), storedFileEntity) : null;
	}

	@Override
	public StoredFileModel create(byte[] file, StoreConfigurationModel configuration) {
		StoreConfigurationType provider = configuration.getProviderName();
		switch (provider) {
		case localhost:
			return createLocalFile(file, configuration);
		case Dropbox:
			return createDropboxFile(file, configuration);
		case GoogleDrive:
			return createGoogleDriveFile(file, configuration);
		default:
			return null;
		}
	}

	private StoredFileModel createLocalFile(byte[] file, StoreConfigurationModel configuration) {
		// File storage
		MongoFileEntity fileEntity = new MongoFileEntity();
		fileEntity.setFile(file);
		getEntityManager().persist(fileEntity);

		// Store configuration entity
		MongoStoreConfigurationEntity storeConfigurationEntity = StoreConfigurationAdapter
				.toStoreConfigurationEntity(configuration, getEntityManager());

		// Create StoreFileEntity
		MongoStoredFileEntity storedFileEntity = new MongoStoredFileEntity();
		storedFileEntity.setFileId(UUID.randomUUID().toString());
		storedFileEntity.setUrl(UUID.randomUUID().toString());
		storedFileEntity.setStoreConfiguration(storeConfigurationEntity);

		getEntityManager().persist(storedFileEntity);
		return new StoredFileAdapter(getEntityManager(), storedFileEntity);
	}

	private StoredFileModel createDropboxFile(byte[] file, StoreConfigurationModel configuration) {
		// File storage
		DbxRequestConfig config = new DbxRequestConfig(configuration.getAppKey(), Locale.getDefault().toString());
		DbxClient dbxClient = new DbxClient(config, configuration.getToken());
		DropboxProvider dropboxProvider = new DropboxProvider(dbxClient);
		DbxEntry.File fileEntity = dropboxProvider.upload(file);

		// Store configuration entity
		MongoStoreConfigurationEntity storeConfigurationEntity = StoreConfigurationAdapter
				.toStoreConfigurationEntity(configuration, getEntityManager());

		// Create StoreFileEntity
		MongoStoredFileEntity storedFileEntity = new MongoStoredFileEntity();
		storedFileEntity.setFileId(fileEntity.name);
		storedFileEntity.setUrl(fileEntity.path);
		storedFileEntity.setStoreConfiguration(storeConfigurationEntity);

		getEntityManager().persist(storedFileEntity);
		return new StoredFileAdapter(getEntityManager(), storedFileEntity);
	}

	private StoredFileModel createGoogleDriveFile(byte[] file, StoreConfigurationModel configuration) {
		return null;
	}

	@Override
	public byte[] download(String fileId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(StoredFileModel storedFile) {
		// TODO Auto-generated method stub
		return false;
	}

}
