package ci.gouv.dgbf.system.cloture.server.impl.service;

import org.cyk.utility.mapping.Mapper;

import ci.gouv.dgbf.system.cloture.server.impl.persistence.ActivityImpl;

@org.mapstruct.Mapper
public interface ActivityDtoImplMapper extends Mapper<ActivityImpl, ActivityDtoImpl> {

}