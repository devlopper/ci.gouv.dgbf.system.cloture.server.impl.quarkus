package ci.gouv.dgbf.system.cloture.server.impl.service;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.utility.service.server.AbstractSpecificServiceImpl;

import ci.gouv.dgbf.system.cloture.server.api.persistence.EconomicNature;
import ci.gouv.dgbf.system.cloture.server.api.service.EconomicNatureDto;
import ci.gouv.dgbf.system.cloture.server.api.service.EconomicNatureService;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.EconomicNatureImpl;

public class EconomicNatureServiceImpl extends AbstractSpecificServiceImpl<EconomicNatureDto,EconomicNatureDtoImpl,EconomicNature,EconomicNatureImpl> implements EconomicNatureService,Serializable {

	@Inject EconomicNatureDtoImplMapper mapper;
	
	public EconomicNatureServiceImpl() {
		this.serviceEntityClass = EconomicNatureDto.class;
		this.serviceEntityImplClass = EconomicNatureDtoImpl.class;
		this.persistenceEntityClass = EconomicNature.class;
		this.persistenceEntityImplClass = EconomicNatureImpl.class;
	}
}