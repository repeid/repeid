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
package org.repeid.manager.api.model.exceptions;

/**
 * @author <a href="mailto:carlosthe19916@sistcoop.com">Carlos Feria</a>
 */

public class ModelException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private transient Object[] parameters;

	public ModelException() {
	}

	public ModelException(String message) {
		super(message);
	}

	public ModelException(String message, Object... parameters) {
		super(message);
		this.parameters = parameters;
	}

	public ModelException(String message, Throwable cause) {
		super(message, cause);
	}

	public ModelException(Throwable cause) {
		super(cause);
	}

	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}
}
