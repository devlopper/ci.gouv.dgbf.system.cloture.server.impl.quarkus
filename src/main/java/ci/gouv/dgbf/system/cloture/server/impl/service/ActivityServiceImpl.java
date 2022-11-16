package ci.gouv.dgbf.system.cloture.server.impl.service;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.utility.service.server.AbstractSpecificServiceImpl;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Activity;
import ci.gouv.dgbf.system.cloture.server.api.service.ActivityDto;
import ci.gouv.dgbf.system.cloture.server.api.service.ActivityService;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.ActivityImpl;

public class ActivityServiceImpl extends AbstractSpecificServiceImpl<ActivityDto,ActivityDtoImpl,Activity,ActivityImpl> implements ActivityService,Serializable {

	@Inject ActivityDtoImplMapper mapper;
	
	public ActivityServiceImpl() {
		this.serviceEntityClass = ActivityDto.class;
		this.serviceEntityImplClass = ActivityDtoImpl.class;
		this.persistenceEntityClass = Activity.class;
		this.persistenceEntityImplClass = ActivityImpl.class;
	}
}