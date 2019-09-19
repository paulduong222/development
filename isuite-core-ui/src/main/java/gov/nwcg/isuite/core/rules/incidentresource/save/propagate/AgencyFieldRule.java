package gov.nwcg.isuite.core.rules.incidentresource.save.propagate;

import gov.nwcg.isuite.core.rules.data.ResourceData;
import gov.nwcg.isuite.core.vo.PropagateFieldPromptVo;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

public class AgencyFieldRule extends AbstractPropagateRule {
	public static final String FIELD_NAME="Agency";
	
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
		//if(!StringUtility.hasValue(super.origParentResourceData.agencyCode) && StringUtility.hasValue(super.newParentResourceData.agencyCode)){
		if(null==super.origParentResourceData.agencyId && null != super.newParentResourceData.agencyId){
			for(ResourceData childData : super.childrenResourceData){
				if(null==childData.agencyId){
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
		if(null!=super.origParentResourceData.agencyId && null != super.newParentResourceData.agencyId){
			if(super.origParentResourceData.agencyId.compareTo(super.newParentResourceData.agencyId)!=0){
				// prompt user to propagate
				if(BooleanUtility.isFalse(PropagateFieldPromptVo.hasFieldVo(FIELD_NAME, super.propagateFieldPromptVos))){
					//propagateFieldPromptVo.setPromptUserMessage("Update all crewmembers with the new Agency: " + newParentResourceData.agencyCode + "?" );
					propagateFieldPromptVo.setPromptUserMessage("Agency" );
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
