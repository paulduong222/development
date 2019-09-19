package gov.nwcg.isuite.core.controllers.restdata;

import java.util.Collection;

import gov.nwcg.isuite.core.vo.ResourceInventoryGridVo;

public class ResourceInventoryGridData extends DialogueData {

	private ResourceInventoryGridVo resourceInventoryGridVo;
	private Collection<ResourceInventoryGridVo> resourceInventoryGridVos;
	
	public ResourceInventoryGridVo getResourceInventoryGridVo() {
		return resourceInventoryGridVo;
	}
	
	public void setResourceInventoryGridVo(ResourceInventoryGridVo resourceInventoryGridVo) {
		this.resourceInventoryGridVo = resourceInventoryGridVo;
	}
	
	public Collection<ResourceInventoryGridVo> getResourceInventoryGridVos() {
		return resourceInventoryGridVos;
	}
	
	public void setResourceInventoryGridVos(Collection<ResourceInventoryGridVo> resourceInventoryGridVos) {
		this.resourceInventoryGridVos = resourceInventoryGridVos;
	}
}
