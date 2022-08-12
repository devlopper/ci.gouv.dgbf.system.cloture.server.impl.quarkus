package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Script;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ScriptPersistence;

@ApplicationScoped
public class ScriptPersistenceImpl extends org.cyk.quarkus.extension.hibernate.orm.AbstractSpecificPersistenceImpl<Script>  implements ScriptPersistence,Serializable {

	public ScriptPersistenceImpl() {
		entityClass = Script.class;
		entityImplClass = ScriptImpl.class;
	}

	@Override
	public void executeProcedure(String name) {
		
	}
}