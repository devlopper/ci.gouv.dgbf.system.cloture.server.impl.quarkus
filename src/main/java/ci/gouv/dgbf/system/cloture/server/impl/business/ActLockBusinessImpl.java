package ci.gouv.dgbf.system.cloture.server.impl.business;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.business.server.AbstractSpecificBusinessImpl;

import ci.gouv.dgbf.system.cloture.server.api.business.ActLockBusiness;
import ci.gouv.dgbf.system.cloture.server.api.persistence.ActLock;

@ApplicationScoped
public class ActLockBusinessImpl extends AbstractSpecificBusinessImpl<ActLock> implements ActLockBusiness,Serializable {

	
}