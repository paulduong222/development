package gov.nwcg.isuite.core.rules.mbadmin.savepopup;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.MessageBoardVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;

public class AbstractSaveMessagePopUpRule extends AbstractRule {
	
	MessageBoardVo messageBoardVo;
	
	public AbstractSaveMessagePopUpRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#setObject(java.lang.Object, java.lang.String)
	 */
	public void setObject(Object object, String objectName) {
		if(objectName.equals(SaveMessagePopUpRuleFactory.ObjectTypeEnum.MESSAGE_BOARD_VO.name()))
			messageBoardVo = (MessageBoardVo)object;
	}

}
