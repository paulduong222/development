package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;

public class ReportAgencySelectVo extends AbstractVo implements PersistableVo {

	private String label;
	private Long value;
	private Collection<ReportAgencySelectVo> children;
	
	public ReportAgencySelectVo() {}

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }
  
  public Long getValue() {
    return value;
  }

  public void setValue(Long value) {
    this.value = value;
  }

  public Collection<ReportAgencySelectVo> getChildren() {
    if(children == null )
      children = new ArrayList<ReportAgencySelectVo>();
    return children;
  }

  public void setChildren(Collection<ReportAgencySelectVo> children) {
    this.children = children;
  }

  
  /**
   * Returns a ReportAgencySelectVo instance from a Report entity.
   * 
   * @param entity
   * 			the source Report entity
   * @param cascadable
   * 			flag indicating whether the vo instance should created as a cascadable vo
   * @return ReportAgencySelectVo
   * @throws Exception
   */
	public static ReportAgencySelectVo getInstance(ReportAgencySelect entity, boolean cascadable) 
			throws Exception {
		ReportAgencySelectVo dataVo = new ReportAgencySelectVo();

		if (entity == null)
			throw new Exception("Unable to create ReportAgencySelectVo from null ReportAgencySelect entity.");

		ReportAgencySelectVo rsv = new ReportAgencySelectVo();
		rsv.setLabel(entity.getResourceName());
		rsv.setValue(entity.getResourceId());
		
		return rsv;
	}
	
	/**
	 * Return a collection of ReportAgencySelectVos
	 * 
	 * @param entities
	 * 			the source ReportAgencySelect entities
	 * @param cascadable
	 * 			flag indicating whether the vo instances should created as cascadable vos
	 * @return
	 * 			Collection of ReportAgencySelectVos
	 * @throws Exception
	 */
	public static List<ReportAgencySelectVo> getInstances(Collection<ReportAgencySelect> entities, boolean cascadable) 
			throws Exception {
		
		List<ReportAgencySelectVo> dataVos = new ArrayList<ReportAgencySelectVo>();

		for (ReportAgencySelect r : entities) {

			boolean foundAgency = false;
			boolean foundFax = false;

			ReportAgencySelectVo agencyVo = new ReportAgencySelectVo();
			ReportAgencySelectVo faxVo = new ReportAgencySelectVo();

			ReportAgencySelectVo rsv = getInstance(r, true);

			for (ReportAgencySelectVo rasv : dataVos) {
				if (rasv.getLabel()!=null && rasv.getLabel().equalsIgnoreCase(r.getAgencyCode())) {
					foundAgency = true;
					agencyVo = rasv;
					break;
				}
			}

			if (foundAgency) {
				for (ReportAgencySelectVo fax : agencyVo.getChildren()) {
					if (fax.getLabel()!=null && fax.getLabel().equals(r.getFaxNumber())) {
						foundFax = true;
						faxVo = fax;
						break;
					}
				}
			} else {
				agencyVo.setLabel(r.getAgencyCode());
			}

			if (!foundFax) {
				faxVo.setLabel(r.getFaxNumber());
				agencyVo.getChildren().add(faxVo);
			}
			faxVo.getChildren().add(rsv);

			if (!foundAgency) {
				dataVos.add(agencyVo);
			}
		}
		return dataVos;
	}
}

