package ci.gouv.dgbf.system.cloture.server.impl.service;

import org.cyk.utility.mapping.Mapper;

import ci.gouv.dgbf.system.cloture.server.impl.persistence.ExerciseImpl;

@org.mapstruct.Mapper
public interface ExerciseDtoImplMapper extends Mapper<ExerciseImpl, ExerciseDtoImpl> {

}