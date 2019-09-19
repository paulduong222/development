package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.CostGroupAgencyDaySharePercentage;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;

public class CostGroupAgencyDaySharePercentageGridVo extends AbstractVo {
	
	private String agencyCode;
	private String percentage;
	
	public CostGroupAgencyDaySharePercentageGridVo() {
	}

	public static Collection<CostGroupAgencyDaySharePercentageGridVo> getInstances(Collection<CostGroupAgencyDaySharePercentage> entities, boolean cascadable) throws Exception {
		ArrayList<CostGroupAgencyDaySharePercentageGridVo> vos = new ArrayList<CostGroupAgencyDaySharePercentageGridVo>();
		
		for(CostGroupAgencyDaySharePercentage entity : entities) {
			CostGroupAgencyDaySharePercentageGridVo vo = CostGroupAgencyDaySharePercentageGridVo.getInstance(entity, cascadable);
			vos.add(vo);
		}
		
		return vos;
	}
	
	public static CostGroupAgencyDaySharePercentageGridVo getInstance(CostGroupAgencyDaySharePercentage entity, boolean cascadable) throws Exception {
		CostGroupAgencyDaySharePercentageGridVo vo = new CostGroupAgencyDaySharePercentageGridVo();
		
		if(null != entity) {
			vo.setId(entity.getId());
			
			if(cascadable) {
				if(null != entity.getAgency()) {
					vo.setAgencyCode(entity.getAgency().getAgencyCode());
				}
				if(null != entity.getPercentage()) {
					vo.setPercentage(entity.getPercentage().toString() + "%");
				}
			}
		}
		
		return vo;
	}
	
	/**
	 * @param agencyCode the agencyCode to set
	 */
	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}

	/**
	 * @return the agencyCode
	 */
	public String getAgencyCode() {
		return agencyCode;
	}

	/**
	 * @param percentage the percentage to set
	 */
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	/**
	 * @return the percentage
	 */
	public String getPercentage() {
		return percentage;
	}

}
