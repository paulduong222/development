package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.IapAttachment;
import gov.nwcg.isuite.core.domain.IapBranch;
import gov.nwcg.isuite.core.domain.IapBranchRscAssign;
import gov.nwcg.isuite.core.domain.IapForm202;
import gov.nwcg.isuite.core.domain.IapForm203;
import gov.nwcg.isuite.core.domain.IapForm205;
import gov.nwcg.isuite.core.domain.IapForm206;
import gov.nwcg.isuite.core.domain.IapForm220;
import gov.nwcg.isuite.core.domain.IapPlan;
import gov.nwcg.isuite.core.domain.impl.IapBranchImpl;
import gov.nwcg.isuite.core.domain.impl.IapForm202Impl;
import gov.nwcg.isuite.core.domain.impl.IapForm203Impl;
import gov.nwcg.isuite.core.domain.impl.IapForm205Impl;
import gov.nwcg.isuite.core.domain.impl.IapForm206Impl;
import gov.nwcg.isuite.core.domain.impl.IapForm220Impl;
import gov.nwcg.isuite.core.domain.impl.IapPlanImpl;
import gov.nwcg.isuite.core.persistence.IapAttachmentDao;
import gov.nwcg.isuite.core.persistence.IapBranchDao;
import gov.nwcg.isuite.core.persistence.IapBranchRscAssignDao;
import gov.nwcg.isuite.core.persistence.IapForm202Dao;
import gov.nwcg.isuite.core.persistence.IapForm203Dao;
import gov.nwcg.isuite.core.persistence.IapForm205Dao;
import gov.nwcg.isuite.core.persistence.IapForm206Dao;
import gov.nwcg.isuite.core.persistence.IapForm220Dao;
import gov.nwcg.isuite.core.persistence.IapPlanDao;
import gov.nwcg.isuite.core.rules.IapCopyFormRulesHandler;
import gov.nwcg.isuite.core.rules.IapCopyPlanRulesHandler;
import gov.nwcg.isuite.core.rules.IsLockedRulesHandler;
import gov.nwcg.isuite.core.service.IapCopyService;
import gov.nwcg.isuite.core.vo.IapAircraftFrequencyVo;
import gov.nwcg.isuite.core.vo.IapAircraftTaskVo;
import gov.nwcg.isuite.core.vo.IapAircraftVo;
import gov.nwcg.isuite.core.vo.IapAreaLocationCapabilityVo;
import gov.nwcg.isuite.core.vo.IapAttachmentVo;
import gov.nwcg.isuite.core.vo.IapBranchCommSummaryVo;
import gov.nwcg.isuite.core.vo.IapBranchPersonnelVo;
import gov.nwcg.isuite.core.vo.IapBranchRscAssignVo;
import gov.nwcg.isuite.core.vo.IapCopyFixVo;
import gov.nwcg.isuite.core.vo.IapCopyVo;
import gov.nwcg.isuite.core.vo.IapForm202Vo;
import gov.nwcg.isuite.core.vo.IapForm203Vo;
import gov.nwcg.isuite.core.vo.IapForm204Vo;
import gov.nwcg.isuite.core.vo.IapForm205Vo;
import gov.nwcg.isuite.core.vo.IapForm206Vo;
import gov.nwcg.isuite.core.vo.IapForm220Vo;
import gov.nwcg.isuite.core.vo.IapFrequencyVo;
import gov.nwcg.isuite.core.vo.IapGridVo;
import gov.nwcg.isuite.core.vo.IapHospitalVo;
import gov.nwcg.isuite.core.vo.IapMedicalAidVo;
import gov.nwcg.isuite.core.vo.IapPersonnelVo;
import gov.nwcg.isuite.core.vo.IapPlanVo;
import gov.nwcg.isuite.core.vo.IapRemoteCampLocationsVo;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class IapCopyServiceImpl extends BaseService implements IapCopyService{
	private IapPlanDao iapPlanDao=null;
	private IapForm202Dao iapForm202Dao=null;
	private IapForm203Dao iapForm203Dao=null;
	private IapBranchDao iapBranchDao=null;
	private IapForm205Dao iapForm205Dao=null;
	private IapForm206Dao iapForm206Dao=null;
	private IapForm220Dao iapForm220Dao=null;
	private IapAttachmentDao iapAttachmentDao=null;

	private Collection<Integer> iapBranchRscAssignFixList = new ArrayList<Integer>();
	private Collection<IapCopyFixVo> iapCopyFixVos;
	
	public IapCopyServiceImpl(){
		super();
	}
	
	public void initialization() {
		iapPlanDao = (IapPlanDao)context.getBean("iapPlanDao");
		iapForm202Dao = (IapForm202Dao)context.getBean("iapForm202Dao");
		iapForm203Dao = (IapForm203Dao)context.getBean("iapForm203Dao");
		iapBranchDao = (IapBranchDao)context.getBean("iapBranchDao");
		iapForm205Dao = (IapForm205Dao)context.getBean("iapForm205Dao");
		iapForm206Dao = (IapForm206Dao)context.getBean("iapForm206Dao");
		iapForm220Dao = (IapForm220Dao)context.getBean("iapForm220Dao");
		iapAttachmentDao = (IapAttachmentDao)context.getBean("iapAttachmentDao");
	}

	public DialogueVo copyForm(IapCopyVo iapCopyVo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
			if(lockedRuleHandler.execute(iapCopyVo.getIapPlanDestinationVo().getId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
				return dialogueVo;
			}
			
			IapCopyFormRulesHandler rulesHandler = new IapCopyFormRulesHandler(context);
			if(rulesHandler.execute(dialogueVo, iapCopyVo)== IapCopyFormRulesHandler._OK){
				// 1. Get the destination plan from the db. 
				IapPlan destinationEntity = iapPlanDao.getById(iapCopyVo.getIapPlanDestinationVo().getId(), IapPlanImpl.class);
				
				// 2. Clone and save the forms that the user wants to clone, using the destination plan as their parent plan
				cloneAndSaveForms(iapCopyVo, destinationEntity, false);
				
				IapGridVo iapGridVo = IapGridVo.getInstance(destinationEntity);
				dialogueVo.setResultObject(iapGridVo);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("COPY_IAP_FORM");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The selected IAP forms were copied."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setCourseOfActionVo(coaVo);
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		return dialogueVo;
	}	
	
	public DialogueVo copyPlan(IapCopyVo iapCopyVo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
			if(lockedRuleHandler.execute(iapCopyVo.getIapPlanSourceVo().getId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
				return dialogueVo;
			}
			
			IapCopyPlanRulesHandler rulesHandler = new IapCopyPlanRulesHandler(context);
			if(rulesHandler.execute(dialogueVo, iapCopyVo)==IapCopyPlanRulesHandler._OK){
				IapPlanVo destinationVo = iapCopyVo.getIapPlanDestinationVo();
				IapPlan destinationEntity = IapPlanVo.toEntity(null, destinationVo, Boolean.TRUE);
				destinationEntity.setIsPlanLocked(StringBooleanEnum.N);
				
				cloneAndSaveForms(iapCopyVo, destinationEntity, true);
				
				IapGridVo iapGridVo = IapGridVo.getInstance(destinationEntity);
				dialogueVo.setResultObject(iapGridVo);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("COPY_IAP_PLAN");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setIsDialogueEnding(true);
				coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP Plan was copied along with the selected forms."}, MessageTypeEnum.INFO));
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	private void cloneAndSaveForms(IapCopyVo iapCopyVo, IapPlan destinationEntity, boolean isCopyPlan) throws Exception {
		// 1. Get the source form from the database.
		// 2. Convert form entity to vo
		// 3. Detach the entity
		// 4. Process the vo - set its and nested objects' ids to null
		// 5. Convert vo back to entity
		// 6. Set form as part of the destination plan
		// 7. Save form... after saving the plan 
		
		//////////////////////////////////////////////////////////////////
		//202
		Collection<Integer> form202Ids = iapCopyVo.getIapForm202Ids();
		Collection<IapForm202> destinationForm202List = getClonedForm202s(form202Ids);

		//203
		Collection<Integer> form203Ids = iapCopyVo.getIapForm203Ids();
		Collection<IapForm203> destinationForm203List = getClonedForm203s(form203Ids);
	
		//204
		Collection<IapForm204Vo> sourceForm204List = iapCopyVo.getIapForm204Vos(); 
		Collection<IapBranch> destinationForm204List = getClonedForm204s(sourceForm204List);
		
		//205
		Collection<Integer> form205Ids = iapCopyVo.getIapForm205Ids(); 
		Collection<IapForm205> destinationForm205List = getClonedForm205s(form205Ids);
		
		//206
		Collection<Integer> form206Ids = iapCopyVo.getIapForm206Ids(); 
		Collection<IapForm206> destinationForm206List = getClonedForm206s(form206Ids);
		
		//220
		Collection<Integer> form220Ids = iapCopyVo.getIapForm220Ids(); 
		Collection<IapForm220> destinationForm220List = getClonedForm220s(form220Ids);
		
		//Attachments
		Collection<IapAttachment> allAttachmentList = new ArrayList<IapAttachment>();
		Collection<IapAttachment> finalAttachmentList = new ArrayList<IapAttachment>();
		Collection<IapAttachmentVo> selectedAttachmentVoList = iapCopyVo.getIapAttachmentVos();
		
		if(isCopyPlan && selectedAttachmentVoList != null && selectedAttachmentVoList.size()>0) { // If user has selected any attachments to copy
			allAttachmentList = getClonedAttachments(iapCopyVo.getIapPlanSourceVo().getId()); // Get list of all attachments for this plan
			for(IapAttachmentVo selectedAttachmentVo: selectedAttachmentVoList){
				if(!"".equals(selectedAttachmentVo.getFilename()) && !"".equals(selectedAttachmentVo.getAttachmentName())){ // Safety 
					for(IapAttachment attachment: allAttachmentList){
						//If BOTH attachmentName and FileName match, then add this attachment to the copied plan
						if(selectedAttachmentVo.getFilename().equals(attachment.getFilename()) && selectedAttachmentVo.getAttachmentName().equals(attachment.getAttachmentName())){
							finalAttachmentList.add(attachment);
							break;
						}
					}
				}
			}
		}
		
		//////////////////////////////////////////////////////////////////
		// Starting saving, beginning with plan and then all the forms.
		
		// Save Plan, only if this is part of a copy plan process
		if(isCopyPlan) {
			iapPlanDao.save(destinationEntity);  
		}

		// defect 5103 Update the preparedDate in the form based on the system date
		String sDiff=((UserSessionVo)this.context.getBean("userSessionVo")).getClientToServerHourDifference();
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.HOUR, Integer.parseInt(sDiff));
		Date sysDate=cal.getTime();
		
		
		//DateUtil.
		// Save all 202
		if(destinationForm202List.size()>0) {
			for(IapForm202 form: destinationForm202List) {
				form.setIapPlan(destinationEntity);
				form.setIapPlanId(destinationEntity.getId());
				form.setApprovedBy(null);	// Reset the approved by since it should be entered by the user
				form.setApprovedDate(null); // Reset the approved date since it should be entered by the user
			}
			iapForm202Dao.saveAll(destinationForm202List);  
			destinationEntity.setIapForm202s(destinationForm202List);
		}
		
		// Save all 203
		if(destinationForm203List.size()>0) {
			for(IapForm203 form: destinationForm203List) {
				form.setIapPlan(destinationEntity);
				form.setIapPlanId(destinationEntity.getId());
				form.setPreparedDate(sysDate);
			}
			iapForm203Dao.saveAll(destinationForm203List); 
			destinationEntity.setIapForm203s(destinationForm203List);
		}

		// Save all 204
		if(destinationForm204List.size()>0) {
			for(IapBranch form: destinationForm204List) {
				form.setIapPlan(destinationEntity);
				form.setIapPlanId(destinationEntity.getId());
				form.setPreparedDate(sysDate);
				for(IapBranchRscAssign ra : form.getIapBranchRscAssigns()){
					ra.setIapBranch(form);
				}
			}
			iapBranchDao.saveAll(destinationForm204List);  
			
			//if(CollectionUtility.hasValue(this.iapBranchRscAssignFixList)){
				IapBranchRscAssignDao rscAssignDao = (IapBranchRscAssignDao)context.getBean("iapBranchRscAssignDao");;
				for(IapBranch b : destinationForm204List){
					if(CollectionUtility.hasValue(this.iapCopyFixVos)){
						for(IapCopyFixVo copyFixVo : this.iapCopyFixVos){
							String copyFixVoBranch=(null==copyFixVo.getBranchName()?"":copyFixVo.getBranchName());
							String bBranch=(null==b.getBranchName()?"":b.getBranchName());
							if(copyFixVoBranch.equals(bBranch)
									&& copyFixVo.getDivisionName().equals(b.getDivisionName())){
								for(Integer pNum : copyFixVo.getPositionNumList()){
									for(IapBranchRscAssign bra : b.getIapBranchRscAssigns()){
										if(bra.getPositionNum().intValue()==pNum){
											bra.setLastDayToWorkDate(null);
											rscAssignDao.save(bra);
										}
									}
								}
								
							}

						}
					}
				}
			//}
			destinationEntity.setIapBranchs(destinationForm204List);
		}
		
		// Save all 205
		if(destinationForm205List.size()>0) {
			for(IapForm205 form: destinationForm205List) {
				form.setIapPlan(destinationEntity);
				form.setIapPlanId(destinationEntity.getId());
				form.setPreparedDate(sysDate);
			}
			iapForm205Dao.saveAll(destinationForm205List);  
			destinationEntity.setIapForm205s(destinationForm205List);
		}
		
		// Save all 206
		if(destinationForm206List.size()>0) {
			for(IapForm206 form: destinationForm206List) {
				form.setIapPlan(destinationEntity);
				form.setIapPlanId(destinationEntity.getId());
				form.setPreparedDate(sysDate);
				form.setReviewedBy(null); 	//Reset the reviewed by since it should be entered by the user
				form.setReviewedDate(null);	//Reset the reviewed date since it should be entered by the user
			}
			iapForm206Dao.saveAll(destinationForm206List);  
			destinationEntity.setIapForm206s(destinationForm206List);
		}
		
		// Save all 220
		if(destinationForm220List.size()>0) {
			for(IapForm220 form: destinationForm220List) {
				form.setIapPlan(destinationEntity);
				form.setIapPlanId(destinationEntity.getId());
				form.setPreparedDate(sysDate);
			}
			iapForm220Dao.saveAll(destinationForm220List);  
			destinationEntity.setIapForm220s(destinationForm220List);
		}
		
		// Save attachments
		if(finalAttachmentList.size()>0){
			for(IapAttachment attachment: finalAttachmentList) {
				attachment.setIapPlan(destinationEntity);
				attachment.setIapPlanId(destinationEntity.getId());
			}
			iapAttachmentDao.saveAll(finalAttachmentList);
			destinationEntity.setIapAttachments(finalAttachmentList);
		}
		
		// Update destination entity
		destinationEntity = iapPlanDao.getById(destinationEntity.getId(), IapPlanImpl.class);
	}
	
	private Collection<IapForm202> getClonedForm202s(Collection<Integer> idList) throws Exception {
		Collection<IapForm202> formEntityList = new ArrayList<IapForm202>();
		
		for(Integer formId: idList) {
			// 1. Get the form from the database
			IapForm202 formEntity = iapForm202Dao.getById(formId.longValue(), IapForm202Impl.class);
			
			if(formEntity!=null){
				// 2. Convert the form entity to form vo
				IapForm202Vo formVo = IapForm202Vo.getInstance(formEntity, Boolean.TRUE);
				iapForm202Dao.flushAndEvict(formEntity); 
				
				// 3. Process the Vo, setting its and nested objects' ids to null
				formVo.setId(null);
				formVo.setIapPlanId(null);
				formVo.setIsFormLocked(Boolean.FALSE);
				
				// 4. Convert the form Vo back to form entity
				formEntity = IapForm202Vo.toEntity(null, formVo, Boolean.TRUE);
				formEntityList.add(formEntity);
			}
		}
		return formEntityList;
	}
	
	private Collection<IapForm203> getClonedForm203s(Collection<Integer> idList) throws Exception {
		Collection<IapForm203> formEntityList = new ArrayList<IapForm203>();
		
		for(Integer formId: idList) {
			// 1. Get the form from the database
			IapForm203 formEntity = iapForm203Dao.getById(formId.longValue(), IapForm203Impl.class);
			
			if(formEntity!=null){
				// 2. Convert the form entity to form vo
				IapForm203Vo formVo = IapForm203Vo.getInstance(formEntity, Boolean.TRUE);
				iapForm203Dao.flushAndEvict(formEntity); 
				
				// 3. Process the Vo, setting its and nested objects' ids to null
				formVo.setId(null);
				formVo.setIapPlanId(null);
				formVo.setIsFormLocked(Boolean.FALSE);
				
				for(IapPersonnelVo iapPersonnelVo: formVo.getIapPersonnelVos()) {
					prepareIapPersonnel(iapPersonnelVo); 
				}
				
				// 4. Convert the form Vo back to form entity
				formEntity = IapForm203Vo.toEntity(null, formVo, Boolean.TRUE);  
				formEntityList.add(formEntity);
			}
		}
		return formEntityList;
	}
	
	private Collection<IapBranch> getClonedForm204s(Collection<IapForm204Vo> sourceFormList) throws Exception {
		Collection<IapBranch> formEntityList = new ArrayList<IapBranch>();
		
		//iapBranchRscAssignFixList = new ArrayList<Integer>();
		this.iapCopyFixVos = new ArrayList<IapCopyFixVo>();
		
		for(IapForm204Vo sourceFormVo: sourceFormList) {
			// 1. Get the form from the database
			IapBranch formEntity = iapBranchDao.getById(sourceFormVo.getId(), IapBranchImpl.class);
			
			
			Collection<IapBranchRscAssign> copies = new ArrayList<IapBranchRscAssign>();
			
			if(formEntity!=null){
				IapCopyFixVo iapCopyFixVo = new IapCopyFixVo();
				iapCopyFixVo.setBranchName(formEntity.getBranchName());
				iapCopyFixVo.setDivisionName(formEntity.getDivisionName());
				
				
				// 2. Convert the form entity to form vo
				IapForm204Vo destinationFormVo = IapForm204Vo.getInstance(formEntity, Boolean.TRUE);
				iapBranchDao.flushAndEvict(formEntity); 
				
				// 3. Process the Vo, setting its and nested objects' ids to null
				destinationFormVo.setId(null);
				destinationFormVo.setIapPlanId(null);
				destinationFormVo.setIsForm204Locked(Boolean.FALSE);
				
				// Special update required for 204s:
				// Reset the form branch and division from what's coming from Flex instead of what's retrieved from the DB
				if(StringUtility.hasValue(sourceFormVo.getBranchName())) {
					destinationFormVo.setBranchName(sourceFormVo.getBranchName());
				}
				
				if(StringUtility.hasValue(sourceFormVo.getDivisionName())) {
					destinationFormVo.setDivisionName(sourceFormVo.getDivisionName());
				}
				
				for(IapBranchCommSummaryVo vo: destinationFormVo.getIapBranchCommSummaryVos()) {
					vo.setId(null);
					vo.setIapBranchId(null);
				}
				
				for(IapBranchPersonnelVo vo: destinationFormVo.getIapBranchPersonnelVos()) {
					prepareIapBranchPersonnel(vo);
				}
				
				copies = IapBranchRscAssignVo.getInstancesForIapCopy(formEntity.getIapBranchRscAssigns(), true);
				for(IapBranchRscAssign rscAssign : copies){
					if(!DateUtil.hasValue(rscAssign.getLastDayToWorkDate())){
						rscAssign.setLastDayToWorkDate(DateUtil.toDate("12/31/1999",DateUtil.MM_SLASH_DD_SLASH_YYYY));
						iapCopyFixVo.getPositionNumList().add(rscAssign.getPositionNum());
						//this.iapBranchRscAssignFixList.add(rscAssign.getPositionNum());
						
					}
				}
				/*
				for(IapBranchRscAssignVo vo: destinationFormVo.getIapBranchRscAssignVos()) {
					vo.setId(null);
					vo.setIapBranchId(null);
					
					if(null != vo.getLastDayToWorkDateVo()){
						String dt =vo.getLastDayToWorkDateVo().getDateString();
						if(StringUtility.hasValue(dt)){
							vo.setLastDayToWorkDateVo(new DateTransferVo());
							vo.getLastDayToWorkDateVo().setDateString(dt);
							vo.getLastDayToWorkDateVo().setTimeString("0000");
						}
					}
					
					if(null == vo.getLastDayToWorkDateVo() || BooleanUtility.isTrue(vo.getIsBlankLine())){
						vo.setLastDayToWorkDateVo(new DateTransferVo());
						vo.getLastDayToWorkDateVo().setDateString("12/31/1999");
						vo.getLastDayToWorkDateVo().setTimeString("0000");
						this.iapBranchRscAssignFixList.add(vo.getPositionNum());
					}
				}
				*/
				
				// 4. Convert the form Vo back to form entity
				formEntity = IapForm204Vo.toEntity(null, destinationFormVo, Boolean.TRUE);  
				formEntity.setIapBranchRscAssigns(new ArrayList<IapBranchRscAssign>());
				//System.out.println(formEntity.getIapBranchRscAssigns().size());
				formEntity.getIapBranchRscAssigns().addAll(copies);
				formEntityList.add(formEntity);
				
				this.iapCopyFixVos.add(iapCopyFixVo);
			}
		}
		return formEntityList;
	}
	
	private Collection<IapForm205> getClonedForm205s(Collection<Integer> idList) throws Exception {
		Collection<IapForm205> formEntityList = new ArrayList<IapForm205>();
		
		for(Integer formId: idList) {
			// 1. Get the form from the database
			IapForm205 formEntity = iapForm205Dao.getById(formId.longValue(), IapForm205Impl.class);
			
			if(formEntity!=null){
				// 2. Convert the form entity to form vo
				IapForm205Vo formVo = IapForm205Vo.getInstance(formEntity, Boolean.TRUE);
				iapForm205Dao.flushAndEvict(formEntity); 
				
				// 3. Process the Vo, setting its and nested objects' ids to null
				formVo.setId(null);
				formVo.setIapPlanId(null);
				formVo.setIsFormLocked(Boolean.FALSE);
				
				for(IapFrequencyVo vo: formVo.getIapFrequencieVos()) {
					vo.setId(null);
					vo.setIapForm205Id(null);
					// Do not reset master frequencey id in this vo
				}
				
				// 4. Convert the form Vo back to form entity
				formEntity = IapForm205Vo.toEntity(null, formVo, Boolean.TRUE);  
				formEntityList.add(formEntity);
			}
		}
		return formEntityList;
	}
	
	private Collection<IapForm206> getClonedForm206s(Collection<Integer> idList) throws Exception {
		Collection<IapForm206> formEntityList = new ArrayList<IapForm206>();
		
		for(Integer formId: idList) {
			// 1. Get the form from the database
			IapForm206 formEntity = iapForm206Dao.getById(formId.longValue(), IapForm206Impl.class);
			
			if(formEntity!=null){
				// 2. Convert the form entity to form vo
				IapForm206Vo formVo = IapForm206Vo.getInstance(formEntity, Boolean.TRUE);
				iapForm206Dao.flushAndEvict(formEntity); 
				
				// 3. Process the Vo, setting its and nested objects' ids to null
				formVo.setId(null);
				formVo.setIapPlanId(null);
				formVo.setIsFormLocked(Boolean.FALSE);
				
				for(IapMedicalAidVo vo: formVo.getIapAmbulanceVos()) {
					prepareMedicalAid(vo);
				}
				
				for(IapMedicalAidVo vo: formVo.getIapAirAmbulanceVos()) {
					prepareMedicalAid(vo);
				}
				
				for(IapHospitalVo vo: formVo.getIapHospitalVos()) {
					vo.setId(null);
					vo.setIapForm206Id(null);
					if (vo.getAddressVo()!=null) {
						vo.getAddressVo().setId(null);
					}
				}
				
				for(IapAreaLocationCapabilityVo vo: formVo.getIapAreaLocationCapabilityVos()) {
					vo.setId(null);
					vo.setIapForm206Id(null);
				}
				
				for(IapRemoteCampLocationsVo vo: formVo.getIapRemoteCampLocationsVos()) {
					vo.setId(null);
					vo.setIapForm206Id(null);
				}
				
				// 4. Convert the form Vo back to form entity
				formEntity = IapForm206Vo.toEntity(null, formVo, Boolean.TRUE);  
				formEntityList.add(formEntity);
			}
		}
		return formEntityList;
	}
	
	private Collection<IapForm220> getClonedForm220s(Collection<Integer> idList) throws Exception {
		Collection<IapForm220> formEntityList = new ArrayList<IapForm220>();
		
		for(Integer formId: idList) {
			// 1. Get the form from the database
			IapForm220 formEntity = iapForm220Dao.getById(formId.longValue(), IapForm220Impl.class);
			
			if(formEntity!=null){
				// 2. Convert the form entity to form vo
				IapForm220Vo formVo = IapForm220Vo.getInstance(formEntity, Boolean.TRUE);
				iapForm220Dao.flushAndEvict(formEntity); 
				
				// 3. Process the Vo, setting its and nested objects' ids to null
				formVo.setId(null);
				formVo.setIapPlanId(null);
				formVo.setIsFormLocked(Boolean.FALSE);
				
				for(IapAircraftVo vo: formVo.getIapAircraftVos()) {
					vo.setId(null);
					vo.setIapForm220Id(null);
				}
				
				for(IapAircraftVo vo: formVo.getIapFixedWingVos()) {
					vo.setId(null);
					vo.setIapForm220Id(null);
				}
				
				for(IapAircraftVo vo: formVo.getIapHelicopterVos()) {
					vo.setId(null);
					vo.setIapForm220Id(null);
				}
				
				for(IapAircraftFrequencyVo vo: formVo.getIapAircraftFrequencyVos()) {
					vo.setId(null);
					vo.setIapForm220Id(null);
				}
				
				for(IapAircraftTaskVo vo: formVo.getIapAircraftTaskVos()) {
					vo.setId(null);
					vo.setIapForm220Id(null);
				}
				
				for(IapAircraftTaskVo vo: formVo.getIapAircraftTaskVos()) {
					vo.setId(null);
					vo.setIapForm220Id(null);
				}
				
				for(IapPersonnelVo iapPersonnelVo: formVo.getIapPersonnelVos()) {
					prepareIapPersonnel(iapPersonnelVo); 
				}

				// 4. Convert the form Vo back to form entity
				formEntity = IapForm220Vo.toEntity(null, formVo, Boolean.TRUE);  
				formEntityList.add(formEntity);
			}
		}
		return formEntityList;
	}
	
	private Collection<IapAttachment> getClonedAttachments(Long iapPlanId) throws Exception {
		Collection<IapAttachment> iapAttachmentList = new ArrayList<IapAttachment>();
		
		Collection<IapAttachment>iapAttachments = iapAttachmentDao.getByPlanId(iapPlanId);
		
		for(IapAttachment iapAttachment : iapAttachments) {
			IapAttachmentVo attachmentVo = IapAttachmentVo.getInstance(iapAttachment, Boolean.TRUE);
			iapAttachmentDao.flushAndEvict(iapAttachment);
			
			attachmentVo.setId(null);
			attachmentVo.setIapPlanId(null);
			
			iapAttachment = IapAttachmentVo.toEntity(null, attachmentVo, Boolean.TRUE);
			iapAttachmentList.add(iapAttachment);
		}
		
		return iapAttachmentList;
	}
	
	private void prepareIapPersonnel(IapPersonnelVo iapPersonnelOuter) throws Exception {
		for(IapPersonnelVo iapPersonnelVo: iapPersonnelOuter.getBranchPersonnelVos()) {
			prepareIapPersonnel(iapPersonnelVo);
		}
		
		iapPersonnelOuter.setId(null);
		iapPersonnelOuter.setIapBranchPersonnelParentId(null);
		iapPersonnelOuter.setIapForm203Id(null);
		iapPersonnelOuter.setIapForm220Id(null);
		
		if(iapPersonnelOuter.getIapPersonnelResVo1()!=null){
			iapPersonnelOuter.getIapPersonnelResVo1().setId(null);
			iapPersonnelOuter.getIapPersonnelResVo1().setIapPersonnelId(null);
		}
		
		if(iapPersonnelOuter.getIapPersonnelResVo2()!=null){
			iapPersonnelOuter.getIapPersonnelResVo2().setId(null);
			iapPersonnelOuter.getIapPersonnelResVo2().setIapPersonnelId(null);
		}
	}
	
	private void prepareIapBranchPersonnel(IapBranchPersonnelVo vo) throws Exception {
		vo.setId(null);
		vo.setIapBranchId(null);
		
		if(vo.getIapBranchPersonnelResVo1()!=null){
			vo.getIapBranchPersonnelResVo1().setId(null);
			vo.getIapBranchPersonnelResVo1().setIapBranchPersonnelId(null);
		}
		
		if(vo.getIapBranchPersonnelResVo2()!=null){
			vo.getIapBranchPersonnelResVo2().setId(null);
			vo.getIapBranchPersonnelResVo2().setIapBranchPersonnelId(null);
		}
	}
	
	private void prepareMedicalAid(IapMedicalAidVo vo) throws Exception {
		vo.setId(null);
		vo.setIapForm206Id(null);
		if (vo.getAddressVo()!=null) {
			vo.getAddressVo().setId(null);
		}
	}
}
