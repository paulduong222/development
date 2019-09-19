package gov.nwcg.isuite.core.vo.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import gov.nwcg.isuite.core.vo.CostDataVo;
import gov.nwcg.isuite.core.vo.IncidentAccountCodeVo;
import gov.nwcg.isuite.core.vo.IncidentPrefsOtherFieldsVo;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.ResourceVo;
import gov.nwcg.isuite.core.vo.WorkPeriodVo;

public class IncidentResourceVo2 {
	private IncidentVo incidentVo=new IncidentVo();
	private ResourceVo resourceVo=new ResourceVo();
	private Long resNumId=0L;
	private String nameAtIncident;
	private String dailyCostException;
//	private WorkPeriodVo workPeriodVo=new WorkPeriodVo();
	private Date incStartDate;
//	private IncidentPrefsOtherFieldsVo incidentPrefsOtherFieldsVo;
//	private Collection<IncidentAccountCodeVo> incidentAccountCodeVos = new ArrayList<IncidentAccountCodeVo>();
//	private CostDataVo costDataVo;

	private Boolean hasTimeVo=false;
	
	// the default for the incident
//	private IncidentAccountCodeVo defaultIncidentAccountCodeVo;

}
