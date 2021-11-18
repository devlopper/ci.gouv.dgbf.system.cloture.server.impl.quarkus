package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import org.cyk.utility.persistence.server.query.ArraysReaderByIdentifiers;

public abstract class AbstractOperationImplReader extends ArraysReaderByIdentifiers.AbstractImpl.DefaultImpl<OperationImpl> {

	@Override
	protected Class<OperationImpl> getEntityClass() {
		return OperationImpl.class;
	}
}