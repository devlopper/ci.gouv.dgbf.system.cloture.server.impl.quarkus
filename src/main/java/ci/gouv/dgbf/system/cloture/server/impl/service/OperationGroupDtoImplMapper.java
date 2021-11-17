package ci.gouv.dgbf.system.cloture.server.impl.service;

import org.cyk.utility.mapping.Mapper;

import ci.gouv.dgbf.system.cloture.server.impl.persistence.OperationGroupImpl;

@org.mapstruct.Mapper
public interface OperationGroupDtoImplMapper extends Mapper<OperationGroupImpl, OperationGroupDtoImpl> {

}