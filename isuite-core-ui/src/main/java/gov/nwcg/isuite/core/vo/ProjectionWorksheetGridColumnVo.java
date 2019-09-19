package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.ProjectionItemWorksheet;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectionWorksheetGridColumnVo {
	
	private Date columnDate;
	private BigDecimal quantity;
	private BigDecimal averageCost;
	private Integer numberOfPersonnel;
	private Boolean total;
	private Boolean currency;
	
	/**
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public static ProjectionWorksheetGridColumnVo getInstance(ProjectionItemWorksheet entity) throws Exception {
		ProjectionWorksheetGridColumnVo vo = new ProjectionWorksheetGridColumnVo();
		
		vo.columnDate = entity.getProjectionDate();
		vo.quantity = new BigDecimal(entity.getQuantity().toString());
		vo.numberOfPersonnel = entity.getNumberOfPersonnel();
		vo.averageCost = entity.getAverageCost();
		vo.total = false;
		vo.currency = false;
		
		return vo;
	}
	
	/**
	 * @param entities
	 * @return
	 * @throws Exception
	 */
	public static Collection<ProjectionWorksheetGridColumnVo> getInstances(Collection<ProjectionItemWorksheet> entities) throws Exception {
		Collection<ProjectionWorksheetGridColumnVo> vos = new ArrayList<ProjectionWorksheetGridColumnVo>();
		
		for(ProjectionItemWorksheet entity : entities) {
			vos.add(ProjectionWorksheetGridColumnVo.getInstance(entity));
		}
		
		return vos;
	}
	
	/**
	 * @param entities
	 * @return
	 * @throws Exception
	 */
	public static Collection<ProjectionWorksheetGridColumnVo> getPersonTotals(Collection<ProjectionItemWorksheet> entities) throws Exception {
		Collection<ProjectionWorksheetGridColumnVo> vos = new ArrayList<ProjectionWorksheetGridColumnVo>();
		Boolean bFound = false;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");	
		
		for(ProjectionItemWorksheet entity : entities) {
			for(ProjectionWorksheetGridColumnVo colVo : vos) {
				if(dateFormat.format(entity.getProjectionDate()).equals(dateFormat.format(colVo.getColumnDate()))) {
					bFound = true;
					colVo.setNumberOfPersonnel(colVo.getNumberOfPersonnel() + entity.getNumberOfPersonnel());
					break;
				}
			}
			if(!bFound) {
				ProjectionWorksheetGridColumnVo columnVo = new ProjectionWorksheetGridColumnVo();
				columnVo.setColumnDate(entity.getProjectionDate());
				columnVo.setNumberOfPersonnel(entity.getNumberOfPersonnel());
				columnVo.setTotal(true);
				vos.add(columnVo);
			}
			bFound = false;
		}
		
		return vos;
	}
	
	/**
	 * @param entities
	 * @return
	 * @throws Exception
	 */
	public static Collection<ProjectionWorksheetGridColumnVo> getAverageCostForAllItems(Collection<ProjectionItemWorksheet> entities) throws Exception {
		
		Collection<ProjectionWorksheetGridColumnVo> vos = new ArrayList<ProjectionWorksheetGridColumnVo>();
		Map<Date,ProjectionWorksheetGridColumnVo> vosMap = new HashMap<Date,ProjectionWorksheetGridColumnVo>();
		
		//Boolean bFound = false;
		for(ProjectionItemWorksheet entity : entities) {
			if(!vosMap.containsKey(entity.getProjectionDate())) {
				ProjectionWorksheetGridColumnVo columnVo = new ProjectionWorksheetGridColumnVo();
				columnVo.setAverageCost(BigDecimal.ZERO);
				columnVo.setColumnDate(entity.getProjectionDate());
				//if(entity.getProjectionItem().getIsSupportCost().getValue()) 
				columnVo.setAverageCost(entity.getAverageCost());
				//if(!entity.getProjectionItem().getIsSupportCost().getValue())
				columnVo.setNumberOfPersonnel(entity.getNumberOfPersonnel());
				columnVo.setCurrency(true);
				vosMap.put(entity.getProjectionDate(),columnVo);
				vos.add(columnVo);
			}
		}
		return vos;
	}
	
	/**
	 * @param entities
	 * @return
	 * @throws Exception
	 */
	public static Collection<ProjectionWorksheetGridColumnVo> getProjectionTotals(Collection<ProjectionItemWorksheet> entities) throws Exception {
		Collection<ProjectionWorksheetGridColumnVo> vos = new ArrayList<ProjectionWorksheetGridColumnVo>();
		Boolean bFound = false;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		for(ProjectionItemWorksheet entity : entities) {
			for(ProjectionWorksheetGridColumnVo colVo : vos) {
				if(dateFormat.format(entity.getProjectionDate()).equals(dateFormat.format(colVo.getColumnDate()))) {
					bFound = true;
					
					if(null !=  entity.getAverageCost() && null != entity.getQuantity()) {
						colVo.setQuantity(colVo.getQuantity().add((entity.getAverageCost()).multiply(new BigDecimal(entity.getQuantity()))));
					}
					
					break;
				}
			}
			if(!bFound) {
				ProjectionWorksheetGridColumnVo columnVo = new ProjectionWorksheetGridColumnVo();
				columnVo.setColumnDate(entity.getProjectionDate());
				
				if(null !=  entity.getAverageCost() && null != entity.getQuantity()) {
					//columnVo.setQuantity((entity.getAverageCost()).multiply(new BigDecimal(entity.getQuantity())).intValue());
					columnVo.setQuantity((entity.getAverageCost()).multiply(new BigDecimal(entity.getQuantity())));
				}
				
				columnVo.setTotal(true);
				columnVo.setCurrency(true);
				vos.add(columnVo);
			}
			bFound = false;
		}
		
		return vos;
	}
	
	/**
	 * @param entities
	 * @return
	 * @throws Exception
	 */
	public static Collection<ProjectionWorksheetGridColumnVo> getSupportingCostTotals(Collection<ProjectionItemWorksheet> entities) throws Exception {
		Collection<ProjectionWorksheetGridColumnVo> vos = new ArrayList<ProjectionWorksheetGridColumnVo>();
		Map<Date,ProjectionWorksheetGridColumnVo> vosMap = new HashMap<Date,ProjectionWorksheetGridColumnVo>();
		
		for(ProjectionItemWorksheet entity : entities) {
			if(!vosMap.containsKey(entity.getProjectionDate())) {
				ProjectionWorksheetGridColumnVo columnVo = new ProjectionWorksheetGridColumnVo();
				columnVo.setAverageCost(BigDecimal.ZERO);
				columnVo.setColumnDate(entity.getProjectionDate());
				columnVo.setAverageCost(entity.getAverageCost().multiply(new BigDecimal(entity.getNumberOfPersonnel())));
				columnVo.setTotal(true);
				columnVo.setCurrency(true);
				vosMap.put(entity.getProjectionDate(),columnVo);
				vos.add(columnVo);
			}
		}
				
		return vos;
	}
	
	/**
	 * @param columnDate the columnDate to set
	 */
	public void setColumnDate(Date columnDate) {
		this.columnDate = columnDate;
	}

	/**
	 * @return the columnDate
	 */
	public Date getColumnDate() {
		return columnDate;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the quantity
	 */
	public BigDecimal getQuantity() {
		return quantity;
	}

	/**
	 * @param numberOfPersonnel the numberOfPersonnel to set
	 */
	public void setNumberOfPersonnel(Integer numberOfPersonnel) {
		this.numberOfPersonnel = numberOfPersonnel;
	}

	/**
	 * @return the numberOfPersonnel
	 */
	public Integer getNumberOfPersonnel() {
		return numberOfPersonnel;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Boolean total) {
		this.total = total;
	}

	/**
	 * @return the total
	 */
	public Boolean getTotal() {
		return total;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(Boolean currency) {
		this.currency = currency;
	}

	/**
	 * @return the currency
	 */
	public Boolean getCurrency() {
		return currency;
	}

	public BigDecimal getAverageCost() {
		return averageCost;
	}

	public void setAverageCost(BigDecimal averageCost) {
		this.averageCost = averageCost;
	}
}
