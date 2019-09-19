package gov.nwcg.isuite.core.rules.incidentresource.save.propagate;

import gov.nwcg.isuite.core.rules.data.ResourceData;
import gov.nwcg.isuite.core.vo.PropagateFieldPromptVo;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

public class CheckInTimeFieldRule extends AbstractPropagateRule {
	public static final String FIELD_NAME="Check-in Time";
	
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
		if(!DateUtil.hasValue(super.origParentResourceData.ciCheckInDate) && DateUtil.hasValue(super.newParentResourceData.ciCheckInDate)){
			if(!StringUtility.hasValue(super.origParentResourceData.ciCheckInTime) && StringUtility.hasValue(super.newParentResourceData.ciCheckInTime)){
				for(ResourceData childData : super.childrenResourceData){
					if(!StringUtility.hasValue(childData.ciCheckInTime)){
						// do auto propagate
						if(BooleanUtility.isFalse(PropagateFieldPromptVo.hasFieldVo(FIELD_NAME, super.propagateFieldPromptVos))){
							propagateFieldPromptVo.setFieldAutoPropagate(true);			
							return propagateFieldPromptVo;
						}
					}
				}
			}
		}
		
		// 	Propagate on Editing Parent Field that is not null
		//  DEV: Prompt Y or N - Would you like to update all crewmembers with the new ______?
		if(DateUtil.hasValue(super.origParentResourceData.ciCheckInDate) && DateUtil.hasValue(super.newParentResourceData.ciCheckInDate)){
			String origTime="";
			String newTime="";
			if(StringUtility.hasValue(origParentResourceData.ciCheckInTime) && !origParentResourceData.ciCheckInTime.equals("0000"))
				origTime=origParentResourceData.ciCheckInTime;
			if(StringUtility.hasValue(newParentResourceData.ciCheckInTime) && !newParentResourceData.ciCheckInTime.equals("0000"))
				newTime=newParentResourceData.ciCheckInTime;
			
			if(!origTime.equals(newTime)){
				// prompt user to propagate
				if(BooleanUtility.isFalse(PropagateFieldPromptVo.hasFieldVo(FIELD_NAME, super.propagateFieldPromptVos))){
					propagateFieldPromptVo.setPromptUserMessage("Check-in Time" );
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
		if(!DateUtil.hasValue(super.origParentResourceData.ciCheckInDate) && DateUtil.hasValue(super.newParentResourceData.ciCheckInDate)){
			if(!StringUtility.hasValue(super.origParentResourceData.ciCheckInTime) && StringUtility.hasValue(super.newParentResourceData.ciCheckInTime)){
				for(ResourceData childData : super.childrenResourceData){
					if(!StringUtility.hasValue(childData.ciCheckInTime)){
						// do auto propagate
						if(BooleanUtility.isFalse(PropagateFieldPromptVo.hasFieldVo(FIELD_NAME, super.propagateFieldPromptVos))){
							propagateFieldPromptVo.setFieldAutoPropagate(true);			
							return propagateFieldPromptVo;
						}
					}
				}
			}
		}
		
		// 	Propagate on Editing Parent Field that is not null
		//  DEV: Prompt Y or N - Would you like to update all crewmembers with the new ______?
		if(DateUtil.hasValue(super.origParentResourceData.ciCheckInDate) && DateUtil.hasValue(super.newParentResourceData.ciCheckInDate)){
			String origTime="";
			String newTime="";
			if(StringUtility.hasValue(origParentResourceData.ciCheckInTime) && !origParentResourceData.ciCheckInTime.equals("0000"))
				origTime=origParentResourceData.ciCheckInTime;
			if(StringUtility.hasValue(newParentResourceData.ciCheckInTime) && !newParentResourceData.ciCheckInTime.equals("0000"))
				newTime=newParentResourceData.ciCheckInTime;
			if(!origTime.equals(newTime)){
				// prompt user to propagate
				if(BooleanUtility.isFalse(PropagateFieldPromptVo.hasFieldVo(FIELD_NAME, super.propagateFieldPromptVos))){
					propagateFieldPromptVo.setPromptUserMessage("Check-in Time" );
					propagateFieldPromptVo.setPromptUserToPropagate(true);			
					return propagateFieldPromptVo;
				}
			}
		}
		
		propagateFieldPromptVo=null;
		
		return propagateFieldPromptVo;
	}
	
}
