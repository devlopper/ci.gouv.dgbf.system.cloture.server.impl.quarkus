package ci.gouv.dgbf.system.cloture.server.impl.persistence;

import org.cyk.utility.persistence.server.query.ArraysReaderByIdentifiers;

public abstract class AbstractActLockImplReader extends ArraysReaderByIdentifiers.AbstractImpl.DefaultImpl<ActLockImpl> {

	@Override
	protected Class<ActLockImpl> getEntityClass() {
		return ActLockImpl.class;
	}
}