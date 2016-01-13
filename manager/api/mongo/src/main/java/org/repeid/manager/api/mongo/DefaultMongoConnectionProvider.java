package org.repeid.manager.api.mongo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author <a href="mailto:carlosthe19916@gmail.com">Carlos Feria</a>
 */
public class DefaultMongoConnectionProvider implements MongoConnectionProvider {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void close() {
		em.close();
	}

	@Override
	public EntityManager getEntityManager() {
		return em;
	}

}