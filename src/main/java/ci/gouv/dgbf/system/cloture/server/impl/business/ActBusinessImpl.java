package ci.gouv.dgbf.system.cloture.server.impl.business;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.business.server.AbstractSpecificBusinessImpl;

import ci.gouv.dgbf.system.cloture.server.api.business.ActBusiness;
import ci.gouv.dgbf.system.cloture.server.api.persistence.Act;

@ApplicationScoped
public class ActBusinessImpl extends AbstractSpecificBusinessImpl<Act> implements ActBusiness,Serializable {
	
}