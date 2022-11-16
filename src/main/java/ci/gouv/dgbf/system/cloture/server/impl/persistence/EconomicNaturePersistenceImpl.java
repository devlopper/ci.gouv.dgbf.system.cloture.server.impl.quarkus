package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import ci.gouv.dgbf.system.cloture.server.api.persistence.EconomicNature;
import ci.gouv.dgbf.system.cloture.server.api.persistence.EconomicNaturePersistence;

@ApplicationScoped
public class EconomicNaturePersistenceImpl extends org.cyk.quarkus.extension.hibernate.orm.AbstractSpecificPersistenceImpl<EconomicNature>  implements EconomicNaturePersistence,Serializable {

	public EconomicNaturePersistenceImpl() {
		entityClass = EconomicNature.class;
		entityImplClass = EconomicNatureImpl.class;
	}
	
}