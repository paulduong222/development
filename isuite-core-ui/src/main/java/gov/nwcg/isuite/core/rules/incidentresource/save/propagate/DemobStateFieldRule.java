package gov.nwcg.isuite.core.rules.incidentresource.save.propagate;

import gov.nwcg.isuite.core.rules.data.ResourceData;
import gov.nwcg.isuite.core.vo.PropagateFieldPromptVo;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

public class DemobStateFieldRule extends AbstractPropagateRule {
	public static final String FIELD_NAME="Demob State";
	
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
		//  DEV: Update if child field is null 
		if(!LongUtility.hasValue(super.origParentResourceData.dmTentativeDemobStateId) && LongUtility.hasValue(super.newParentResourceData.dmTentativeDemobStateId)){
			for(ResourceData childData : super.childrenResourceData){
				if(!LongUtility.hasValue(childData.dmTentativeDemobStateId)){
					// do auto propagate
					if(BooleanUtility.isFalse(PropagateFieldPromptVo.hasFieldVo(FIELD_NAME, super.propagateFieldPromptVos))){
						propagateFieldPromptVo.setFieldAutoPropagate(true);			
						return propagateFieldPromptVo;
					}
				}
			}
		}
		
		// 	Propagate on Editing Parent Field that is not null
		//  DEV: Prompt Y or N - Would you like to update all crewmembers with the new ______?
		if(LongUtility.hasValue(super.origParentResourceData.dmTentativeDemobStateId) && LongUtility.hasValue(super.newParentResourceData.dmTentativeDemobStateId)){
			if(super.origParentResourceData.dmTentativeDemobStateId.compareTo(newParentResourceData.dmTentativeDemobStateId)!=0){
				// prompt user to propagate
				if(BooleanUtility.isFalse(PropagateFieldPromptVo.hasFieldVo(FIELD_NAME, super.propagateFieldPromptVos))){
					propagateFieldPromptVo.setPromptUserMessage("Demob State" );
					propagateFieldPromptVo.setPromptUserToPropagate(true);			
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
		
		propagateFieldPromptVo=null;
		
		return propagateFieldPromptVo;
	}
	
}
