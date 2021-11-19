package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import org.cyk.utility.persistence.server.query.ArraysReaderByIdentifiers;

public abstract class AbstractActImplReader extends ArraysReaderByIdentifiers.AbstractImpl.DefaultImpl<ActImpl> {

	@Override
	protected Class<ActImpl> getEntityClass() {
		return ActImpl.class;
	}
}