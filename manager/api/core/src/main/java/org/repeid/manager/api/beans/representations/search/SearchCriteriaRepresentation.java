/*******************************************************************************
 * Repeid, Home of Professional Open Source
 * <p>
 * Copyright 2015 Sistcoop, Inc. and/or its affiliates.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.repeid.manager.api.beans.representations.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Generic search criteria used when searching for beans.
 *
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */
public class SearchCriteriaRepresentation implements Serializable {

    private static final long serialVersionUID = 5103776179000907112L;

    private String filterText;
    private List<SearchCriteriaFilterRepresentation> filters = new ArrayList<>();
    private List<OrderByRepresentation> orders = new ArrayList<>();
    private PagingRepresentation paging;

    public SearchCriteriaRepresentation() {
        // TODO Auto-generated constructor stub
    }

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }

    public List<SearchCriteriaFilterRepresentation> getFilters() {
        return filters;
    }

    public void setFilters(List<SearchCriteriaFilterRepresentation> filters) {
        this.filters = filters;
    }

    public List<OrderByRepresentation> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderByRepresentation> orders) {
        this.orders = orders;
    }

    public PagingRepresentation getPaging() {
        return paging;
    }

    public void setPaging(PagingRepresentation paging) {
        this.paging = paging;
    }

}
