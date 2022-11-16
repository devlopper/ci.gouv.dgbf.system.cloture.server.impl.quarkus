package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import org.cyk.utility.persistence.server.query.ArraysReaderByIdentifiers;

public abstract class AbstractImputationImplReader extends ArraysReaderByIdentifiers.AbstractImpl.DefaultImpl<ImputationImpl> {

	@Override
	protected Class<ImputationImpl> getEntityClass() {
		return ImputationImpl.class;
	}
}