package ci.gouv.dgbf.system.cloture.server.impl.business;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.cyk.utility.business.server.AbstractSpecificBusinessImpl;

import ci.gouv.dgbf.system.cloture.server.api.business.OperationGroupBusiness;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationGroup;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationGroupPersistence;

@ApplicationScoped
public class OperationGroupBusinessImpl extends AbstractSpecificBusinessImpl<OperationGroup> implements OperationGroupBusiness,Serializable {

	@Inject OperationGroupPersistence operationGroupPersistence;
	
}