package gov.nwcg.isuite.core.task;

import gov.nwcg.isuite.core.cost.CostGenerator;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.vo.CostResourceDataVo;
import gov.nwcg.isuite.framework.core.task.BaseTask;
import gov.nwcg.isuite.framework.core.task.EISuiteTask;
import gov.nwcg.isuite.framework.exceptions.TaskException;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public class RunDailyCostTask extends BaseTask implements EISuiteTask {
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.tasks.EISuiteTask#runScheduledTask()
	 */
	public int runScheduledTask() throws TaskException {
		
		try{
			//super.simulateLogin();
			
			IncidentDao dao = (IncidentDao)context.getBean("incidentDao");
			IncidentResourceDao irdao = (IncidentResourceDao)context.getBean("incidentResourceDao");
			
			// get list of active incidents
			Collection<Incident> incidents = dao.getAutoCostRunIncidents();
			
			// for each incident, process run cost
			for(Incident incident : incidents){
				Long id=incident.getId();

				// Try and run the cost process for the incident
				Collection<CostResourceDataVo> costResourceDataVos = irdao.getCostResourceData(null,id,null);

				CostGenerator costGen = new CostGenerator(this.context);
				for(CostResourceDataVo v : costResourceDataVos){
					// only process top level resources
					if(!LongUtility.hasValue(v.getParentResourceId())){
						costGen.generateCosts(v, costResourceDataVos,false);
					}
				}
				
			}
			
			processTaskComplete();
		}catch(Exception e){
			super.handleException(e);
		}
		
		return 1;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.task.EISuiteTask#postTask()
	 */
	public void postTask() throws TaskException{

		try{
			
		}catch(Exception e){
			System.out.println(e.getMessage());
			throw new TaskException(e.getMessage());
		}
		
	}
	
}
