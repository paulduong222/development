package gov.nwcg.isuite.core.rules.incidentresource.save.propagate;

import gov.nwcg.isuite.core.rules.data.ResourceData;
import gov.nwcg.isuite.core.vo.PropagateFieldPromptVo;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

public class TentativeArrivalDateFieldRule extends AbstractPropagateRule {
	public static final String FIELD_NAME="Estimated Arrival Date";
	
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
		if(!DateUtil.hasValue(super.origParentResourceData.dmTentativeArrivalDate) && DateUtil.hasValue(super.newParentResourceData.dmTentativeArrivalDate)){
			for(ResourceData childData : super.childrenResourceData){
				if(!DateUtil.hasValue(childData.dmTentativeArrivalDate)){
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
		if(DateUtil.hasValue(super.origParentResourceData.dmTentativeArrivalDate) && DateUtil.hasValue(super.newParentResourceData.dmTentativeArrivalDate)){
			if(!DateUtil.isSameDate(origParentResourceData.dmTentativeArrivalDate,newParentResourceData.dmTentativeArrivalDate)){
				// prompt user to propagate
				if(BooleanUtility.isFalse(PropagateFieldPromptVo.hasFieldVo(FIELD_NAME, super.propagateFieldPromptVos))){
					propagateFieldPromptVo.setPromptUserMessage("Estimated Arrival Date" );
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
		//  DEV: Update if child field is null 
		if(!DateUtil.hasValue(super.origParentResourceData.dmTentativeArrivalDate) && DateUtil.hasValue(super.newParentResourceData.dmTentativeArrivalDate)){
			for(ResourceData childData : super.childrenResourceData){
				if(!DateUtil.hasValue(childData.dmTentativeArrivalDate)){
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
		if(DateUtil.hasValue(super.origParentResourceData.dmTentativeArrivalDate) && DateUtil.hasValue(super.newParentResourceData.dmTentativeArrivalDate)){
			if(!DateUtil.isSameDate(origParentResourceData.dmTentativeArrivalDate,newParentResourceData.dmTentativeArrivalDate)){
				// prompt user to propagate
				if(BooleanUtility.isFalse(PropagateFieldPromptVo.hasFieldVo(FIELD_NAME, super.propagateFieldPromptVos))){
					propagateFieldPromptVo.setPromptUserMessage("Estimated Arrival Date" );
					propagateFieldPromptVo.setPromptUserToPropagate(true);			
					return propagateFieldPromptVo;
				}
			}
		}
		
		propagateFieldPromptVo=null;
		
		return propagateFieldPromptVo;
	}
	
}
