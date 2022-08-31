package ci.gouv.dgbf.system.cloture.server.impl;

import io.quarkus.runtime.annotations.StaticInitSafe;
import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithConverter;
import io.smallrye.config.WithDefault;
import io.smallrye.config.WithName;

@StaticInitSafe
@ConfigMapping(prefix = "cloture")
public interface Configuration extends org.cyk.quarkus.extension.core_.configuration.Configuration {
	
	@WithName("server.client.rest.uri")
	@WithConverter(StringConverter.class)
	String serverClientRestUri();
	
	Operation operation();
	
	/**/
	
	public static interface Operation {
		Execution execution();
		
		public static interface Execution {
			@WithDefault("true")
			Boolean sequential();
		}
		
		Type type();
		
		public static interface Type {
			String CODE_DEVERROUILLAGE = "DEVERROUILLAGE";
			String CODE_VERROUILLAGE = "VERROUILLAGE";
			
			@WithConverter(StringConverter.class)
			@WithDefault(CODE_VERROUILLAGE)
			String lockingCode();
			
			@WithConverter(StringConverter.class)
			@WithDefault(CODE_DEVERROUILLAGE)
			String unlockingCode();
			
			@WithConverter(StringConverter.class)
			@WithDefault(CODE_DEVERROUILLAGE)
			String defaultCode();
		}
		
		Status status();
		
		public static interface Status {
			String CODE_CREATED = "CREEE";
			String CODE_STARTED = "DEMARREE";
			String CODE_EXECUTED = "EXECUTEE";
			
			//Structure created();
			
			@WithConverter(StringConverter.class)
			@WithDefault(CODE_CREATED)
			String createdCode();
			
			@WithConverter(StringConverter.class)
			@WithDefault(CODE_STARTED)
			String startedCode();
			
			@WithConverter(StringConverter.class)
			@WithDefault(CODE_EXECUTED)
			String executedCode();
			
			@WithConverter(StringConverter.class)
			@WithDefault(CODE_CREATED)
			String defaultCode();
			
			/*public static interface Structure {
				@WithConverter(StringConverter.class)
				String code();
				
				@WithConverter(StringConverter.class)
				Integer limit();
			}*/
		}
	}
}