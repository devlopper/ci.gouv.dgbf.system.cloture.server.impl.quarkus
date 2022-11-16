package ci.gouv.dgbf.system.cloture.server.impl.service;

import org.cyk.utility.mapping.Mapper;

import ci.gouv.dgbf.system.cloture.server.impl.persistence.ImputationImpl;

@org.mapstruct.Mapper
public interface ImputationDtoImplMapper extends Mapper<ImputationImpl, ImputationDtoImpl> {

}