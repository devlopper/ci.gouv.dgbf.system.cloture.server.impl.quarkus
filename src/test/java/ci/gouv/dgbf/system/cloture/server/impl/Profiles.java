package ci.gouv.dgbf.system.cloture.server.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.cyk.quarkus.extension.test.Profile;

import io.quarkus.test.junit.QuarkusTestProfile;

public interface Profiles {
	
	public class Exercise implements QuarkusTestProfile{
		@Override
		public Map<String, String> getConfigOverrides() {
			Map<String, String> map = Profile.buildConfig(Exercise.class);
			return map;
		}
		
		@Override
		public Set<String> tags() {
			return Profile.buildTags(Exercise.class);
		}
	}
	
	public class Act implements QuarkusTestProfile{
		@Override
		public Map<String, String> getConfigOverrides() {
			Map<String, String> map = Profile.buildConfig(Act.class);
			return map;
		}
		
		@Override
		public Set<String> tags() {
			return Profile.buildTags(Act.class);
		}
	}
	
	public class Activity implements QuarkusTestProfile{
		@Override
		public Map<String, String> getConfigOverrides() {
			Map<String, String> map = Profile.buildConfig(Activity.class);
			return map;
		}
		
		@Override
		public Set<String> tags() {
			return Profile.buildTags(Activity.class);
		}
	}
	
	public class Imputation implements QuarkusTestProfile{
		@Override
		public Map<String, String> getConfigOverrides() {
			Map<String, String> map = Profile.buildConfig(Imputation.class);
			return map;
		}
		
		@Override
		public Set<String> tags() {
			return Profile.buildTags(Imputation.class);
		}
	}
	
	public class Operation implements QuarkusTestProfile{
		@Override
		public Map<String, String> getConfigOverrides() {
			Map<String, String> map = Profile.buildConfig(Operation.class);
			map.put("cloture.operation.execution.sequential", "false");
			return map;
		}
		
		@Override
		public Set<String> tags() {
			return Profile.buildTags(Operation.class);
		}
	}
	
	public interface Persistence {
		public class Default implements QuarkusTestProfile {
			@Override
			public Map<String, String> getConfigOverrides() {
				Map<String, String> map = new HashMap<>();
				map.put("quarkus.hibernate-orm.sql-load-script", "persistence.sql");
				return map;
			}
			
			@Override
			public Set<String> tags() {
				return Set.of(Persistence.class.getSimpleName().toLowerCase()+"."+Default.class.getSimpleName().toLowerCase());
			}
		}
		
		public class Act implements QuarkusTestProfile {
			@Override
			public Map<String, String> getConfigOverrides() {
				Map<String, String> map = new HashMap<>();
				map.put("quarkus.hibernate-orm.sql-load-script", "persistence-act.sql");
				return map;
			}
			
			@Override
			public Set<String> tags() {
				return Set.of(Persistence.class.getSimpleName().toLowerCase()+"."+Act.class.getSimpleName().toLowerCase());
			}
		}
		
		public class Filter implements QuarkusTestProfile {
			@Override
			public Map<String, String> getConfigOverrides() {
				Map<String, String> map = new HashMap<>();
				map.put("quarkus.hibernate-orm.sql-load-script", "persistence-filter.sql");
				return map;
			}
			
			@Override
			public Set<String> tags() {
				return Set.of(Persistence.class.getSimpleName().toLowerCase()+"."+Filter.class.getSimpleName().toLowerCase());
			}
		}
	}
	
	public interface Business {
		public class Default implements QuarkusTestProfile {
			@Override
			public Map<String, String> getConfigOverrides() {
				Map<String, String> map = new HashMap<>();
				map.put("quarkus.hibernate-orm.sql-load-script", "business.sql");
				return map;
			}
			
			@Override
			public Set<String> tags() {
				return Collections.singleton(Business.class.getSimpleName().toLowerCase());
			}
		}
		
		public class Act implements QuarkusTestProfile {
			@Override
			public Map<String, String> getConfigOverrides() {
				Map<String, String> map = new HashMap<>();
				map.put("quarkus.hibernate-orm.sql-load-script", "business-act.sql");
				return map;
			}
			
			@Override
			public Set<String> tags() {
				return Set.of(Business.class.getSimpleName().toLowerCase()+"."+Act.class.getSimpleName().toLowerCase());
			}
		}
	}
	
	public interface Service {
		public class Unit implements QuarkusTestProfile {
			@Override
			public Set<String> tags() {
				return Set.of(Service.class.getSimpleName().toLowerCase()+"."+Unit.class.getSimpleName().toLowerCase());
			}
		}
		
		public class Integration implements QuarkusTestProfile {
			@Override
			public Map<String, String> getConfigOverrides() {
				Map<String, String> map = new HashMap<>();
				map.put("quarkus.hibernate-orm.sql-load-script", "service.sql");
				return map;
			}
			
			@Override
			public Set<String> tags() {
				return Set.of(Service.class.getSimpleName().toLowerCase()+"."+Integration.class.getSimpleName().toLowerCase());
			}
		}
	}
	
	public class Client implements QuarkusTestProfile {
		@Override
		public Map<String, String> getConfigOverrides() {
			Map<String, String> map = new HashMap<>();
			map.put("quarkus.hibernate-orm.sql-load-script", "client.sql");
			return map;
		}
		
		@Override
		public Set<String> tags() {
			return Collections.singleton(Client.class.getSimpleName().toLowerCase());
		}
	}
	
	/**/
	
	public class Unit implements QuarkusTestProfile {
		@Override
		public Set<String> tags() {
			return Collections.singleton(Unit.class.getSimpleName().toLowerCase());
		}
	}
	
	public class Integration implements QuarkusTestProfile {
		@Override
		public Set<String> tags() {
			return Collections.singleton(Integration.class.getSimpleName().toLowerCase());
		}
	}
}