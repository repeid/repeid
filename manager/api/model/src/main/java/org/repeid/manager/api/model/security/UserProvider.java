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
package org.repeid.manager.api.model.security;

import java.util.List;
import java.util.Set;

import org.repeid.manager.api.beans.exceptions.StorageException;
import org.repeid.manager.api.beans.representations.security.PermissionType;
import org.repeid.manager.api.model.provider.Provider;
import org.repeid.manager.api.model.search.SearchCriteriaModel;
import org.repeid.manager.api.model.search.SearchResultsModel;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */
public interface UserProvider extends Provider {

    UserModel create(String username, String fullName, String email) throws StorageException;

    UserModel findById(String id) throws StorageException;

    UserModel findByUsername(String username) throws StorageException;

    boolean remove(UserModel user);

    /**
     * Returns a set of permissions granted to the user due to their role
     * memberships.
     * 
     * @param userId
     *            the user's id
     * @return set of permissions
     * @throws StorageException
     *             if an exception occurs during storage attempt
     */
    Set<PermissionType> getPermissions(String userId) throws StorageException;

    List<UserModel> getAll();

    List<UserModel> getAll(int firstResult, int maxResults);

    SearchResultsModel<UserModel> search(SearchCriteriaModel criteria) throws StorageException;

    SearchResultsModel<UserModel> search(SearchCriteriaModel criteria, String filterText)
            throws StorageException;

}
