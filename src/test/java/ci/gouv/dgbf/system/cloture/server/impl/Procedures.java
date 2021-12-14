package ci.gouv.dgbf.system.cloture.server.impl;

import org.cyk.utility.__kernel__.time.TimeHelper;

public class Procedures {
	
	public static String PA_EXECUTER_PROCEDURE(String name) {
		if(UNKNOWN_NAME.equals(name))
			throw new RuntimeException("Cette procédure n'existe pas");
		TimeHelper.pause(1 * 1000l);
		return null;
	}
	
	public static String PA_VERROUILLER_ACTE(String identifier,String lockType,String targetTable) {
		if(IDENTIFIER_ERROR.equals(identifier))
			throw new RuntimeException("Une erreur est survenue");
		return null;
	}
	
	public static String PA_DEVERROUILLER_ACTE(String identifier,String lockType,String targetTable) {
		if(IDENTIFIER_ERROR.equals(identifier))
			throw new RuntimeException("Une erreur est survenue");
		return null;
	}
	
	public static final String UNKNOWN_NAME = "XXX";
	public static final String IDENTIFIER_ERROR = "XXX";
}