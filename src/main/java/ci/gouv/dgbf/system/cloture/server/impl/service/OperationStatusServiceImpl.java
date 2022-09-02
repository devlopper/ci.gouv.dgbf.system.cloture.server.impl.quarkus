package ci.gouv.dgbf.system.cloture.server.impl.service;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.utility.service.server.AbstractSpecificServiceImpl;

import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationStatus;
import ci.gouv.dgbf.system.cloture.server.api.service.OperationStatusDto;
import ci.gouv.dgbf.system.cloture.server.api.service.OperationStatusService;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.OperationStatusImpl;

public class OperationStatusServiceImpl extends AbstractSpecificServiceImpl<OperationStatusDto,OperationStatusDtoImpl,OperationStatus,OperationStatusImpl> implements OperationStatusService,Serializable {

	@Inject OperationStatusDtoImplMapper mapper;
	
	public OperationStatusServiceImpl() {
		this.serviceEntityClass = OperationStatusDto.class;
		this.serviceEntityImplClass = OperationStatusDtoImpl.class;
		this.persistenceEntityClass = OperationStatus.class;
		this.persistenceEntityImplClass = OperationStatusImpl.class;
	}
}