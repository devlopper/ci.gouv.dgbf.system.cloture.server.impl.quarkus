package ci.gouv.dgbf.system.cloture.server.impl.service;

import org.cyk.utility.mapping.Mapper;

import ci.gouv.dgbf.system.cloture.server.impl.persistence.OperationStatusImpl;

@org.mapstruct.Mapper
public interface OperationStatusDtoImplMapper extends Mapper<OperationStatusImpl, OperationStatusDtoImpl> {

}