package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;

import org.cyk.utility.persistence.EntityManagerGetter;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Act;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActPersistence;

@ApplicationScoped
public class ActPersistenceImpl extends org.cyk.quarkus.extension.hibernate.orm.AbstractSpecificPersistenceImpl<Act>  implements ActPersistence,Serializable {

	public ActPersistenceImpl() {
		entityClass = Act.class;
		entityImplClass = ActImpl.class;
	}
	
	@Override
	public void lock(String identifier,String lockType,String targetTable, EntityManager entityManager) {
		
	}
	
	@Override
	public void lock(String identifier, String lockType, String targetTable) {
		lock(identifier, lockType, targetTable, EntityManagerGetter.getInstance().get());
	}
	
	@Override
	public void unlock(String identifier, String lockType, String targetTable, EntityManager entityManager) {
		
	}
	
	@Override
	public void unlock(String identifier,String lockType,String targetTable) {
		unlock(identifier, lockType, targetTable, EntityManagerGetter.getInstance().get());
	}
}