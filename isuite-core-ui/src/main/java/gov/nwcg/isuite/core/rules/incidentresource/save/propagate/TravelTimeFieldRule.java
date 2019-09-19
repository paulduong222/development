package gov.nwcg.isuite.core.rules.incidentresource.save.propagate;

import gov.nwcg.isuite.core.rules.data.ResourceData;
import gov.nwcg.isuite.core.vo.PropagateFieldPromptVo;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

public class TravelTimeFieldRule extends AbstractPropagateRule {
	public static final String FIELD_NAME="Travel Time from ICP to Airport";
	
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
		Boolean check=false;
		if(!IntegerUtility.hasValue(super.origParentResourceData.travelHours) && IntegerUtility.hasValue(super.newParentResourceData.travelHours))
			check=true;
		if(!IntegerUtility.hasValue(super.origParentResourceData.travelMinutes) && IntegerUtility.hasValue(super.newParentResourceData.travelMinutes))
			check=true;
		if(check==true){
			for(ResourceData childData : super.childrenResourceData){
				if(!IntegerUtility.hasValue(childData.travelHours) || !IntegerUtility.hasValue(childData.travelMinutes)){
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
		check=false;
		if(IntegerUtility.hasValue(super.origParentResourceData.travelHours) && IntegerUtility.hasValue(super.newParentResourceData.travelHours)){
			if(super.origParentResourceData.travelHours.compareTo(super.newParentResourceData.travelHours)!=0)
				check=true;
		}
		if(IntegerUtility.hasValue(super.origParentResourceData.travelMinutes) && IntegerUtility.hasValue(super.newParentResourceData.travelMinutes)){
			if(super.origParentResourceData.travelMinutes.compareTo(super.newParentResourceData.travelMinutes)!=0)
				check=true;
		}
		if(check==true){
			// prompt user to propagate
			if(BooleanUtility.isFalse(PropagateFieldPromptVo.hasFieldVo(FIELD_NAME, super.propagateFieldPromptVos))){
				propagateFieldPromptVo.setPromptUserMessage("Travel Time from ICP to Airport");
				propagateFieldPromptVo.setPromptUserToPropagate(true);			
				return propagateFieldPromptVo;
			}
		}
		
		propagateFieldPromptVo=null;
		
		return propagateFieldPromptVo;
	}
	

	private PropagateFieldPromptVo checkStrikeTeamRules(){
		
		propagateFieldPromptVo=null;
		
		return propagateFieldPromptVo;
	}
	
}
