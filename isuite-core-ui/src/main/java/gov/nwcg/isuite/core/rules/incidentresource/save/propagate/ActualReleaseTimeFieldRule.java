package gov.nwcg.isuite.core.rules.incidentresource.save.propagate;

import gov.nwcg.isuite.core.rules.data.ResourceData;
import gov.nwcg.isuite.core.vo.PropagateFieldPromptVo;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

public class ActualReleaseTimeFieldRule extends AbstractPropagateRule {
	public static final String FIELD_NAME="Actual Release Time";
	
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
		if(!DateUtil.hasValue(super.origParentResourceData.dmReleaseDate) && DateUtil.hasValue(super.newParentResourceData.dmReleaseDate)){
			if(!StringUtility.hasValue(super.origParentResourceData.dmReleaseTime) && StringUtility.hasValue(super.newParentResourceData.dmReleaseTime)){
				for(ResourceData childData : super.childrenResourceData){
					if(!StringUtility.hasValue(childData.dmReleaseTime)){
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
		if(DateUtil.hasValue(super.origParentResourceData.dmReleaseDate) && DateUtil.hasValue(super.newParentResourceData.dmReleaseDate)){
			String origTime="";
			String newTime="";
			if(StringUtility.hasValue(origParentResourceData.dmReleaseTime) && !origParentResourceData.dmReleaseTime.equals("0000"))
				origTime=origParentResourceData.dmReleaseTime;
			if(StringUtility.hasValue(newParentResourceData.dmReleaseTime) && !newParentResourceData.dmReleaseTime.equals("0000"))
				newTime=newParentResourceData.dmReleaseTime;
			if(!origTime.equals(newTime)){
				// prompt user to propagate
				if(BooleanUtility.isFalse(PropagateFieldPromptVo.hasFieldVo(FIELD_NAME, super.propagateFieldPromptVos))){
					propagateFieldPromptVo.setPromptUserMessage("Actual Release Time?" );
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
		if(!DateUtil.hasValue(super.origParentResourceData.dmReleaseDate) && DateUtil.hasValue(super.newParentResourceData.dmReleaseDate)){
			if(!StringUtility.hasValue(super.origParentResourceData.dmReleaseTime) && StringUtility.hasValue(super.newParentResourceData.dmReleaseTime)){
				for(ResourceData childData : super.childrenResourceData){
					if(!StringUtility.hasValue(childData.dmReleaseTime)){
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
		if(DateUtil.hasValue(super.origParentResourceData.dmReleaseDate) && DateUtil.hasValue(super.newParentResourceData.dmReleaseDate)){
			String origTime="";
			String newTime="";
			if(StringUtility.hasValue(origParentResourceData.dmReleaseTime) && !origParentResourceData.dmReleaseTime.equals("0000"))
				origTime=origParentResourceData.dmReleaseTime;
			if(StringUtility.hasValue(newParentResourceData.dmReleaseTime) && !newParentResourceData.dmReleaseTime.equals("0000"))
				newTime=newParentResourceData.dmReleaseTime;
			if(!origTime.equals(newTime)){
				// prompt user to propagate
				if(BooleanUtility.isFalse(PropagateFieldPromptVo.hasFieldVo(FIELD_NAME, super.propagateFieldPromptVos))){
					propagateFieldPromptVo.setPromptUserMessage("Actual Release Time?" );
					propagateFieldPromptVo.setPromptUserToPropagate(true);			
					return propagateFieldPromptVo;
				}
			}
		}
		
		propagateFieldPromptVo=null;
		
		return propagateFieldPromptVo;
	}
	
}
