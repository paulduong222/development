package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.BranchSetting;
import gov.nwcg.isuite.core.domain.BranchSettingPosition;
import gov.nwcg.isuite.core.domain.IapMasterFrequency;
import gov.nwcg.isuite.core.domain.IapPositionItemCode;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.IncidentShift;
import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.domain.impl.BranchSettingImpl;
import gov.nwcg.isuite.core.domain.impl.BranchSettingPositionImpl;
import gov.nwcg.isuite.core.domain.impl.IapMasterFrequencyImpl;
import gov.nwcg.isuite.core.domain.impl.IapPositionItemCodeImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentShiftImpl;
import gov.nwcg.isuite.core.domain.impl.KindImpl;
import gov.nwcg.isuite.core.filter.IapMasterFrequencyFilter;
import gov.nwcg.isuite.core.persistence.BranchSettingDao;
import gov.nwcg.isuite.core.persistence.BranchSettingPositionDao;
import gov.nwcg.isuite.core.persistence.IapMasterFrequencyDao;
import gov.nwcg.isuite.core.persistence.IapPositionItemCodeDao;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.IncidentShiftDao;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.core.persistence.hibernate.IncidentDaoHibernate;
import gov.nwcg.isuite.core.persistence.hibernate.IncidentGroupDaoHibernate;
import gov.nwcg.isuite.core.rules.IapDeleteBranchPositionRulesHandler;
import gov.nwcg.isuite.core.rules.IapDeleteBranchSettingRulesHandler;
import gov.nwcg.isuite.core.rules.IapMasterFrequencyDeleteRulesHandler;
import gov.nwcg.isuite.core.rules.IapMasterFrequencySaveRulesHandler;
import gov.nwcg.isuite.core.rules.IapOperationalPeriodDeleteRulesHandler;
import gov.nwcg.isuite.core.rules.IapOperationalPeriodSaveRulesHandler;
import gov.nwcg.isuite.core.rules.IapPositionDeleteRulesHandler;
import gov.nwcg.isuite.core.rules.IapPositionSaveRulesHandler;
import gov.nwcg.isuite.core.rules.IapSaveBranchPositionRulesHandler;
import gov.nwcg.isuite.core.rules.IapSaveBranchSettingRulesHandler;
import gov.nwcg.isuite.core.rules.IapSettingIGSaveRulesHandler;
import gov.nwcg.isuite.core.rules.IapSettingSaveRulesHandler;
import gov.nwcg.isuite.core.rules.IsLockedRulesHandler;
import gov.nwcg.isuite.core.service.IapService;
import gov.nwcg.isuite.core.vo.BranchPositionVo;
import gov.nwcg.isuite.core.vo.BranchSettingPositionVo;
import gov.nwcg.isuite.core.vo.BranchSettingVo;
import gov.nwcg.isuite.core.vo.IapBranchRscAssignVo;
import gov.nwcg.isuite.core.vo.IapMasterFrequencyVo;
import gov.nwcg.isuite.core.vo.IapPositionItemCodeVo;
import gov.nwcg.isuite.core.vo.IapPositionVo;
import gov.nwcg.isuite.core.vo.IncidentGroupVo;
import gov.nwcg.isuite.core.vo.IncidentShiftVo;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptor;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorFactory;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorType;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.IapSectionEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.FileUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.xml.XmlHandler;
import gov.nwcg.isuite.framework.xml.XmlSchemaTypeEnum;
import gov.nwcg.isuite.xml.masterfrequency.MasterFrequency;
import gov.nwcg.isuite.xml.masterfrequency.MasterFrequencyType;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.criterion.Restrictions;

public class IapServiceImpl extends BaseService implements IapService {
	private static final String PARAMETER_REPORT_OUTPUT_FOLDER = "REPORT_OUTPUT_FOLDER";
	public IapServiceImpl() {
		super();
	}
	
