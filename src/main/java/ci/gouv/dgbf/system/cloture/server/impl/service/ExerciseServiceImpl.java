package ci.gouv.dgbf.system.cloture.server.impl.service;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.utility.service.server.AbstractSpecificServiceImpl;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Exercise;
import ci.gouv.dgbf.system.cloture.server.api.service.ExerciseDto;
import ci.gouv.dgbf.system.cloture.server.api.service.ExerciseService;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.ExerciseImpl;

public class ExerciseServiceImpl extends AbstractSpecificServiceImpl<ExerciseDto,ExerciseDtoImpl,Exercise,ExerciseImpl> implements ExerciseService,Serializable {

	@Inject ExerciseDtoImplMapper mapper;
	
	public ExerciseServiceImpl() {
		this.serviceEntityClass = ExerciseDto.class;
		this.serviceEntityImplClass = ExerciseDtoImpl.class;
		this.persistenceEntityClass = Exercise.class;
		this.persistenceEntityImplClass = ExerciseImpl.class;
	}
}