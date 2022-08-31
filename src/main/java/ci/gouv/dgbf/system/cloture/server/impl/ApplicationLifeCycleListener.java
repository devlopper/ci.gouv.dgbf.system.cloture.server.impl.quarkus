package ci.gouv.dgbf.system.cloture.server.impl;

import javax.enterprise.event.Observes;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.mapping.MapperClassGetter;
import org.cyk.utility.__kernel__.variable.VariableHelper;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.cyk.utility.business.Validator;
import org.cyk.utility.persistence.query.EntityCounter;
import org.cyk.utility.persistence.query.EntityReader;
import org.cyk.utility.persistence.server.TransientFieldsProcessor;
import org.cyk.utility.persistence.server.query.string.RuntimeQueryStringBuilder;
import org.cyk.utility.service.server.PersistenceEntityClassGetterImpl;

import ci.gouv.dgbf.system.cloture.server.impl.persistence.ActImpl;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.ActTypeImpl;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.OperationImpl;
import ci.gouv.dgbf.system.cloture.server.impl.persistence.OperationTypeImpl;
import ci.gouv.dgbf.system.cloture.server.impl.service.ActDtoImpl;
import ci.gouv.dgbf.system.cloture.server.impl.service.ActDtoImplMapper;
import ci.gouv.dgbf.system.cloture.server.impl.service.ActTypeDtoImpl;
import ci.gouv.dgbf.system.cloture.server.impl.service.ActTypeDtoImplMapper;
import ci.gouv.dgbf.system.cloture.server.impl.service.OperationDtoImpl;
import ci.gouv.dgbf.system.cloture.server.impl.service.OperationDtoImplMapper;
import ci.gouv.dgbf.system.cloture.server.impl.service.OperationTypeDtoImpl;
import ci.gouv.dgbf.system.cloture.server.impl.service.OperationTypeDtoImplMapper;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.Startup;
import io.quarkus.runtime.StartupEvent;

@Startup(value = ApplicationLifeCycleListener.ORDER)
@javax.enterprise.context.ApplicationScoped
public class ApplicationLifeCycleListener {
	public static final int ORDER = org.cyk.quarkus.extension.hibernate.orm.ApplicationLifeCycleListener.ORDER+1;

    void onStart(@Observes StartupEvent startupEvent) {
    	org.cyk.quarkus.extension.hibernate.orm.ApplicationLifeCycleListener.QUALIFIER = ci.gouv.dgbf.system.cloture.server.api.System.class;
    	DependencyInjection.setQualifierClassTo(ci.gouv.dgbf.system.cloture.server.api.System.class
    			, EntityReader.class,EntityCounter.class, RuntimeQueryStringBuilder.class,TransientFieldsProcessor.class/*, Initializer.class*/,Validator.class
    			);
    	VariableHelper.write(VariableName.SYSTEM_LOGGING_THROWABLE_PRINT_STACK_TRACE, Boolean.TRUE);
    	
    	MapperClassGetter.MAP.put(ActTypeDtoImpl.class, ActTypeDtoImplMapper.class);
    	PersistenceEntityClassGetterImpl.MAP.put(ActTypeDtoImpl.class,ActTypeImpl.class);
    	ActTypeDtoImpl.setProjections();
    	
    	MapperClassGetter.MAP.put(ActDtoImpl.class, ActDtoImplMapper.class);
    	PersistenceEntityClassGetterImpl.MAP.put(ActDtoImpl.class,ActImpl.class);
    	ActDtoImpl.setProjections();
    	
    	MapperClassGetter.MAP.put(OperationTypeDtoImpl.class, OperationTypeDtoImplMapper.class);
    	PersistenceEntityClassGetterImpl.MAP.put(OperationTypeDtoImpl.class,OperationTypeImpl.class);
    	OperationTypeDtoImpl.setProjections();
    	
    	MapperClassGetter.MAP.put(OperationDtoImpl.class, OperationDtoImplMapper.class);
    	PersistenceEntityClassGetterImpl.MAP.put(OperationDtoImpl.class,OperationImpl.class);
    	OperationDtoImpl.setProjections();
    	
    	/**/
    	
    }

    void onStop(@Observes ShutdownEvent shutdownEvent) {               
        
    }
}