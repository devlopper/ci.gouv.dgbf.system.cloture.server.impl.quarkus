package ci.gouv.dgbf.system.cloture.server.impl.service;

import org.cyk.utility.mapping.Mapper;

import ci.gouv.dgbf.system.cloture.server.impl.persistence.ScriptImpl;

@org.mapstruct.Mapper
public interface OperationDtoImplMapper extends Mapper<ScriptImpl, OperationDtoImpl> {

}