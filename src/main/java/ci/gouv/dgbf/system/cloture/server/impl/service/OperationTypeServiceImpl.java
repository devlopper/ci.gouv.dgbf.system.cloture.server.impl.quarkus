package ci.gouv.dgbf.system.cloture.server.impl.service;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.utility.service.server.AbstractSpecificServiceImpl;

import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationType;
import ci.gouv.dgbf.system.cloture.server.api.service.OperationTypeDto;
import ci.gouv.dgbf.system.cloture.server.api.service.OperationTypeService;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.OperationTypeImpl;

public class OperationTypeServiceImpl extends AbstractSpecificServiceImpl<OperationTypeDto,OperationTypeDtoImpl,OperationType,OperationTypeImpl> implements OperationTypeService,Serializable {

	@Inject OperationTypeDtoImplMapper mapper;
	
	public OperationTypeServiceImpl() {
		this.serviceEntityClass = OperationTypeDto.class;
		this.serviceEntityImplClass = OperationTypeDtoImpl.class;
		this.persistenceEntityClass = OperationType.class;
		this.persistenceEntityImplClass = OperationTypeImpl.class;
	}
}