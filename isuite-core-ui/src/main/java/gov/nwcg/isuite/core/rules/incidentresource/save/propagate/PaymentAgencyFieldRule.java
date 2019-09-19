package gov.nwcg.isuite.core.rules.incidentresource.save.propagate;

import gov.nwcg.isuite.core.vo.PropagateFieldPromptVo;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

public class PaymentAgencyFieldRule extends AbstractPropagateRule {
	public static final String FIELD_NAME="Payment Agency";
	
	private PropagateFieldPromptVo propagateFieldPromptVo = new PropagateFieldPromptVo();
	
	public PropagateFieldPromptVo checkRules() {
		propagateFieldPromptVo.setFieldName(FIELD_NAME);
		
		// Non-Strike Teams 
		if(super.isParentStrikeTeam==false)
			return this.checkNonStrikeTeamRules();
		else
			return this.checkStrikeTeamRules();
	}
	
	private PropagateFieldPromptVo checkNonStrikeTeamRules(){
		// 	Propagate on Changing Null Parent Field
		//  DEV: Not Applicable according to propagation matrix
		if(!LongUtility.hasValue(super.origParentResourceData.paymentAgencyId)){
			if(LongUtility.hasValue(super.newParentResourceData.paymentAgencyId)){
				if(BooleanUtility.isFalse(PropagateFieldPromptVo.hasFieldVo(FIELD_NAME, super.propagateFieldPromptVos))){
					propagateFieldPromptVo.setFieldAutoPropagate(true);			
					return propagateFieldPromptVo;
				}
			}
		}
		
		propagateFieldPromptVo=null;
		return propagateFieldPromptVo;
	}
	

	private PropagateFieldPromptVo checkStrikeTeamRules(){
		// 	Propagate on Changing Null Parent Field
		//  DEV: Not Applicable according to propagation matrix

		// 	Propagate on Editing Parent Field that is not null
		//  DEV: Not Applicable according to propagation matrix
		
		return null;
	}
	
}
