package org.repeid.manager.api.model.system;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.repeid.manager.api.core.config.RepeidApplication;
import org.repeid.manager.api.model.PersonaJuridicaProvider;
import org.repeid.manager.api.model.PersonaNaturalProvider;
import org.repeid.manager.api.model.TipoDocumentoProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */

@ApplicationScoped
public class DefaultRepeidSession implements RepeidSession {

	private static final Logger log = LoggerFactory.getLogger(RepeidApplication.class);

	@Inject
	private RepeidTransactionManager transactionManager;

	@Inject
	private TipoDocumentoProvider tipoDocumentoProvider;

	@Inject
	private PersonaNaturalProvider personaNaturalProvider;

	@Inject
	private PersonaJuridicaProvider personaJuridicaProvider;

	@PostConstruct
	public void init() {
		log.info("RepeidSession started");
	}

	@PreDestroy
	public void close() {
		log.info("Stopping RepeidSession");
	}

	@Override
	public TipoDocumentoProvider tipoDocumentos() {
		return tipoDocumentoProvider;
	}

	@Override
	public RepeidTransactionManager getTransaction() {
		return transactionManager;
	}

	@Override
	public PersonaNaturalProvider personasNaturales() {
		return personaNaturalProvider;
	}

	@Override
	public PersonaJuridicaProvider personasJuridicas() {
		return personaJuridicaProvider;
	}

}
