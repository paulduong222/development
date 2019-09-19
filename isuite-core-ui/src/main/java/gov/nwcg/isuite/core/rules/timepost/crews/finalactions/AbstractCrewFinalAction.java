package gov.nwcg.isuite.core.rules.timepost.crews.finalactions;

import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.RateClassRate;
import gov.nwcg.isuite.core.domain.impl.AssignmentImpl;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimePostImpl;
import gov.nwcg.isuite.core.domain.impl.RateClassRateImpl;
import gov.nwcg.isuite.core.persistence.AssignmentDao;
import gov.nwcg.isuite.core.persistence.AssignmentTimeDao;
import gov.nwcg.isuite.core.persistence.KindDao;
import gov.nwcg.isuite.core.persistence.RateClassRateDao;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.RateClassRateVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;
import gov.nwcg.isuite.framework.util.BigDecimalUtility;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

@SuppressWarnings("unchecked")
public class AbstractCrewFinalAction {
	protected Collection<AssignmentTimePostVo> vosToSave = new ArrayList<AssignmentTimePostVo>();
	protected AssignmentTimePost firstEntity=null;
	protected TimePostDao tpDao = null;
	protected KindDao kindDao=null;
	
	protected ApplicationContext context=null;
	protected AssignmentDao assignmentDao=null;
	protected AssignmentTimePostVo originalVo;
	protected int memberIndex=1;
	
	protected int crewCount=0;
	
	protected enum SpecialPayRuleEnum {
		C_HF(1L,false,true,false)
		,C_IN(2L,false,true,false)
		,COP(3L,true,true,true)
		,C_TR(4L,false,true,false)
		,DAYOFF(5L,true,true,true)
		,EP100(6L,true,false,false)
		,EP15(7L,true,false,false)
		,EP25(8L,true,false,false)
		,EP4(9L,true,false,false)
		,EP8(10L,true,false,false)
		,GUAR(11L,true,true,true)
		,HP(12L,true,false,true)
		,TVL(13L,true,true,true)
		;
		
		public Boolean isFed=false;
		public Boolean isAd=false;
		public Boolean isOther=false;
		public Long id;
		
		SpecialPayRuleEnum(Long id,Boolean isFed, Boolean isAd, Boolean isOther){
			this.id=id;
			this.isFed=isFed;
			this.isAd=isAd;
			this.isOther=isOther;
		}
		
		static SpecialPayRuleEnum getEnumById(Long spId){
			for(SpecialPayRuleEnum re : SpecialPayRuleEnum.values()){
				if(re.id.compareTo(spId)==0)
					return re;
			}
			return null;
		}
		
		static SpecialPayRuleEnum getEnumByCode(String code){
			for(SpecialPayRuleEnum re : SpecialPayRuleEnum.values()){
				String reName=re.name().replace("_", "-");
				
				if(reName.equals(code))
					return re;
				
				if(code.equals("DAY OFF")){
					if(reName.equals("DAYOFF"))
						return re;
				}
			}
			return null;
		}
	}
	
	public AbstractCrewFinalAction(ApplicationContext ctx){
		this.context=ctx;
	}
	
	public void setOriginalVo(AssignmentTimePostVo vo){
		this.originalVo=vo;
	}

	public void setTimePostDao(TimePostDao dao){
		this.tpDao=dao;
	}
	
	public AssignmentTimePost getFirstEntity() {
		return firstEntity;
	}
	
	/**
	 * Verify the special code value is valid for the resource employment type.
	 * 
	 * @param atEntity
	 * @param atpvo
	 * @throws Exception
	 */
	protected void verifySpecialCodes(AssignmentTime atEntity, AssignmentTimePostVo atpvo) throws Exception {

		if(null != atpvo.getSpecialPayVo()){

			SpecialPayRuleEnum sprEnum = SpecialPayRuleEnum.getEnumByCode(atpvo.getSpecialPayVo().getCode());
			
			if(null == sprEnum)
				return;
			
			if(atEntity.getEmploymentType()==EmploymentTypeEnum.FED){
				if(sprEnum.name().equals("COP"))
					atpvo.setQuantity(new BigDecimal(0));
				else if(sprEnum.name().equals("DAYOFF"))
					atpvo.setQuantity(new BigDecimal(0));
				else if(sprEnum.name().equals("GUAR"))
					atpvo.setQuantity(new BigDecimal(0));
				else{
					if(!sprEnum.isFed){
						atpvo.setSpecialPayVo(null);
					}
				}
			}else if(atEntity.getEmploymentType()==EmploymentTypeEnum.AD){
				
				if(sprEnum.name().equals("EP4"))
					atpvo.setSpecialPayVo(null);
				else if(sprEnum.name().equals("EP8"))
					atpvo.setSpecialPayVo(null);
				else if(sprEnum.name().equals("EP25"))
					atpvo.setSpecialPayVo(null);
				else if(sprEnum.name().equals("EP100"))
					atpvo.setSpecialPayVo(null);
				else{
					if(!sprEnum.isAd){
						atpvo.setSpecialPayVo(null);
					}
				}

				if("COP DAYOFF DAY OFF GUAR".indexOf(sprEnum.name())>-1){
					//System.out.println(atpvo.getQuantity());
					//atpvo.setQuantity(new BigDecimal(0));
				}
				
			}else if(atEntity.getEmploymentType()==EmploymentTypeEnum.OTHER){
				
				if(sprEnum.name().equals("COP"))
					atpvo.setQuantity(new BigDecimal(0));
				else if(sprEnum.name().equals("DAYOFF"))
					atpvo.setQuantity(new BigDecimal(0));
				else if(sprEnum.name().equals("GUAR"))
					atpvo.setQuantity(new BigDecimal(0));
				else{
					if(!sprEnum.isOther){
						atpvo.setSpecialPayVo(null);
					}
				}
			}

		}
	}
	
