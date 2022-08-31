package ci.gouv.dgbf.system.cloture.server.impl;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ConfigurationTest {

	@Inject Configuration configuration;
	
	@Test
	void operation_type_defaultCode() {
		assertThat(configuration.operation().type().defaultCode()).isEqualTo(Configuration.Operation.Type.CODE_DEVERROUILLAGE);
	}
	
	@Test
	void operation_status_defaultCode() {
		assertThat(configuration.operation().status().defaultCode()).isEqualTo(Configuration.Operation.Status.CODE_CREATED);
	}
	
	@Test
	void operation_execution_sequential() {
		assertThat(configuration.operation().execution().sequential()).isEqualTo(Boolean.TRUE);
	}
}