package gov.nwcg.isuite.core.controllers.restdata;

import java.util.Collection;

import gov.nwcg.isuite.core.vo.CostGroupResourceGridVo;

public class CostGroupResourcesData extends DialogueData {

	private Collection<CostGroupResourceGridVo> resources;
	private Collection<CostGroupResourceGridVo> otherResources;

	public Collection<CostGroupResourceGridVo> getResources() {
		return resources;
	}

	public void setResources(Collection<CostGroupResourceGridVo> resources) {
		this.resources = resources;
	}

	public Collection<CostGroupResourceGridVo> getOtherResources() {
		return otherResources;
	}

	public void setOtherResources(Collection<CostGroupResourceGridVo> otherResources) {
		this.otherResources = otherResources;
	}

}
