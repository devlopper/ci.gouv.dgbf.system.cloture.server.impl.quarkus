package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.cyk.quarkus.extension.hibernate.orm.AbstractSpecificPersistenceGetterImpl;

import ci.gouv.dgbf.system.cloture.server.api.persistence.ActLockPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActTypePersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationGroupPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationStatusPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationTypePersistence;
import io.quarkus.arc.Unremovable;

@ApplicationScoped @Unremovable
public class SpecificPersistenceGetterImpl extends AbstractSpecificPersistenceGetterImpl implements Serializable {

	@Inject OperationGroupPersistence operationGroupPersistence;
	@Inject OperationPersistence operationPersistence;
	@Inject OperationTypePersistence operationTypePersistence;
	@Inject OperationStatusPersistence operationStatusPersistence;
	@Inject ActPersistence actPersistence;
	@Inject ActTypePersistence actTypePersistence;
	@Inject ActLockPersistence actLockPersistence;
	
}