	protected void verifyItemCode(AssignmentTime atEntity, AssignmentTimePostVo atpvo) throws Exception {

		if(null==this.assignmentDao)
			assignmentDao=(AssignmentDao)context.getBean("assignmentDao");

		Assignment assignment = assignmentDao.getById(atEntity.getAssignmentId(), AssignmentImpl.class);
		if(null!=assignment){
			KindVo kindVo = new KindVo();
			kindVo.setId(assignment.getKind().getId());
			atpvo.setKindVo(kindVo);
		}
		
		
	}
	
	/**
	 * Verify the rate information is set correctly based on employment type.
	 * 
	 * @param atEntity
	 * @param atpvo
	 * @throws Exception
	 */
	protected void verifyRateInfo(AssignmentTime atEntity, AssignmentTimePostVo atpvo) throws Exception {

		if(atEntity.getEmploymentType()==EmploymentTypeEnum.FED){
			/*
			 * For Fed, force the rate amount and rate class are null
			 */
			atpvo.setRateAmount(new BigDecimal(0));
			atpvo.setRateClassRateVo(null);
			atpvo.setEmploymentType(EmploymentTypeEnum.FED);
		}else if(atEntity.getEmploymentType()==EmploymentTypeEnum.AD){
			/*
			 * For Ad, make sure we have a rate amount and rate class.
			 * If we do not, default to the resource's default values.
			 */
			RateClassRateDao rcrDao = (RateClassRateDao)context.getBean("rateClassRateDao");
			RateClassRate rcrEntity=rcrDao.getById(atEntity.getAdPaymentInfo().getRateClassRateId(), RateClassRateImpl.class);
			
			atpvo.setRateClassRateVo(RateClassRateVo.getInstance(rcrEntity,true));
			atpvo.setRateAmount(rcrEntity.getRate());
			atpvo.setEmploymentType(EmploymentTypeEnum.AD);

			/*
			if(null == atpvo.getRateClassRateVo()){
				// use resources default values
				atpvo.setRateClassRateVo(RateClassRateVo.getInstance(atEntity.getAdPaymentInfo().getRateClassRate(),true));
			}
			if(this.memberIndex > 1){
				atpvo.setRateClassRateVo(RateClassRateVo.getInstance(atEntity.getAdPaymentInfo().getRateClassRate(),true));
				atpvo.setRateAmount(atEntity.getAdPaymentInfo().getRateClassRate().getRate());
			}else{
				if(!BigDecimalUtility.hasValue(atpvo.getRateAmount())){
					atpvo.setRateAmount(atEntity.getAdPaymentInfo().getRateClassRate().getRate());
				}
			}
			*/
		}else if(atEntity.getEmploymentType()==EmploymentTypeEnum.OTHER){
			/*
			 * For Other, make sure we have a rate amount.
			 * If not, default to resources default other amount value
			 */
			//if(!BigDecimalUtility.hasValue(atpvo.getRateAmount())){

				// if more than one crew memmber is selected,
				// then default to the resource's other rate amount
				// if a single crew memmber is selected,
				// then use the amount the user inputted
			    if(crewCount > 1){
			    	atpvo.setRateAmount(atEntity.getOtherDefaultRate());
			    	atpvo.setOtherRate(atpvo.getRateAmount());
			    }else{
			    	//atpvo.setOtherRate(atpvo.getRateAmount());
			    	atpvo.setRateAmount(atpvo.getOtherRate());
			    }
				atpvo.setEmploymentType(EmploymentTypeEnum.OTHER);
				atpvo.setRateClassRateVo(null);
			//}
		}
	}
	
	protected void saveTimePosts(Collection<AssignmentTimePostVo> vos,DialogueVo dialogueVo) throws Exception {
		int x=0;
		
		for(AssignmentTimePostVo v : vos){
			AssignmentTimePost entity = null;
			
			if(LongUtility.hasValue(v.getId())){
				entity = tpDao.getById(v.getId(), AssignmentTimePostImpl.class);
			}
			
			// fix poststopdate/time for returntravelonly
			if(BooleanUtility.isTrue(v.getReturnTravelStartOnly()) 
					&& !StringUtility.hasValue(v.getPostStopTime())){
				v.setPostStopDate(null);
			}
				
			entity = AssignmentTimePostVo.toEntity(entity, v, true);
			
			tpDao.save(entity);
			
			if(x==0)
				firstEntity=entity;
			if(null != entity.getSpecialPay())
				tpDao.flushAndEvict(entity.getSpecialPay());
			tpDao.flushAndEvict(entity);
			entity = tpDao.getById(entity.getId(), AssignmentTimePostImpl.class);
			AssignmentTimePostVo newvo = AssignmentTimePostVo.getInstance(entity, true);
			newvo.setAssignmentTimeId(entity.getAssignmentTime().getId());
			((Collection<AssignmentTimePostVo>)dialogueVo.getResultObjectAlternate3()).add(newvo);
			x++;
		}
		
	}

	protected CourseOfActionVo getCoa(String name, DialogueVo dialogueVo) throws Exception {
		
		for(CourseOfActionVo coaVo : dialogueVo.getProcessedCourseOfActionVos()){
			if(coaVo.getCoaName().equals(name)){
				return coaVo;
			}
		}
		
		return null;
	}

	public void setMemberIndex(int i){
		this.memberIndex=i;
	}
	
}
