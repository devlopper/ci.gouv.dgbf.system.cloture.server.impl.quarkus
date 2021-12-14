package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import java.io.Serializable;

import org.cyk.utility.__kernel__.number.NumberHelper;

public class ActImplNumberOfLocksLockedReader extends ActImplNumberOfLocksEnabledReader implements Serializable {
	
	@Override
	protected void __set__(ActImpl act, Object[] array) {
		super.__set__(act, array);
		act.setLocked(NumberHelper.isGreaterThanZero(act.getNumberOfLocks()));		
	}
}