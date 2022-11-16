package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Activity;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActivityPersistence;

@ApplicationScoped
public class ActivityPersistenceImpl extends org.cyk.quarkus.extension.hibernate.orm.AbstractSpecificPersistenceImpl<Activity>  implements ActivityPersistence,Serializable {

	public ActivityPersistenceImpl() {
		entityClass = Activity.class;
		entityImplClass = ActivityImpl.class;
	}
	
}