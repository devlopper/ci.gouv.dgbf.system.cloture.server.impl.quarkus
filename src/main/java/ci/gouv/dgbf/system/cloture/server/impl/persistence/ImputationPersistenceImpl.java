package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Imputation;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ImputationPersistence;

@ApplicationScoped
public class ImputationPersistenceImpl extends org.cyk.quarkus.extension.hibernate.orm.AbstractSpecificPersistenceImpl<Imputation>  implements ImputationPersistence,Serializable {

	public ImputationPersistenceImpl() {
		entityClass = Imputation.class;
		entityImplClass = ImputationImpl.class;
	}
}