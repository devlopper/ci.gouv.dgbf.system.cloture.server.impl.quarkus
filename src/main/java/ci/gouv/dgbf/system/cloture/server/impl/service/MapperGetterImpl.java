package ci.gouv.dgbf.system.cloture.server.impl.service;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.cyk.quarkus.extension.service.core_.AbstractMapperGetterImpl;

import io.quarkus.arc.Unremovable;

@ApplicationScoped @Unremovable
public class MapperGetterImpl extends AbstractMapperGetterImpl implements Serializable {

	@Inject OperationDtoImplMapper operationDtoImplMapper;
	@Inject OperationGroupDtoImplMapper operationGroupDtoImplMapper;
	@Inject ActDtoImplMapper actDtoImplMapper;
}