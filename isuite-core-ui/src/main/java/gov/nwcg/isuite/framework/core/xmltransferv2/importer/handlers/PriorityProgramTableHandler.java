package gov.nwcg.isuite.framework.core.xmltransferv2.importer.handlers;

import gov.nwcg.isuite.core.domain.PriorityProgram;
import gov.nwcg.isuite.core.domain.impl.PriorityProgramImpl;
import gov.nwcg.isuite.core.persistence.PriorityProgramDao;
import gov.nwcg.isuite.core.vo.PriorityProgramVo;
import gov.nwcg.isuite.framework.core.xmltransferv2.utility.XmlTransferUtil;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Collection;

import org.apache.log4j.Level;


public class PriorityProgramTableHandler extends BaseTableHandler implements TableHandler {
	
	public PriorityProgramTableHandler(){
	}
	
	public Boolean doPreProcess() throws Exception {
		return true;
	}

	public void doPostInsertProcesses() throws Exception {
		PriorityProgramDao ppDao = (PriorityProgramDao)context.getBean("priorityProgramDao");
		
		/*
		 * For Site Mode, sync with group
		 */
		if(StringUtility.hasValue(super.runMode) && super.runMode.equalsIgnoreCase("SITE")){
			String ti=(String)XmlTransferUtil.invokeGetMethod(xmlObject, "TI");
			Long ppId=0L;
			Long incidentId=0L;
			Long incidentGroupId=0L;
			
			if(StringUtility.hasValue(ti)){
				ppId=ppDao.getIdByTransferableIdentity(ti);
				if(LongUtility.hasValue(ppId)){
					PriorityProgram pp = ppDao.getById(ppId, PriorityProgramImpl.class);
					if(null != pp){
						incidentId=pp.getIncidentId();
						incidentGroupId=pp.getIncidentGroupId();
						ppDao.flushAndEvict(pp);
					}
				}
				
				if(LongUtility.hasValue(incidentId)){
					Collection<PriorityProgramVo> vos2 =ppDao.getGrid(incidentId, null);
					try{
						for(PriorityProgramVo v : vos2){
							ppDao.syncNewFromIncident(v.getCode(), 1L);
						}
					}catch(Exception e){
						this.log(Level.ERROR,"PriorityProgramTableHandler.doPostInsertProcesses() Exception - "+e.getMessage() );
						this.log(Level.ERROR,e.getMessage());
						throw new RuntimeException(e);
					}
				}

				//if(LongUtility.hasValue(incidentGroupId)){
					Collection<PriorityProgramVo> vos =ppDao.getGrid(null, 1L);
					if(CollectionUtility.hasValue(vos)){
						try{
							for(PriorityProgramVo v : vos){
								ppDao.syncNewWithGroup(v.getCode(), 1L);
							}
						}catch(Exception e){
							this.log(Level.ERROR,"PriorityProgramTableHandler.doPostInsertProcesses() Exception - "+e.getMessage() );
							this.log(Level.ERROR,e.getMessage());
							throw new RuntimeException(e);
						}
					}
				//}
			}
			
			
		}
		
		
	}

	public void doPostUpdateProcesses() throws Exception {
	}

}
