package ci.gouv.dgbf.system.cloture.server.impl.service;

import java.io.Serializable;

import javax.inject.Inject;
import javax.ws.rs.Path;

import org.cyk.utility.service.server.AbstractSpecificServiceImpl;

import ci.gouv.dgbf.system.cloture.server.api.persistence.Imputation;
import ci.gouv.dgbf.system.cloture.server.api.service.ImputationDto;
import ci.gouv.dgbf.system.cloture.server.api.service.ImputationService;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.ImputationImpl;

@Path(ImputationService.PATH)
public class ImputationServiceImpl extends AbstractSpecificServiceImpl<ImputationDto,ImputationDtoImpl,Imputation,ImputationImpl> implements ImputationService,Serializable {

	@Inject ImputationDtoImplMapper mapper;
	
	public ImputationServiceImpl() {
		this.serviceEntityClass = ImputationDto.class;
		this.serviceEntityImplClass = ImputationDtoImpl.class;
		this.persistenceEntityClass = Imputation.class;
		this.persistenceEntityImplClass = ImputationImpl.class;
	}
}