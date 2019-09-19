package gov.nwcg.isuite.core.rules.incidentresource.save.propagate;

import gov.nwcg.isuite.core.rules.data.ResourceData;
import gov.nwcg.isuite.core.vo.PropagateFieldPromptVo;

import java.util.Collection;

public abstract class AbstractPropagateRule {
	public Boolean isParentStrikeTeam = false;
	public ResourceData origParentResourceData;
	public ResourceData newParentResourceData;
	public Collection<ResourceData> childrenResourceData;
	public Collection<PropagateFieldPromptVo> propagateFieldPromptVos;
	public Long unknownUnitId;
	
	public abstract PropagateFieldPromptVo checkRules();
	
}
