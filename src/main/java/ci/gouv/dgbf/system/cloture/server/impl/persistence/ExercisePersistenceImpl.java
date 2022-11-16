package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Exercise;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ExercisePersistence;

@ApplicationScoped
public class ExercisePersistenceImpl extends org.cyk.quarkus.extension.hibernate.orm.AbstractSpecificPersistenceImpl<Exercise>  implements ExercisePersistence,Serializable {

	public ExercisePersistenceImpl() {
		entityClass = Exercise.class;
		entityImplClass = ExerciseImpl.class;
	}
	
}