	public void initialization() {
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapService#deleteIapMasterFrequency(gov.nwcg.isuite.core.vo.dialogue.DialogueVo, gov.nwcg.isuite.core.vo.IapMasterFrequencyVo)
	 */
	public DialogueVo deleteIapMasterFrequency(DialogueVo dialogueVo, IapMasterFrequencyVo vo) throws ServiceException {
		
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{

			if((null == vo) || !LongUtility.hasValue(vo.getId()))
				throw new ServiceException("Unable to delete unknown master frequency.");

			Long id=0L;
			String type="";
			
			if(null != vo.getIncidentVo() && LongUtility.hasValue(vo.getIncidentVo().getId())){
				id=vo.getIncidentVo().getId();
				type="INCIDENT";
			}
			if(null != vo.getIncidentGroupVo() && LongUtility.hasValue(vo.getIncidentGroupVo().getId())){
				id=vo.getIncidentGroupVo().getId();
				type="INCIDENTGROUP";
			}
			IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
			if(lockedRuleHandler.execute(id, type, dialogueVo)!=AbstractRule._OK){
				return dialogueVo;
			}
			
			IapMasterFrequencyDao dao = (IapMasterFrequencyDao)context.getBean("iapMasterFrequencyDao");
			
			IapMasterFrequency entity = null;
			if(LongUtility.hasValue(vo.getId()))
				entity = dao.getById(vo.getId(), IapMasterFrequencyImpl.class);
			
			IapMasterFrequencyDeleteRulesHandler ruleHandler = new IapMasterFrequencyDeleteRulesHandler(context);
			if(ruleHandler.execute(vo, entity,dialogueVo)==AbstractRule._OK){
				
				dao.delete(entity);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_MASTER_FREQUENCY");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.masterFrequency", "info.0028" , new String[]{"Master Frequency"}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				dialogueVo.setResultObject(vo);
				dialogueVo.setCourseOfActionVo(coaVo);
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapService#propagateChanges(gov.nwcg.isuite.core.vo.dialogue.DialogueVo, gov.nwcg.isuite.core.vo.IapMasterFrequencyVo)
	 */
	public DialogueVo propagateChanges(DialogueVo dialogueVo, Collection<IapMasterFrequencyVo> vos) throws ServiceException {
		
		if (null == dialogueVo)
			dialogueVo = new DialogueVo();

		try {
			Long id2=0L;
			String type="";
			for(IapMasterFrequencyVo vo: vos) {
				if(null != vo && null != vo.getIncidentVo() && LongUtility.hasValue(vo.getIncidentVo().getId())){
					id2=vo.getIncidentVo().getId();
					type="INCIDENT";
				}
				if(null != vo && null != vo.getIncidentGroupVo() && LongUtility.hasValue(vo.getIncidentGroupVo().getId())){
					id2=vo.getIncidentGroupVo().getId();
					type="INCIDENTGROUP";
				}
				if(LongUtility.hasValue(id2)){
					IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
					if(lockedRuleHandler.execute(id2, type, dialogueVo)!=AbstractRule._OK){
						return dialogueVo;
					}
				}
				
				if ((null == vo) || !LongUtility.hasValue(vo.getId()))
					throw new ServiceException("Unable to propagate changes for unknown master frequency.");
				}

			
            IapMasterFrequencyDao dao = (IapMasterFrequencyDao)context.getBean("iapMasterFrequencyDao");
						
//			dao.propagateChanges(vo);
			dao.propagateAllFrequencyChanges(vos);

			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("PROPAGATE_CHANGES_MASTER_FREQUENCY");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.masterFrequency",
					"info.0030", new String[] { "Master Frequency" },
					MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(true);
//			dialogueVo.setResultObject(vo);
			dialogueVo.setResultObject(vos);
			dialogueVo.setCourseOfActionVo(coaVo);

		} catch (Exception e) {
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}
	
	public DialogueVo reorderMasterFrequencyPositions(DialogueVo dialogueVo, Collection<IapMasterFrequencyVo> vos) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		Collection<IapMasterFrequencyVo> updatedVos = new ArrayList<IapMasterFrequencyVo>();

		try {
			int cnt=0;
			for(IapMasterFrequencyVo vo: vos) {
				
				if(cnt==0){
					Long id=0L;
					String type="";
					if(null != vo.getIncidentVo() && LongUtility.hasValue(vo.getIncidentVo().getId())){
						id=vo.getIncidentVo().getId();
						type="INCIDENT";
					}
					if(null != vo.getIncidentGroupVo() && LongUtility.hasValue(vo.getIncidentGroupVo().getId())){
						id=vo.getIncidentGroupVo().getId();
						type="INCIDENTGROUP";
					}
					IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
					if(lockedRuleHandler.execute(id, type, dialogueVo)!=AbstractRule._OK){
						return dialogueVo;
					}
					cnt++;
				}
				
				DialogueVo dvo = this.saveIapMasterFrequency(dialogueVo, vo);
				updatedVos.add((IapMasterFrequencyVo) dvo.getResultObject());
			}

			// return result
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("REORDER_MFL_POSITIONS");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"Master Frequencey List was re-ordered."}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(updatedVos);

		} catch (Exception e) {
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapService#exportMasterFrequency(gov.nwcg.isuite.core.vo.dialogue.DialogueVo, gov.nwcg.isuite.core.filter.IapMasterFrequencyFilter)
	 */
	public DialogueVo exportMasterFrequency(DialogueVo dialogueVo, IapMasterFrequencyFilter filter) throws ServiceException {
		
		if (null == dialogueVo)
			dialogueVo = new DialogueVo();

		try {
            /*
			if ((null == vo) || !LongUtility.hasValue(vo.getId()))
				throw new ServiceException(
						"Unable to export for master frequency.");
		    */
			
			SystemParameterDao spDao = (SystemParameterDao)super.context.getBean("systemParameterDao");
			SystemParameter spEntity = spDao.getByParameterName(PARAMETER_REPORT_OUTPUT_FOLDER);

			//if( (null != spEntity) && ( null != spEntity.getParameterValue()) && (!spEntity.getParameterValue().isEmpty()) ){
			//return spEntity.getParameterValue() + "masterfrequencies_export.xml";
			//}else{
			//return "masterfrequencies_export.xml";
			//}
			
  		    IapMasterFrequencyDao dao = (IapMasterFrequencyDao)context.getBean("iapMasterFrequencyDao");
			Collection<IapMasterFrequencyVo> vos = new ArrayList<IapMasterFrequencyVo>();
			vos = dao.getGrid(filter);
			
			MasterFrequency rootNode = new MasterFrequency();
			for(IapMasterFrequencyVo vo : vos){
				MasterFrequencyType freq = new MasterFrequencyType();
				freq.setAssignment(vo.getAssignment());
				freq.setChannel(vo.getChannel());
				freq.setChannelNameRadioTalkgroup(vo.getChannelName());
				freq.setFunction(vo.getRfunction());
				freq.setMode(vo.getMode());
				freq.setRemarks(vo.getRemarks());
				freq.setRXFreq(vo.getRxFreq());
				freq.setRXTone(vo.getRxTone());
				freq.setShow((vo.getShow() == true) ? "Y" : "N");
				freq.setTXFreq(vo.getTxFreq());
				freq.setTXTone(vo.getTxTone());
				freq.setZoneGroup(vo.getGroup());
				
				rootNode.getFrequency().add(freq);
			}
			
			// Convert the xml object(s) to a StringBuffer
			XmlHandler xmlHandler = new XmlHandler();
			xmlHandler.setFormatXml(true);
			xmlHandler.setXmlSchemaType(XmlSchemaTypeEnum.MASTER_FREQUENCY);
			StringBuffer xmlString = xmlHandler.marshall(rootNode);
			
			//System.out.println(xmlString);

			//FileUtil.writeFile(xmlString.toString().getBytes(), spEntity.getParameterValue() + "masterfrequencies_export.xml");

			//CourseOfActionVo coaVo = new CourseOfActionVo();
			//coaVo.setCoaName("EXPORT_MASTER_FREQUENCY");
			//coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			//coaVo.setMessageVo(new MessageVo("text.masterFrequency",
			//		"info.9916", new String[] { "Export Successful! You can find masterfrequencies_export.xml in the report output directory." },
			//		MessageTypeEnum.INFO));
			//coaVo.setIsDialogueEnding(true);
			//dialogueVo.setCourseOfActionVo(coaVo);
			
			byte[] returnDataBytes = xmlString.toString().getBytes();

			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("EXPORT_MASTER_FREQUENCY");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coaVo.setIsDialogueEnding(true);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(returnDataBytes);

		} catch (Exception e) {
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapService#exportMasterFrequency(gov.nwcg.isuite.core.vo.dialogue.DialogueVo, gov.nwcg.isuite.core.filter.IapMasterFrequencyFilter, byte[])
	 */
	public DialogueVo importMasterFrequency(DialogueVo dialogueVo, IapMasterFrequencyFilter filter, byte[] xmlByteArray) throws ServiceException {
		
		if (null == dialogueVo)
			dialogueVo = new DialogueVo();

		try {
			Long id=0L;
			String type="";
			if(LongUtility.hasValue(filter.getIncidentId())){
				id=filter.getIncidentId();
				type="INCIDENT";
			}
			if(LongUtility.hasValue(filter.getIncidentGroupId())){
				id=filter.getIncidentGroupId();
				type="INCIDENTGROUP";
			}
			IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
			if(lockedRuleHandler.execute(id, type, dialogueVo)!=AbstractRule._OK){
				return dialogueVo;
			}
           			
			StringBuffer xmlBuffer = new StringBuffer().append(new String(xmlByteArray));
			
			// Convert the StringBuffer to a xml object(s) 
			XmlHandler xmlHandler = new XmlHandler();
			xmlHandler.setFormatXml(true);
			xmlHandler.setXmlSchemaType(XmlSchemaTypeEnum.MASTER_FREQUENCY);
			MasterFrequency rootNode = (MasterFrequency)xmlHandler.unmarshall(xmlBuffer);		
			
            IapMasterFrequencyDao dao = (IapMasterFrequencyDao)context.getBean("iapMasterFrequencyDao");
            
            for(MasterFrequencyType freq : rootNode.getFrequency()){

            	IapMasterFrequency entity = null;
            	IapMasterFrequencyVo vo = new IapMasterFrequencyVo();
            	
            	if(LongUtility.hasValue(filter.getIncidentId())) {
             		IncidentVo ivo = new IncidentVo();
            		ivo.setId(filter.getIncidentId());
            		vo.setIncidentVo(ivo);
            
        		}
        		else {
                	   IncidentGroupVo igvo = new IncidentGroupVo();
            		   igvo.setId(filter.getIncidentGroupId());
            		   vo.setIncidentGroupVo(igvo);
        		}
            	
            	vo.setAssignment(freq.getAssignment());
            	vo.setChannel(freq.getChannel());
            	vo.setChannelName(freq.getChannelNameRadioTalkgroup());
            	vo.setRfunction(freq.getFunction());
            	vo.setMode(freq.getMode());
            	vo.setRemarks(freq.getRemarks());
            	vo.setRxFreq(freq.getRXFreq());
            	vo.setRxTone(freq.getRXTone());
            	vo.setShow((freq.getShow().equals("Y")) ? true : false);
            	vo.setTxFreq(freq.getTXFreq());
            	vo.setTxTone(freq.getTXTone());
            	vo.setGroup(freq.getZoneGroup());
            	            	
            	IapMasterFrequencySaveRulesHandler ruleHandler = new IapMasterFrequencySaveRulesHandler(context);
            	if(ruleHandler.execute(vo, entity, dialogueVo)==IapMasterFrequencySaveRulesHandler._OK) {
    				
    				entity = IapMasterFrequencyVo.toEntity(entity, vo, true);
    				dao.save(entity);
    				dao.flushAndEvict(entity);
            	}
            	
			}
            
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("IMPORT_MASTER_FREQUENCY");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.masterFrequency",
					"info.0030", new String[] { "Import Successful!" },
					MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(true);
			//dialogueVo.setResultObject(vo);
			dialogueVo.setCourseOfActionVo(coaVo);

		} catch (Exception e) {
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapService#saveIapMasterFrequency(gov.nwcg.isuite.core.vo.dialogue.DialogueVo, gov.nwcg.isuite.core.vo.IapMasterFrequencyVo)
	 */
	public DialogueVo saveIapMasterFrequency(DialogueVo dialogueVo, IapMasterFrequencyVo vo) throws ServiceException {

		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IapMasterFrequencyDao dao = (IapMasterFrequencyDao)context.getBean("iapMasterFrequencyDao");

			Long id=0L;
			String type="";
			if(null != vo.getIncidentVo() && LongUtility.hasValue(vo.getIncidentVo().getId())){
				id=vo.getIncidentVo().getId();
				type="INCIDENT";
			}
			if(null != vo.getIncidentGroupVo() && LongUtility.hasValue(vo.getIncidentGroupVo().getId())){
				id=vo.getIncidentGroupVo().getId();
				type="INCIDENTGROUP";
			}
			IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
			if(lockedRuleHandler.execute(id, type, dialogueVo)!=AbstractRule._OK){
				return dialogueVo;
			}
			
			
			IapMasterFrequency entity = null;
			
			if(LongUtility.hasValue(vo.getId())) {
				entity = dao.getById(vo.getId(), IapMasterFrequencyImpl.class);
			}
			
			IapMasterFrequencySaveRulesHandler ruleHandler = new IapMasterFrequencySaveRulesHandler(context);
			
			if(ruleHandler.execute(vo, entity, dialogueVo)==IapMasterFrequencySaveRulesHandler._OK) {
				
				entity = IapMasterFrequencyVo.toEntity(entity, vo, true);
				dao.save(entity);
				dao.flushAndEvict(entity);
				
				entity = dao.getById(entity.getId(), IapMasterFrequencyImpl.class);
				vo = IapMasterFrequencyVo.getInstance(entity, true);
				
				//Build course of action
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_MASTER_FREQUENCY");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.masterFrequency", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				
				dialogueVo.setCourseOfActionVo(coaVo);

				// set the result object with updated vo
				dialogueVo.setResultObject(vo);
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapService#getMasterFrequencyGrid(gov.nwcg.isuite.core.vo.dialogue.DialogueVo, gov.nwcg.isuite.core.filter.IapMasterFrequencyFilter)
	 */
	public DialogueVo getMasterFrequencyGrid(DialogueVo dialogueVo, IapMasterFrequencyFilter filter) throws ServiceException {

		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IapMasterFrequencyDao dao = (IapMasterFrequencyDao)context.getBean("iapMasterFrequencyDao");
			Collection<IapMasterFrequencyVo> vos = new ArrayList<IapMasterFrequencyVo>();
			
			// Check if incident is part of a group.
			// If it is, remove the incident id in the filter and instead set the incident group id.
			// This is because incidents inside an incidentgroup do NOT have their own MFL. They
			// instead use the group's MFL, regardless of whether they are being accessed directly, or 
			// through the group. 
			
			if(LongUtility.hasValue(filter.getIncidentGroupId())) { // Ignore the incident id completely.
				filter.setIncidentId(null);
			} else if(LongUtility.hasValue(filter.getIncidentId())) { // Check if incident is part of a group.
				IncidentDao incidentDao = (IncidentDao)super.context.getBean("incidentDao");
				
				Long incidentGroupId = incidentDao.getIncidentGroupId(filter.getIncidentId());
				if(LongUtility.hasValue(incidentGroupId)) {
					filter.setIncidentId(null);
					filter.setIncidentGroupId(incidentGroupId);
				}
			}
			
			vos = dao.getGrid(filter);
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_MASTER_FREQUENCIES");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapService#saveOperationalPeriod(gov.nwcg.isuite.core.vo.dialogue.DialogueVo, gov.nwcg.isuite.core.vo.IncidentShiftVo)
	 */
	public DialogueVo saveOperationalPeriod(DialogueVo dialogueVo, IncidentShiftVo vo) throws ServiceException {
		
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IncidentShiftDao dao = (IncidentShiftDao)super.context.getBean("incidentShiftDao");
			
			IncidentShift entity = null;
			
			if(LongUtility.hasValue(vo.getId())) {
				entity = dao.getById(vo.getId(), IncidentShiftImpl.class);
			}
			
			IapOperationalPeriodSaveRulesHandler ruleHandler = new IapOperationalPeriodSaveRulesHandler(context);
			
			if(ruleHandler.execute(vo, entity, dialogueVo)==IapOperationalPeriodSaveRulesHandler._OK) {
				
				entity = IncidentShiftVo.toEntity(entity, vo, true);
				dao.save(entity);
				dao.flushAndEvict(entity);
				
				entity = dao.getById(entity.getId(), IncidentShiftImpl.class);
				vo = IncidentShiftVo.getInstance(entity, true);
				
				//Build course of action
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_OPERATIONAL_PERIOD");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.operationalPeriod", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				
				dialogueVo.setCourseOfActionVo(coaVo);

				// set the result object with updated vo
				dialogueVo.setResultObject(vo);
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapService#deleteOperationalPeriod(gov.nwcg.isuite.core.vo.dialogue.DialogueVo, gov.nwcg.isuite.core.vo.IncidentShiftVo)
	 */
	public DialogueVo deleteOperationalPeriod(DialogueVo dialogueVo, IncidentShiftVo vo) throws ServiceException {
	
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{

			if((null == vo) || !LongUtility.hasValue(vo.getId()))
				throw new ServiceException("Unable to delete unknown operational period.");

			IncidentShiftDao dao = (IncidentShiftDao)super.context.getBean("incidentShiftDao");
			
			IncidentShift entity = null;
			if(LongUtility.hasValue(vo.getId()))
				entity = dao.getById(vo.getId(), IncidentShiftImpl.class);
			
			IapOperationalPeriodDeleteRulesHandler ruleHandler = new IapOperationalPeriodDeleteRulesHandler(context);
			if(ruleHandler.execute(vo, entity,dialogueVo)==AbstractRule._OK){
				
				dao.delete(entity);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_OPERATIONAL_PERIOD");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.operationalPeriod", "info.0028" , new String[]{"Operational Period"}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				dialogueVo.setResultObject(vo);
				dialogueVo.setCourseOfActionVo(coaVo);
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapService#saveIapSettings(gov.nwcg.isuite.core.vo.dialogue.DialogueVo, gov.nwcg.isuite.core.vo.IncidentVo)
	 */
	public DialogueVo saveIapSettings(DialogueVo dialogueVo, IncidentVo vo) throws ServiceException {
        if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IncidentDao dao = (IncidentDao)super.context.getBean("incidentDao");
			
			Long id=0L;
			String type="";
			if(null != vo && LongUtility.hasValue(vo.getId())){
				id=vo.getId();
				type="INCIDENT";
			}
			IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
			if(lockedRuleHandler.execute(id, type, dialogueVo)!=AbstractRule._OK){
				return dialogueVo;
			}

			Incident entity = null;
			
			if(LongUtility.hasValue(vo.getId())) {
				entity = dao.getById(vo.getId(), IncidentImpl.class);
			}
			
			IapSettingSaveRulesHandler ruleHandler = new IapSettingSaveRulesHandler(context);
			
			if(ruleHandler.execute(vo, entity, dialogueVo)==IapSettingSaveRulesHandler._OK) {
							
				entity = IncidentVo.toEntity(entity, vo, true);
				
				dao.save(entity);
				dao.flushAndEvict(entity);
				
				entity = dao.getById(entity.getId(), IncidentImpl.class);
				vo = IncidentVo.getInstance(entity, true);
				
				//Build course of action
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_IAP_SETTINGS");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.iapSettings", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				
				dialogueVo.setCourseOfActionVo(coaVo);

				// set the result object with updated vo
				dialogueVo.setResultObject(vo);
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;	
	
	}	
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapService#saveIapSettings(gov.nwcg.isuite.core.vo.dialogue.DialogueVo, gov.nwcg.isuite.core.vo.IncidentVo)
	 */
	public DialogueVo saveIapSettingsIG(DialogueVo dialogueVo, IncidentGroupVo vo) throws ServiceException {
        if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IncidentGroupDao dao = (IncidentGroupDao)super.context.getBean("incidentGroupDao");
			
			Long id=0L;
			String type="";
			if(null != vo && LongUtility.hasValue(vo.getId())){
				id=vo.getId();
				type="INCIDENTGROUP";
			}
			IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
			if(lockedRuleHandler.execute(id, type, dialogueVo)!=AbstractRule._OK){
				return dialogueVo;
			}
			
			IncidentGroup entity = null;
			
			if(LongUtility.hasValue(vo.getId())) {
				entity = dao.getById(vo.getId(), IncidentGroupImpl.class);
			}
			
			IapSettingIGSaveRulesHandler ruleHandler = new IapSettingIGSaveRulesHandler(context);
			
			if(ruleHandler.execute(vo, entity, dialogueVo)==IapSettingIGSaveRulesHandler._OK) {
							
				entity = IncidentGroupVo.toEntity(entity, vo, true);
				
				dao.save(entity);
				dao.flushAndEvict(entity);
				
				entity = dao.getById(entity.getId(), IncidentGroupImpl.class);
				vo = IncidentGroupVo.getInstance(entity, true);
				
				//Build course of action
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_IAP_SETTINGS");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.iapSettings", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				
				dialogueVo.setCourseOfActionVo(coaVo);

				// set the result object with updated vo
				dialogueVo.setResultObject(vo);
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;	
	
	}	
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapService#getPositions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo, java.lang.Long)
	 */
	public DialogueVo getPositions(DialogueVo dialogueVo, Long incidentId) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			IapPositionItemCodeDao dao = (IapPositionItemCodeDao)context.getBean("iapPositionItemCodeDao");
			
			Collection<IapPositionVo> vos = new ArrayList<IapPositionVo>();
			
			vos = dao.getGrid(incidentId);
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_POSITIONS");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapService#savePosition(gov.nwcg.isuite.core.vo.dialogue.DialogueVo, gov.nwcg.isuite.core.vo.IapPositionVo)
	 */
	public DialogueVo savePosition(DialogueVo dialogueVo, IapPositionVo vo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			Long id2=0L;
			String type="";
			if(null != vo.getIncidentVo() && LongUtility.hasValue(vo.getIncidentVo().getId())){
				id2=vo.getIncidentVo().getId();
				type="INCIDENT";
			}
			if(null != vo.getIncidentGroupVo() && LongUtility.hasValue(vo.getIncidentGroupVo().getId())){
				id2=vo.getIncidentGroupVo().getId();
				type="INCIDENTGROUP";
			}
			
			if(LongUtility.hasValue(id2)){
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(id2, type, dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}
			
			if((null == vo.getIncidentVo() || !LongUtility.hasValue(vo.getIncidentVo().getId())) && 
			   (null == vo.getIncidentGroupVo() || !LongUtility.hasValue(vo.getIncidentGroupVo().getId()))) {
				throw new ServiceException("IncidentId/IncidentGroupId is required.");
			}

			
			IapPositionItemCodeDao dao = (IapPositionItemCodeDao)context.getBean("iapPositionItemCodeDao");
			
			Collection<IapPositionItemCode> entities = new ArrayList<IapPositionItemCode>();
			
			String positionName=vo.getPosition();
			String oldPositionName="";
			
			if(LongUtility.hasValue(vo.getId())){
				// get original entity by id
				IapPositionItemCode entity = dao.getById(vo.getId(), IapPositionItemCodeImpl.class);
				if(null != entity){
					// get position name
					positionName=entity.getPosition();
					oldPositionName=entity.getPosition();
					dao.flushAndEvict(entity);
				}
			}
			
			entities = dao.getByPosition(vo,positionName);
			
			IapPositionSaveRulesHandler ruleHandler = new IapPositionSaveRulesHandler(context);
			
			if(ruleHandler.execute(vo, dialogueVo) == IapPositionSaveRulesHandler._OK) {
				for(IapPositionItemCode entity : entities) {
					if(!positionName.equals(vo.getPosition())){
						entity.setPosition(vo.getPosition());
						dao.save(entity);
					}
					dao.flushAndEvict(entity);
				}
				
				Collection<IapPositionItemCode> positionsToDelete=new ArrayList<IapPositionItemCode>();
				Collection<IapPositionItemCodeVo> vosDeleted=new ArrayList<IapPositionItemCodeVo>();
				Collection<IapPositionItemCode> positionsToCreate=new ArrayList<IapPositionItemCode>();
				Collection<IapPositionItemCodeVo> vosCreated=new ArrayList<IapPositionItemCodeVo>();

				// get list of position item codes to delete
				for(IapPositionItemCode entity : entities){
					Boolean bFound=false;
					for(KindVo kindVo : vo.getItemCodeVos()){
						if(kindVo.getId().compareTo(entity.getKindId())==0)
							bFound=true;
					}
					
					if(!bFound)
						positionsToDelete.add(entity);
				}

				if(CollectionUtility.hasValue(positionsToDelete)){
					for(IapPositionItemCode entity : positionsToDelete){
						vosDeleted.add(IapPositionItemCodeVo.getInstance(entity, false));
						dao.delete(entity);
					}
				}
					
				
				// get list of position item codes to create
				for(KindVo kindVo : vo.getItemCodeVos()){
					Boolean bFound=false;
					for(IapPositionItemCode entity : entities){
						if(kindVo.getId().compareTo(entity.getKindId())==0)
							bFound=true;
					}
					
					if(!bFound){
						IapPositionItemCode newEntity = new IapPositionItemCodeImpl();
						newEntity.setForm(vo.getForm());
						newEntity.setPosition(positionName.toUpperCase());
						newEntity.setSection(IapSectionEnum.toEnumValue(vo.getSectionVo().getCode()));
						newEntity.setKind(KindVo.toEntity(null,kindVo, false));
						if(null != vo.getIncidentVo() && LongUtility.hasValue(vo.getIncidentVo().getId())){
							Incident incident = new IncidentImpl();
							incident.setId(vo.getIncidentVo().getId());
							newEntity.setIncident(incident);
						}
						if(null != vo.getIncidentGroupVo() && LongUtility.hasValue(vo.getIncidentGroupVo().getId())){
							IncidentGroup incidentGroup = new IncidentGroupImpl();
							incidentGroup.setId(vo.getIncidentGroupVo().getId());
							newEntity.setIncidentGroup(incidentGroup);
						}
						
						positionsToCreate.add(newEntity);
					}
				}
				
				if(CollectionUtility.hasValue(positionsToCreate)){
					for(IapPositionItemCode entity : positionsToCreate){
						dao.save(entity);
						Long id=entity.getId();
						dao.flushAndEvict(entity.getKind());
						dao.flushAndEvict(entity);
						
						IapPositionItemCode newEntity=dao.getById(id, IapPositionItemCodeImpl.class);
						vosCreated.add(IapPositionItemCodeVo.getInstance(newEntity, true));
					}
				}
				
				
				//Build course of action
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_POSITION");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.position", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				
				dialogueVo.setCourseOfActionVo(coaVo);

				// set the result object with updated vos
				dialogueVo.setResultObject(vosCreated);
				dialogueVo.setResultObjectAlternate(vosDeleted);
				dialogueVo.setResultObjectAlternate2(oldPositionName);
				dialogueVo.setResultObjectAlternate3(vo.getPosition());
			}
			
		}catch(Exception e){
			super.handleException(e);
			//super.dialogueException(dialogueVo, e);
		}	
		
		return dialogueVo;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapService#deletePosition(gov.nwcg.isuite.core.vo.dialogue.DialogueVo, gov.nwcg.isuite.core.vo.IapPositionVo)
	 */
	public DialogueVo deletePosition(DialogueVo dialogueVo, IapPositionVo vo) throws ServiceException {
		
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			Long id2=0L;
			String type="";
			if(null != vo.getIncidentVo() && LongUtility.hasValue(vo.getIncidentVo().getId())){
				id2=vo.getIncidentVo().getId();
				type="INCIDENT";
			}
			if(null != vo.getIncidentGroupVo() && LongUtility.hasValue(vo.getIncidentGroupVo().getId())){
				id2=vo.getIncidentGroupVo().getId();
				type="INCIDENTGROUP";
			}
			
			if(LongUtility.hasValue(id2)){
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(id2, type, dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}

			if((null == vo) || !StringUtility.hasValue(vo.getPosition()))
				throw new ServiceException("Unable to delete unknown position.");

			IapPositionItemCodeDao dao = (IapPositionItemCodeDao)context.getBean("iapPositionItemCodeDao");
			
			Collection<IapPositionItemCode> entities = new ArrayList<IapPositionItemCode>();
			
			String positionName=vo.getPosition();
			
			if(LongUtility.hasValue(vo.getId())){
				// get original entity by id
				IapPositionItemCode entity = dao.getById(vo.getId(), IapPositionItemCodeImpl.class);
				if(null != entity){
					// get position name
					positionName=entity.getPosition();
					dao.flushAndEvict(entity);
				}
			}
			
			entities = dao.getByPosition(vo,positionName);
			
			IapPositionDeleteRulesHandler ruleHandler = new IapPositionDeleteRulesHandler(context);
			if(ruleHandler.execute(dialogueVo)==AbstractRule._OK){
				
				for(IapPositionItemCode ent : entities){
					dao.delete(ent);
				}
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_POSITION");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.position", "info.0028" , new String[]{"Position"}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				dialogueVo.setResultObject(vo);
				dialogueVo.setResultObjectAlternate(positionName);
				dialogueVo.setCourseOfActionVo(coaVo);
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapService#getBranchSettings(java.lang.Long, java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getBranchSettings(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try{
			BranchSettingDao bsDao = (BranchSettingDao)context.getBean("branchSettingDao");
			Collection<BranchSettingVo> vos = new ArrayList<BranchSettingVo>();
			
			Collection<BranchSetting> entities = bsDao.getByIncidentorGroupId(incidentId, incidentGroupId);
			if(CollectionUtility.hasValue(entities)){
				vos=BranchSettingVo.getInstances(entities, true);
			}
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_BRANCH_SETTINGS");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coaVo.setIsDialogueEnding(true);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapService#saveBranchSetting(gov.nwcg.isuite.core.vo.BranchSettingVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveBranchSetting(BranchSettingVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try{
			BranchSettingDao bsDao = (BranchSettingDao)context.getBean("branchSettingDao");
			BranchSetting entity = null;
			Boolean isNew=true;

			if(LongUtility.hasValue(vo.getId())){
				entity = bsDao.getById(vo.getId(), BranchSettingImpl.class);
				bsDao.flushAndEvict(entity);
				isNew=false;
			}
			
			IapSaveBranchSettingRulesHandler ruleHandler = new IapSaveBranchSettingRulesHandler(context);
			if(ruleHandler.execute(vo,entity,dialogueVo)==AbstractRule._OK){
				
				entity = BranchSettingVo.toEntity(entity, vo, true);
				bsDao.save(entity);
				bsDao.flushAndEvict(entity);
				
				if(isNew==true){
					// create the default position item codes records
					bsDao.createDefaultPositions(entity.getId());
				}
				
				entity = bsDao.getById(entity.getId(), BranchSettingImpl.class);
				vo=BranchSettingVo.getInstance(entity, true);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_BRANCH_SETTING");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setIsDialogueEnding(true);
				coaVo.setMessageVo(new MessageVo("text.branch", "info.0030" , null, MessageTypeEnum.INFO));
				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(vo);
			}
			
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapService#deleteBranchSetting(gov.nwcg.isuite.core.vo.BranchSettingVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteBranchSetting(BranchSettingVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try{
			BranchSettingDao bsDao = (BranchSettingDao)context.getBean("branchSettingDao");
			
			Long id2=0L;
			String type="";
			if(LongUtility.hasValue(vo.getIncidentId())){
				id2=vo.getIncidentId();
				type="INCIDENT";
			}
			if(LongUtility.hasValue(vo.getIncidentGroupId())){
				id2=vo.getIncidentGroupId();
				type="INCIDENTGROUP";
			}
			
			if(LongUtility.hasValue(id2)){
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(id2, type, dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}
			
			IapDeleteBranchSettingRulesHandler ruleHandler = new IapDeleteBranchSettingRulesHandler(context);
			if(ruleHandler.execute(vo,dialogueVo)==AbstractRule._OK){
				
				BranchSetting entity = bsDao.getById(vo.getId(), BranchSettingImpl.class);
				bsDao.delete(entity);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_BRANCH_SETTING");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setIsDialogueEnding(true);
				coaVo.setMessageVo(new MessageVo("text.branch", "info.0028" , new String[]{"Branch"}, MessageTypeEnum.INFO));
				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(vo);
			}
			
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapService#saveBranchPosition(java.lang.Long, gov.nwcg.isuite.core.vo.BranchPositionVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveBranchPosition(Long branchSettingId,BranchPositionVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			
			IapSaveBranchPositionRulesHandler ruleHandler = new IapSaveBranchPositionRulesHandler(context);
			if(ruleHandler.execute(vo, branchSettingId, dialogueVo)==AbstractRule._OK){
				BranchSettingPositionDao dao = (BranchSettingPositionDao)context.getBean("branchSettingPositionDao");
				
				if(vo.getIsNew()==true){
					// create the position
					for(KindVo kindVo : vo.getKindVos()){
						BranchSettingPosition entity = new BranchSettingPositionImpl();
						BranchSetting branchSetting = new BranchSettingImpl();
						branchSetting.setId(branchSettingId);
						entity.setBranchSetting(branchSetting);
						entity.setPosition(vo.getPosition());
						Kind kind = new KindImpl();
						kind.setId(kindVo.getId());
						entity.setKind(kind);
						dao.save(entity);
						dao.flushAndEvict(entity);
					}
				}else{
					// run updates
					Collection<BranchSettingPosition> entities = dao.getByBranchAndPosition(branchSettingId, vo.getPosition());
					Collection<KindVo> kindVosToAdd = new ArrayList<KindVo>();
					Collection<BranchSettingPosition> entitiesToRemove = new ArrayList<BranchSettingPosition>();

					if(CollectionUtility.hasValue(entities)){
						for(KindVo kindVo : vo.getKindVos()){
							Boolean isAdd=true;
							for(BranchSettingPosition entity : entities){
								if(entity.getKindId().compareTo(kindVo.getId())==0){
									isAdd=false;
									break;
								}
							}
							if(isAdd==true)kindVosToAdd.add(kindVo);
						}
						for(BranchSettingPosition entity : entities){
							Boolean isRemove=true;
							for(KindVo kindVo : vo.getKindVos()){
								if(entity.getKindId().compareTo(kindVo.getId())==0){
									isRemove=false;
									break;
								}
							}
							if(isRemove==true)entitiesToRemove.add(entity);
						}
						for(KindVo kindVo : kindVosToAdd){
							BranchSettingPosition entity = new BranchSettingPositionImpl();
							BranchSetting branchSetting = new BranchSettingImpl();
							branchSetting.setId(branchSettingId);
							entity.setBranchSetting(branchSetting);
							entity.setPosition(vo.getPosition());
							Kind kind = new KindImpl();
							kind.setId(kindVo.getId());
							entity.setKind(kind);
							dao.save(entity);
							dao.flushAndEvict(entity);
						}
						for(BranchSettingPosition entity : entitiesToRemove){
							dao.delete(entity);
						}
					}else{
						for(KindVo kindVo : vo.getKindVos()){
							BranchSettingPosition entity = new BranchSettingPositionImpl();
							BranchSetting branchSetting = new BranchSettingImpl();
							branchSetting.setId(branchSettingId);
							entity.setBranchSetting(branchSetting);
							entity.setPosition(vo.getPosition());
							Kind kind = new KindImpl();
							kind.setId(kindVo.getId());
							entity.setKind(kind);
							dao.save(entity);
							dao.flushAndEvict(entity);
						}
					}
				}

				Collection<BranchSettingPosition> entities = dao.getByBranchAndPosition(branchSettingId, vo.getPosition());
				Collection<BranchSettingPositionVo> branchSettingPositionVos = BranchSettingPositionVo.getInstances(entities, true);
				Collection<BranchPositionVo> branchPositionVos = BranchPositionVo.getInstances(branchSettingPositionVos);
				BranchPositionVo updatedVo = null;
				if(CollectionUtility.hasValue(branchPositionVos)){
					updatedVo = branchPositionVos.iterator().next();
				}
					
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_BRANCH_POSITION");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setIsDialogueEnding(true);
				coaVo.setMessageVo(new MessageVo("text.branch", "info.0028" , new String[]{"Branch Position"}, MessageTypeEnum.INFO));
				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(updatedVo);
				dialogueVo.setResultObjectAlternate(branchSettingPositionVos);
				dialogueVo.setResultObjectAlternate2(branchPositionVos);
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapService#deleteBranchPosition(java.lang.Long, gov.nwcg.isuite.core.vo.BranchPositionVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteBranchPosition(Long branchSettingId,BranchPositionVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			
			IapDeleteBranchPositionRulesHandler ruleHandler = new IapDeleteBranchPositionRulesHandler(context);
			if(ruleHandler.execute(vo, dialogueVo)==AbstractRule._OK){

				BranchSettingPositionDao dao = (BranchSettingPositionDao)context.getBean("branchSettingPositionDao");
				Collection<BranchSettingPosition> entities = dao.getByBranchAndPosition(branchSettingId, vo.getPosition());
				for(BranchSettingPosition entity : entities){
					dao.delete(entity);
				}
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_BRANCH_POSITION");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setIsDialogueEnding(true);
				coaVo.setMessageVo(new MessageVo("text.branch", "info.0028" , new String[]{"Branch Position"}, MessageTypeEnum.INFO));
				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(vo);

			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}
	
	public DialogueVo restoreDefault203TemplateSettings(Long incidentId, Long incidentGroupId, String sectionCode, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			if(!LongUtility.hasValue(incidentId) && !LongUtility.hasValue(incidentGroupId))
				throw new ServiceException("Incident and Incident Group ID cannot both be null");

			if(LongUtility.hasValue(incidentId) && LongUtility.hasValue(incidentGroupId))
				throw new ServiceException("Only one of Incident or Incident Group ID should have a value");

			if(LongUtility.hasValue(incidentId)) {
				Long id2=incidentId;
				String type="INCIDENT";
				if(LongUtility.hasValue(id2)){
					IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
					if(lockedRuleHandler.execute(id2, type, dialogueVo)!=AbstractRule._OK){
						return dialogueVo;
					}
				}
				
				IncidentDao dao = (IncidentDao)context.getBean("incidentDao");
				dao.restoreDefault203TemplateSettings(incidentId, sectionCode);

				// Retrieve the updated positions 
				IapPositionItemCodeDao iapPositionItemCodeDao = (IapPositionItemCodeDao)context.getBean("iapPositionItemCodeDao");
				Collection<IapPositionItemCodeVo> results = iapPositionItemCodeDao.getAllForIncident(incidentId);
				dialogueVo.setRecordset(results);
			} else {
				Long id2=incidentGroupId;
				String type="INCIDENTGROUP";
				if(LongUtility.hasValue(id2)){
					IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
					if(lockedRuleHandler.execute(id2, type, dialogueVo)!=AbstractRule._OK){
						return dialogueVo;
					}
				}
				IncidentGroupDao dao = (IncidentGroupDaoHibernate)context.getBean("incidentGroupDao");
				dao.restoreDefault203TemplateSettings(incidentGroupId, sectionCode);

				// Retrieve the updated positions 
				IapPositionItemCodeDao iapPositionItemCodeDao = (IapPositionItemCodeDao)context.getBean("iapPositionItemCodeDao");
				Collection<IapPositionItemCodeVo> results = iapPositionItemCodeDao.getAllForIncidentGroup(incidentGroupId);
				dialogueVo.setRecordset(results);
			}
							
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("RESTORE_DEFAULT_TEMPLATE_SETTINGS");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The Default 203 Template settings for this section have been restored."}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(true);

			// Set the COA in the dialogueVo containing the updated positions.
			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo restoreDefault204TemplateSettings(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			if(!LongUtility.hasValue(incidentId) && !LongUtility.hasValue(incidentGroupId))
				throw new ServiceException("Incident and Incident Group ID cannot both be null");

			if(LongUtility.hasValue(incidentId) && LongUtility.hasValue(incidentGroupId))
				throw new ServiceException("Only one of Incident or Incident Group ID should have a value");

			if(LongUtility.hasValue(incidentId)) {
				Long id2=incidentId;
				String type="INCIDENT";
				if(LongUtility.hasValue(id2)){
					IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
					if(lockedRuleHandler.execute(id2, type, dialogueVo)!=AbstractRule._OK){
						return dialogueVo;
					}
				}
				IncidentDao dao = (IncidentDao)context.getBean("incidentDao");
				dao.restoreDefault204TemplateSettings(incidentId);

				// Retrieve the updated positions 
				IapPositionItemCodeDao iapPositionItemCodeDao = (IapPositionItemCodeDao)context.getBean("iapPositionItemCodeDao");
				Collection<IapPositionItemCodeVo> results = iapPositionItemCodeDao.getAllForIncident(incidentId);
				dialogueVo.setRecordset(results);
			} else {
				Long id2=incidentGroupId;
				String type="INCIDENTGROUP";
				if(LongUtility.hasValue(id2)){
					IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
					if(lockedRuleHandler.execute(id2, type, dialogueVo)!=AbstractRule._OK){
						return dialogueVo;
					}
				}
				IncidentGroupDao dao = (IncidentGroupDaoHibernate)context.getBean("incidentGroupDao");
				dao.restoreDefault204TemplateSettings(incidentGroupId);

				// Retrieve the updated positions 
				IapPositionItemCodeDao iapPositionItemCodeDao = (IapPositionItemCodeDao)context.getBean("iapPositionItemCodeDao");
				Collection<IapPositionItemCodeVo> results = iapPositionItemCodeDao.getAllForIncidentGroup(incidentGroupId);
				dialogueVo.setRecordset(results);
			}
							
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("RESTORE_DEFAULT_TEMPLATE_SETTINGS");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The Default 204 Template settings have been restored."}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(true);

			// Set the COA in the dialogueVo containing the updated positions.
			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

}
