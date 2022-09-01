package ci.gouv.dgbf.system.cloture.server.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.service.client.SpecificServiceGetter;

import ci.gouv.dgbf.system.cloture.server.api.persistence.ActPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Operation;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationGroupPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Parameters;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.ActImpl;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.OperationImpl;

@ApplicationScoped
public class Assertor {

	@Inject SpecificServiceGetter specificServiceGetter;
	@Inject OperationGroupPersistence operationGroupPersistence;
	@Inject ActPersistence actPersistence;
	@Inject OperationPersistence operationPersistence;
	
	public void assertIdentifiers(Collection<?> objects,Collection<String> expectedIdentifiers) {
		if(CollectionHelper.isEmpty(objects)) {
			assertThat(expectedIdentifiers).as("Aucun identifiants trouvés").isNull();
		}else {
			assertThat(expectedIdentifiers).as("Identifiants trouvés").isNotNull();
			assertThat(objects.stream().map(x -> FieldHelper.readSystemIdentifier(x)).collect(Collectors.toList())).containsExactly(expectedIdentifiers.toArray(new String[] {}));
		}
	}
	
	public void assertOperationActs(String identifier,Collection<String> expectedActsIdentifiers) {
		Collection<String> identifiers = FieldHelper.readSystemIdentifiersAsStrings(actPersistence.readMany(new QueryExecutorArguments().addProjectionsFromStrings(ActImpl.FIELD_IDENTIFIER).addFilterField(Parameters.OPERATION_IDENTIFIER, identifier)));
		if(CollectionHelper.isEmpty(expectedActsIdentifiers))
			assertThat(identifiers).isNull();
		else
			assertThat(identifiers).containsExactly(expectedActsIdentifiers.toArray(new String[] {}));
	}
	
	public void assertOperationActs(String identifier,String...expectedActsIdentifiers) {
		assertOperationActs(identifier, CollectionHelper.listOf(Boolean.TRUE,expectedActsIdentifiers));
	}
	
	public void assertOperationStatusCode(String identifier,String expectedStatusCode) {
		Operation operation = operationPersistence.readOne(identifier, List.of(OperationImpl.FIELD_IDENTIFIER,OperationImpl.FIELD_STATUS));
		if(operation == null)
			assertThat(expectedStatusCode).isNull();
		else
			assertThat(operation.getStatus().getCode()).isEqualTo(expectedStatusCode);
	}
}