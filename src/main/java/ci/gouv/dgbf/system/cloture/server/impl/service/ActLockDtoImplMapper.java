package ci.gouv.dgbf.system.cloture.server.impl.service;

import org.cyk.utility.mapping.Mapper;

import ci.gouv.dgbf.system.cloture.server.impl.persistence.ActLockImpl;

@org.mapstruct.Mapper
public interface ActLockDtoImplMapper extends Mapper<ActLockImpl, ActLockDtoImpl> {

}