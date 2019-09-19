package gov.nwcg.isuite.core.rules.incidentresource.save.propagate;

import gov.nwcg.isuite.core.rules.data.ResourceData;
import gov.nwcg.isuite.core.vo.PropagateFieldPromptVo;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;

public class AssignmentStatusFieldRule extends AbstractPropagateRule {
	public static final String FIELD_NAME="Status";
	
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
		//System.out.println(newParentResourceData.status.toString());
		
		// 	Propagate on Changing Null Parent Field
		//  DEV: Update if child status is filled
		if(super.origParentResourceData.status==null && super.newParentResourceData.status != null){
			for(ResourceData childData : super.childrenResourceData){
				if(childData.status == null || childData.status == AssignmentStatusTypeEnum.F){
					// do auto propagate
					if(BooleanUtility.isFalse(PropagateFieldPromptVo.hasFieldVo(FIELD_NAME, super.propagateFieldPromptVos))){
						propagateFieldPromptVo.setFieldAutoPropagate(true);			
						return propagateFieldPromptVo;
					}
				}
			}
		}
		
		// 	Propagate on Editing Parent Field that is not null
		//  DEV: Update if child status is not D or R
		if(super.origParentResourceData.status != newParentResourceData.status){
			for(ResourceData childData : super.childrenResourceData){
				if(childData.status == null || (childData.status != AssignmentStatusTypeEnum.D && childData.status != AssignmentStatusTypeEnum.R )){
					// do auto propagate
					if(BooleanUtility.isFalse(PropagateFieldPromptVo.hasFieldVo(FIELD_NAME, super.propagateFieldPromptVos))){
						propagateFieldPromptVo.setFieldAutoPropagate(true);			
						return propagateFieldPromptVo;
					}
				}
			}
		}
		
		propagateFieldPromptVo=null;
		
		return propagateFieldPromptVo;
	}
	

	private PropagateFieldPromptVo checkStrikeTeamRules(){
		
		// 	Propagate on Changing Null Parent Field
		//  DEV: Update if child status is filled
		if(super.origParentResourceData.status==null && super.newParentResourceData.status != null){
			for(ResourceData childData : super.childrenResourceData){
				if(childData.status == null || childData.status == AssignmentStatusTypeEnum.F){
					// do auto propagate
					if(BooleanUtility.isFalse(PropagateFieldPromptVo.hasFieldVo(FIELD_NAME, super.propagateFieldPromptVos))){
						propagateFieldPromptVo.setFieldAutoPropagate(true);			
						return propagateFieldPromptVo;
					}
				}
			}
		}
		
		// 	Propagate on Editing Parent Field that is not null
		//  DEV: Update if child status is not D or R
		if(super.origParentResourceData.status != newParentResourceData.status){
			for(ResourceData childData : super.childrenResourceData){
				if(childData.status == null || (childData.status != AssignmentStatusTypeEnum.D && childData.status != AssignmentStatusTypeEnum.R )){
					// do auto propagate
					if(BooleanUtility.isFalse(PropagateFieldPromptVo.hasFieldVo(FIELD_NAME, super.propagateFieldPromptVos))){
						propagateFieldPromptVo.setFieldAutoPropagate(true);		
						propagateFieldPromptVo.setIsStrikeTeam(true);
						return propagateFieldPromptVo;
					}
				}
			}
		}
		
		propagateFieldPromptVo=null;
		
		return propagateFieldPromptVo;
	}
	
}
