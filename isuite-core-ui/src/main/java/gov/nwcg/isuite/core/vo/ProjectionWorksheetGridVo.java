package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.ProjectionItemWorksheet;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;

public class ProjectionWorksheetGridVo extends AbstractVo {
	
	private String itemCode;
	private Boolean totalRow;
	private Collection<ProjectionWorksheetGridColumnVo> projectionWorksheetGridColumnVos = new ArrayList<ProjectionWorksheetGridColumnVo>();

	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static ProjectionWorksheetGridVo getInstance(String itemCodeGroup, Collection<ProjectionItemWorksheet> entities) throws Exception {
		ProjectionWorksheetGridVo vo = new ProjectionWorksheetGridVo();
		vo.itemCode = itemCodeGroup;
		vo.totalRow = false;
		
		vo.projectionWorksheetGridColumnVos = ProjectionWorksheetGridColumnVo.getInstances(entities);
		
		return vo;
	}
	
	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<ProjectionWorksheetGridVo> getInstances(Collection<ProjectionItemWorksheet> entities) throws Exception {
		Collection<ProjectionWorksheetGridVo> vos = new ArrayList<ProjectionWorksheetGridVo>();
		Collection<ProjectionItemWorksheet> itemCodeGroup = new ArrayList<ProjectionItemWorksheet>();
		String itemCode = "";
		
		for(ProjectionItemWorksheet entity : entities) {
			
			if (itemCode != entity.getProjectionItem().getItemCodeGroup()) {
				if(itemCodeGroup.size() > 0) {
					vos.add(ProjectionWorksheetGridVo.getInstance(itemCode, itemCodeGroup));
				}
			}
			
			itemCode = entity.getProjectionItem().getItemCodeGroup();
			itemCodeGroup.add(entity);
		
		}
		
		if(itemCodeGroup.size() > 0) {
			vos.add(ProjectionWorksheetGridVo.getInstance(itemCode, itemCodeGroup));
		}
		
		if(!entities.isEmpty()) {
			vos.add(ProjectionWorksheetGridVo.getPersonTotals(entities));
			vos.add(ProjectionWorksheetGridVo.getProjectionTotals(entities));
		}
		
		return vos;
	}
	
	public static Collection<ProjectionWorksheetGridVo> getSupportingCostInstances(Collection<ProjectionItemWorksheet> entities) throws Exception {
		Collection<ProjectionWorksheetGridVo> vos = new ArrayList<ProjectionWorksheetGridVo>();
		if(!entities.isEmpty()) {
			vos.add(ProjectionWorksheetGridVo.getSupportingCostAverageAndPersonTotals(entities));
			vos.add(ProjectionWorksheetGridVo.getSupportingCostTotal(entities));
		}
		
		return vos;
	}
	
	/**
	 * @param entities
	 * @return
	 * @throws Exception
	 */
	public static ProjectionWorksheetGridVo getPersonTotals(Collection<ProjectionItemWorksheet> entities) throws Exception {
		ProjectionWorksheetGridVo vo = new ProjectionWorksheetGridVo();
		
		vo.itemCode = "Total";
		vo.totalRow = true;
		vo.projectionWorksheetGridColumnVos = ProjectionWorksheetGridColumnVo.getPersonTotals(entities);
		
		return vo;
	}
	
	/**
	 * @param entities
	 * @return
	 * @throws Exception
	 */
	public static ProjectionWorksheetGridVo getSupportingCostTotal(Collection<ProjectionItemWorksheet> entities) throws Exception {
		ProjectionWorksheetGridVo vo = new ProjectionWorksheetGridVo();
		
		vo.itemCode = "Total";
		vo.totalRow = true;
		vo.projectionWorksheetGridColumnVos = ProjectionWorksheetGridColumnVo.getSupportingCostTotals(entities);
		
		return vo;
	}
	
	/**
	 * @param entities
	 * @return
	 * @throws Exception
	 */
	public static ProjectionWorksheetGridVo getSupportingCostAverageAndPersonTotals(Collection<ProjectionItemWorksheet> entities) throws Exception {
		ProjectionWorksheetGridVo vo = new ProjectionWorksheetGridVo();
		
		vo.itemCode = "";
		vo.totalRow = false;
		vo.projectionWorksheetGridColumnVos = ProjectionWorksheetGridColumnVo.getAverageCostForAllItems(entities);
		
		return vo;
	}
	/**
	 * @param entities
	 * @return
	 * @throws Exception
	 */
	public static ProjectionWorksheetGridVo getProjectionTotals(Collection<ProjectionItemWorksheet> entities) throws Exception {
		
		ProjectionWorksheetGridVo vo = new ProjectionWorksheetGridVo();
		
		vo.itemCode = "Projection Total";
		vo.totalRow = true;
		vo.projectionWorksheetGridColumnVos = ProjectionWorksheetGridColumnVo.getProjectionTotals(entities);
		
		return vo;
	}
	
	
	/**
	 * @param itemCode the itemCode to set
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * @return the itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * @param totalRow the totalRow to set
	 */
	public void setTotalRow(Boolean totalRow) {
		this.totalRow = totalRow;
	}

	/**
	 * @return the totalRow
	 */
	public Boolean getTotalRow() {
		return totalRow;
	}

	/**
	 * @param projectionWorksheetGridColumnVos the projectionWorksheetGridColumnVos to set
	 */
	public void setProjectionWorksheetGridColumnVos(Collection<ProjectionWorksheetGridColumnVo> projectionWorksheetGridColumnVos) {
		this.projectionWorksheetGridColumnVos = projectionWorksheetGridColumnVos;
	}

	/**
	 * @return the projectionWorksheetGridColumnVos
	 */
	public Collection<ProjectionWorksheetGridColumnVo> getProjectionWorksheetGridColumnVos() {
		return projectionWorksheetGridColumnVos;
	}


}
