package gov.nwcg.isuite.framework.util;

import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.core.vo.AssignmentVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class UniqueDatesUtil {

	public Collection<Date> getUniqueTimePostDates(Collection<IncidentResourceVo> irs) {
		Collection<Date> dates = new ArrayList<Date>();
		Set<Date> dateSet = new HashSet<Date>();

		for(IncidentResourceVo ir : irs) {
			for (AssignmentVo a : ir.getWorkPeriodVo().getAssignmentVos()) {
				for(AssignmentTimePostVo atp : a.getAssignmentTimeVo().getAssignmentTimePostVos()) {
					Date date = DateUtil.makeEndOfDay(atp.getPostStartDate());
					dateSet.add(date);
				}
			}
		}
		dates.addAll(dateSet);
		return dates;
	}
}
