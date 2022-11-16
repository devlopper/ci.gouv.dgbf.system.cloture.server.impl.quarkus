package ci.gouv.dgbf.system.cloture.server.impl.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.persistence.query.Filter;
import org.cyk.utility.service.FilterFormat;
import org.cyk.utility.service.server.AbstractSpecificServiceImpl;

import ci.gouv.dgbf.system.cloture.server.api.business.OperationBusiness;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Operation;
import ci.gouv.dgbf.system.cloture.server.api.service.OperationDto;
import ci.gouv.dgbf.system.cloture.server.api.service.OperationService;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.OperationImpl;

public class OperationServiceImpl extends AbstractSpecificServiceImpl<OperationDto,OperationDtoImpl,Operation,OperationImpl> implements OperationService,Serializable {

	@Inject OperationDtoImplMapper mapper;
	@Inject OperationBusiness business;
	
	public OperationServiceImpl() {
		this.serviceEntityClass = OperationDto.class;
		this.serviceEntityImplClass = OperationDtoImpl.class;
		this.persistenceEntityClass = Operation.class;
		this.persistenceEntityImplClass = OperationImpl.class;
	}
	
	@Override
	public Response create(String typeIdentifier, String code, String name, String reason,String auditWho) {
		return buildResponseOk(business.create(typeIdentifier, code, name, reason, auditWho));
	}
	
	@Override
	public Response addAct(String identifier, List<String> actsIdentifiers, Boolean existingIgnorable,String auditWho) {
		return buildResponseOk(business.addAct(identifier, actsIdentifiers, existingIgnorable, auditWho));
	}
	
	@Override
	public Response addActComprehensively(String identifier, List<String> actsIdentifiers, String auditWho) {
		return buildResponseOk(business.addActComprehensively(identifier, actsIdentifiers, auditWho));
	}
	
	@Override
	public Response addActByFilter(String identifier, String filterAsString, FilterFormat filterFormat,Boolean existingIgnorable, String auditWho) {
		Filter filter = ValueHelper.defaultToIfNull(filterFormat, FilterFormat.JSON).equals(FilterFormat.JSON) ? Filter.instantiateFromJson(filterAsString) : new Filter().setValue(filterAsString);
		return buildResponseOk(business.addActByFilter(identifier, filter, existingIgnorable, auditWho));
	}
	
	@Override
	public Response removeAct(String identifier, List<String> actsIdentifiers, Boolean existingIgnorable,String auditWho) {
		return buildResponseOk(business.removeAct(identifier, actsIdentifiers, existingIgnorable, auditWho));
	}
	
	@Override
	public Response removeActComprehensively(String identifier, List<String> actsIdentifiers, String auditWho) {
		return null;//buildResponseOk(business.removeActComprehensively(identifier, actsIdentifiers, auditWho));
	}
	
	@Override
	public Response removeActByFilter(String identifier, String filterAsString, FilterFormat filterFormat,Boolean existingIgnorable, String auditWho) {
		return buildResponseOk(business.removeActByFilter(identifier, ValueHelper.defaultToIfNull(filterFormat, FilterFormat.JSON).equals(FilterFormat.JSON) ? Filter.instantiateFromJson(filterAsString) : new Filter().setValue(filterAsString)
				, existingIgnorable, auditWho));
	}
	
	/* Imputation */
	
	@Override
	public Response addImputation(String identifier, List<String> actsIdentifiers, Boolean existingIgnorable,String auditWho) {
		return buildResponseOk(business.addImputation(identifier, actsIdentifiers, existingIgnorable, auditWho));
	}
	
	@Override
	public Response addImputationComprehensively(String identifier, List<String> actsIdentifiers, String auditWho) {
		return buildResponseOk(business.addImputationComprehensively(identifier, actsIdentifiers, auditWho));
	}
	
	@Override
	public Response addImputationByFilter(String identifier, String filterAsString, FilterFormat filterFormat,Boolean existingIgnorable, String auditWho) {
		Filter filter = ValueHelper.defaultToIfNull(filterFormat, FilterFormat.JSON).equals(FilterFormat.JSON) ? Filter.instantiateFromJson(filterAsString) : new Filter().setValue(filterAsString);
		return buildResponseOk(business.addImputationByFilter(identifier, filter, existingIgnorable, auditWho));
	}
	
	@Override
	public Response removeImputation(String identifier, List<String> actsIdentifiers, Boolean existingIgnorable,String auditWho) {
		return buildResponseOk(business.removeImputation(identifier, actsIdentifiers, existingIgnorable, auditWho));
	}
	
	@Override
	public Response removeImputationComprehensively(String identifier, List<String> actsIdentifiers, String auditWho) {
		return null;//buildResponseOk(business.removeImputationComprehensively(identifier, actsIdentifiers, auditWho));
	}
	
	@Override
	public Response removeImputationByFilter(String identifier, String filterAsString, FilterFormat filterFormat,Boolean existingIgnorable, String auditWho) {
		return buildResponseOk(business.removeImputationByFilter(identifier, ValueHelper.defaultToIfNull(filterFormat, FilterFormat.JSON).equals(FilterFormat.JSON) ? Filter.instantiateFromJson(filterAsString) : new Filter().setValue(filterAsString)
				, existingIgnorable, auditWho));
	}
	
	/* Execution */
	
	@Override
	public Response startExecution(String identifier,String auditWho) {
		return buildResponseOk(business.startExecution(identifier, auditWho));
	}
}