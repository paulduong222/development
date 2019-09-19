package gov.nwcg.isuite.framework.types;

import gov.nwcg.isuite.core.vo.ResourceClassificationVo;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author bsteiner
 * 
 */
public enum ResourceClassificationEnum {
	//S("Single"), //TODO:  Single in this context has no meaning. It should be removed, or replaced with something else. -dbudge
	//C("S/T T/F Component"),
	ST("Strike Team"), 
	TF("Task Force");

	private String description = "";

	ResourceClassificationEnum(String desc) {
		this.description = desc;
	}

	public String getDescription() {
		return this.description;
	}
	
	public static Collection<ResourceClassificationVo> getResourceClassificationVoList(){
		Collection<ResourceClassificationVo> list = new ArrayList<ResourceClassificationVo>();
		
		//list.add(new ResourceClassificationVo(1L,ResourceClassificationEnum.S.name(),ResourceClassificationEnum.S.getDescription()));
		list.add(new ResourceClassificationVo(1L," "," "));
		list.add(new ResourceClassificationVo(2L,ResourceClassificationEnum.ST.name(),ResourceClassificationEnum.ST.getDescription()));
		list.add(new ResourceClassificationVo(3L,ResourceClassificationEnum.TF.name(),ResourceClassificationEnum.TF.getDescription()));
		//list.add(new ResourceClassificationVo(4L,ResourceClassificationEnum.C.name(),ResourceClassificationEnum.C.getDescription()));
		
		return list;
	}
}