package gov.nwcg.isuite.core.rules.incidentresource.save.propagate;

import gov.nwcg.isuite.core.rules.data.ResourceData;
import gov.nwcg.isuite.core.vo.PropagateFieldPromptVo;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

public class AccountingCodeFieldRule extends AbstractPropagateRule {
	public static final String FIELD_NAME="Accounting Code";
	
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
		// 3/25/2014 - Meeting with Donna in Sacramento, implement propagation
		// rule for accounting code based on "if child is null" instead of "n/a"
		if(!LongUtility.hasValue(super.origParentResourceData.iacId) &&  LongUtility.hasValue(super.newParentResourceData.iacId)){
			for(ResourceData childData : super.childrenResourceData){
				if(!LongUtility.hasValue(childData.iacId)){
					// do auto propagate
					if(BooleanUtility.isFalse(PropagateFieldPromptVo.hasFieldVo(FIELD_NAME, super.propagateFieldPromptVos))){
						propagateFieldPromptVo.setFieldAutoPropagate(true);			
				
						return propagateFieldPromptVo;
					}
				}
			}
		}
		
		
		
		// 	Propagate on Editing Parent Field that is not null
		//  DEV: Do Automatic propagation
		if(LongUtility.hasValue(super.origParentResourceData.iacId) && LongUtility.hasValue(super.newParentResourceData.iacId)){
			if(super.origParentResourceData.iacId.compareTo(super.newParentResourceData.iacId)!=0){
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
