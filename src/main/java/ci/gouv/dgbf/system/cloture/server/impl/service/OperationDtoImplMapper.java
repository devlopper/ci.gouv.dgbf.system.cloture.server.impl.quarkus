package ci.gouv.dgbf.system.cloture.server.impl.service;

import org.cyk.utility.mapping.Mapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;

import ci.gouv.dgbf.system.cloture.server.impl.persistence.OperationImpl;

@org.mapstruct.Mapper
public interface OperationDtoImplMapper extends Mapper<OperationImpl, OperationDtoImpl> {

	@AfterMapping
	default void listenAfterMap(OperationImpl source, @MappingTarget OperationDtoImpl target) {
		target.set__audit__(source.get__audit__());
	}
}