package ci.gouv.dgbf.system.cloture.server.impl;

import org.cyk.utility.__kernel__.time.TimeHelper;

public class Procedures {
	
	public static String PA_EXECUTER_PROCEDURE(String name) {
		if(UNKNOWN_NAME.equals(name))
			throw new RuntimeException("Cette procédure n'existe pas");
		TimeHelper.pause(7 * 1000l);
		return null;
	}
	
	public static final String UNKNOWN_NAME = "XXX";
}