package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import org.cyk.utility.persistence.server.query.ArraysReaderByIdentifiers;

public abstract class AbstractScriptImplReader extends ArraysReaderByIdentifiers.AbstractImpl.DefaultImpl<ScriptImpl> {

	@Override
	protected Class<ScriptImpl> getEntityClass() {
		return ScriptImpl.class;
	}
}