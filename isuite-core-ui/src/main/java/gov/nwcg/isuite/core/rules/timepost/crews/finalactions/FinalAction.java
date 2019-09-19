package gov.nwcg.isuite.core.rules.timepost.crews.finalactions;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.filter.impl.TimePostQueryFilterImpl;
import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DecimalUtil;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.Collection;

import org.springframework.context.ApplicationContext;


/*
 * This class handles the situation where the time post being saved
 * is a regular posting, no additional actions needed.
 */
public class FinalAction extends AbstractCrewFinalAction implements ICrewFinalAction{
	
	public FinalAction(ApplicationContext ctx,int ccount){
		super(ctx);
		super.crewCount=ccount;
	} 

	
	public int saveCrewPosting(AssignmentTime atEntity,AssignmentTimePostVo vo,DialogueVo dialogueVo) throws Exception {
		
		if(atEntity.getId().compareTo(10942L)==0){
			System.out.println("");
		}
		AssignmentTimePostVo vo2 = vo.clone();
		Collection<Long> updateIds = null;
		
		/*
		 * if vo has id, then time is being editted.
		 * if vo.atid != atEtnity.id, try to find the one to update 
		 * for the atentity
		 */
		if(LongUtility.hasValue(vo.getId())){
			if(vo.getAssignmentTimeId().compareTo(atEntity.getId()) != 0){
				TimePostQueryFilterImpl filter = new TimePostQueryFilterImpl();
				filter.setStartDate(originalVo.getPostStartDate());
				filter.setStopDate(originalVo.getPostStopDate());
				filter.setAssignmentTimeId(atEntity.getId());
				updateIds = tpDao.getPostByFilter(filter);
			}
			
		}
		
		AssignmentTimePostVo atpvo=null;
		vo2.setPrimaryPosting(true);

		atpvo = vo2.clone();
		atpvo.setPostStopDate(atpvo.getPostStartDate());

		super.verifySpecialCodes(atEntity, atpvo);
		super.verifyRateInfo(atEntity, atpvo);
		
		// if atEntity is for the original vo, do not override kindcode
		if(vo.getAssignmentTimeId().compareTo(atEntity.getId()) != 0)
			super.verifyItemCode(atEntity, atpvo);

		atpvo.setAssignmentTimeId(atEntity.getId());
		atpvo.setEmploymentType(atEntity.getEmploymentType());
		
		if(CollectionUtility.hasValue(updateIds)){
			for(Long id : updateIds){
				atpvo.setId(id);
				vosToSave.add(atpvo);
			}
		}else{
			/*
			 * Check if saving an existing one and for different person?
			 * if yes, set id to null
			 */
			if(LongUtility.hasValue(atpvo.getId())){
				if(vo.getAssignmentTimeId().compareTo(atEntity.getId())!=0)
					atpvo.setId(null);
				
			}

			vosToSave.add(atpvo);
		}
		
		saveTimePosts(vosToSave,dialogueVo);
		
		return AbstractRule._OK;
	}
}
