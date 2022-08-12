package ci.gouv.dgbf.system.cloture.server.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.service.client.SpecificServiceGetter;

import ci.gouv.dgbf.system.cloture.server.api.persistence.OperationGroupPersistence;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ScriptPersistence;

@ApplicationScoped
public class Assertor {

	@Inject SpecificServiceGetter specificServiceGetter;
	@Inject OperationGroupPersistence operationGroupPersistence;
	@Inject ScriptPersistence operationPersistence;
	
	public void assertIdentifiers(Collection<?> objects,Collection<String> expectedIdentifiers) {
		if(CollectionHelper.isEmpty(objects)) {
			assertThat(expectedIdentifiers).as("Aucun identifiants trouvés").isNull();
		}else {
			assertThat(expectedIdentifiers).as("Identifiants trouvés").isNotNull();
			assertThat(objects.stream().map(x -> FieldHelper.readSystemIdentifier(x)).collect(Collectors.toList())).containsExactly(expectedIdentifiers.toArray(new String[] {}));
		}
	}
}