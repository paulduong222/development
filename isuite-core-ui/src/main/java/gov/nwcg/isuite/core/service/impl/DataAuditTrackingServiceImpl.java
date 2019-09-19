package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.filter.impl.DataAuditTrackingFilterImpl;
import gov.nwcg.isuite.core.persistence.DataAuditTrackingDao;
import gov.nwcg.isuite.core.service.DataAuditTrackingService;
import gov.nwcg.isuite.core.vo.DataAuditTrackingGridVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;

import java.util.ArrayList;
import java.util.Collection;

public class DataAuditTrackingServiceImpl extends BaseService implements DataAuditTrackingService {
	private DataAuditTrackingDao dao;

	public DataAuditTrackingServiceImpl(){
		super();
	}

	public void initialization(){
		dao = (DataAuditTrackingDao)super.context.getBean("dataAuditTrackingDao");
	}

	public DialogueVo getGrid(DialogueVo dialogueVo, DataAuditTrackingFilterImpl dataAuditTrackingFilter) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			Collection<DataAuditTrackingGridVo> vos = new ArrayList<DataAuditTrackingGridVo>();

			vos = dao.getGrid(dataAuditTrackingFilter);
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_AUDITDATA");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setRecordset(vos);
			
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	@Override
	public DialogueVo getGrid(DialogueVo dialogueVo) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
