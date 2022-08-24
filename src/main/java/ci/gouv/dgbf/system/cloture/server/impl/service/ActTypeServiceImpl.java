package ci.gouv.dgbf.system.cloture.server.impl.service;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.utility.service.server.AbstractSpecificServiceImpl;

import ci.gouv.dgbf.system.cloture.server.api.persistence.ActType;
import ci.gouv.dgbf.system.cloture.server.api.service.ActTypeDto;
import ci.gouv.dgbf.system.cloture.server.api.service.ActTypeService;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.ActTypeImpl;

public class ActTypeServiceImpl extends AbstractSpecificServiceImpl<ActTypeDto,ActTypeDtoImpl,ActType,ActTypeImpl> implements ActTypeService,Serializable {

	@Inject ActTypeDtoImplMapper mapper;
	
	public ActTypeServiceImpl() {
		this.serviceEntityClass = ActTypeDto.class;
		this.serviceEntityImplClass = ActTypeDtoImpl.class;
		this.persistenceEntityClass = ActType.class;
		this.persistenceEntityImplClass = ActTypeImpl.class;
	}
}