package gov.nwcg.isuite.core.rules.referencedata.jetports.save;

import gov.nwcg.isuite.core.domain.JetPort;
import gov.nwcg.isuite.core.persistence.JetPortDao;
import gov.nwcg.isuite.core.vo.JetPortVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

import org.springframework.context.ApplicationContext;

public class AbstractSaveJetPortRule extends AbstractRule {
	
	JetPortVo jetPortVo;
	JetPort jetPortEntity;
	JetPortDao jetPortDao;
	
	public AbstractSaveJetPortRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(ReferenceDataSaveJetPortRuleFactory.ObjectTypeEnum.JET_PORT_VO.name()))
			jetPortVo = (JetPortVo)object;
		if(objectName.equals(ReferenceDataSaveJetPortRuleFactory.ObjectTypeEnum.JET_PORT_ENTITY.name()))
			jetPortEntity = (JetPort)object;
		if(objectName.equals(ReferenceDataSaveJetPortRuleFactory.ObjectTypeEnum.JET_PORT_DAO.name()))
			jetPortDao = (JetPortDao)object;
	}
	
	
}
