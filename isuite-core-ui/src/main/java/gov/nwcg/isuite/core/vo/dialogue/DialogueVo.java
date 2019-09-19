package gov.nwcg.isuite.core.vo.dialogue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DialogueVo {

	//the type property of the ServiceCallEvent.as
	private String serviceCallEventType;
	
	// current courseofaction object in dialogue
	private CourseOfActionVo courseOfActionVo=null;
	
	private Collection<CourseOfActionVo> supplementalCoursesOfAction = new ArrayList<CourseOfActionVo>();
	
	// processed courses of action in dialogue
	private Collection<CourseOfActionVo> processedCourseOfActionVos= new ArrayList<CourseOfActionVo>();

	// records received from and returned to the client
	private Collection<? extends Object> recordset;
	
	/*
	 * Dialogue resulting object.
	 * 
	 * Corresponds to the ui data.result object.
	 * Ui side will need to type cast this object into 
	 * the expected resulting object.
	 */
	private Object resultObject=null;
	
	/*
	 * Alternate resultobject , use as needed
	 */
	private Object resultObjectAlternate=null;
	private Object resultObjectAlternate2=null;
	private Object resultObjectAlternate3=null;
	private Object resultObjectAlternate4=null;
	
	public DialogueVo(){
		
	}

	public CourseOfActionVo getCourseOfActionVo() {
		return courseOfActionVo;
	}

	public void setCourseOfActionVo(CourseOfActionVo courseOfActionVo) {
		this.courseOfActionVo = courseOfActionVo;
	}

	public Collection<CourseOfActionVo> getProcessedCourseOfActionVos() {
		return processedCourseOfActionVos;
	}

	public void setProcessedCourseOfActionVos(
			Collection<CourseOfActionVo> processedCourseOfActionVos) {
		this.processedCourseOfActionVos = processedCourseOfActionVos;
	}

	public Object getResultObject() {
		return resultObject;
	}

	public void setResultObject(Object resultObject) {
		this.resultObject = resultObject;
	}

	/**
	 * @return the serviceCallEventType
	 */
	public String getServiceCallEventType() {
		return serviceCallEventType;
	}

	/**
	 * @param serviceCallEventType the serviceCallEventType to set
	 */
	public void setServiceCallEventType(String serviceCallEventType) {
		this.serviceCallEventType = serviceCallEventType;
	}

	/**
	 * @return the recordset
	 */
	public Collection<? extends Object> getRecordset() {
		return recordset;
	}

	/**
	 * @param recordset the recordset to set
	 */
	public void setRecordset(Collection<? extends Object> recordset) {
		this.recordset = recordset;
	}
	
	@JsonIgnore
	public CourseOfActionVo getCourseOfActionByName(String coaName) {
		
		if((null != this.courseOfActionVo) && (this.courseOfActionVo.getCoaName().equals(coaName))) {
			return this.courseOfActionVo;
		}
		
		Iterator<CourseOfActionVo> itr = this.processedCourseOfActionVos.iterator();
		
		while(itr.hasNext()) {
			CourseOfActionVo coa = itr.next();
			if(coa.getCoaName().equals(coaName)) {
				return coa;
			}
		}
		
		return null;
	}

	@JsonIgnore
	public Boolean hasCourseOfActionByName(String coaName) {
		
		Iterator<CourseOfActionVo> itr = this.processedCourseOfActionVos.iterator();
		
		while(itr.hasNext()) {
			CourseOfActionVo coa = itr.next();
			if(coa.getCoaName().equals(coaName)) {
				return true;
			}
		}
		
		return false;
	}

	public static void updateCourseOfAction(DialogueVo dialogueVo, CourseOfActionVo coaVo){
		
		Collection<CourseOfActionVo> processedVos = dialogueVo.getProcessedCourseOfActionVos();
		Collection<CourseOfActionVo> vos = new ArrayList<CourseOfActionVo>();
		
		for(CourseOfActionVo vo : processedVos){
			if(vo.getCoaName().equals(coaVo.getCoaName()))
				vos.add(coaVo);
			else
				vos.add(vo);
		}
		
		dialogueVo.setProcessedCourseOfActionVos(vos);
	}
	
	public void setSupplementalCoursesOfAction(
			Collection<CourseOfActionVo> supplementalCoursesOfAction) {
		this.supplementalCoursesOfAction = supplementalCoursesOfAction;
	}

	public Collection<CourseOfActionVo> getSupplementalCoursesOfAction() {
		return supplementalCoursesOfAction;
	}

	@JsonIgnore
	public void addSupplementalCourseOfActionVo(CourseOfActionVo coa) {
	
		if(null == this.supplementalCoursesOfAction) {
			this.supplementalCoursesOfAction = new ArrayList<CourseOfActionVo>();
		}
		
		this.supplementalCoursesOfAction.add(coa);
	}

	/**
	 * @return the resultObjectAlternate
	 */
	public Object getResultObjectAlternate() {
		return resultObjectAlternate;
	}

	/**
	 * @param resultObjectAlternate the resultObjectAlternate to set
	 */
	public void setResultObjectAlternate(Object resultObjectAlternate) {
		this.resultObjectAlternate = resultObjectAlternate;
	}

	public Object getResultObjectAlternate2() {
		return resultObjectAlternate2;
	}

	public void setResultObjectAlternate2(Object resultObjectAlternate2) {
		this.resultObjectAlternate2 = resultObjectAlternate2;
	}

	public Object getResultObjectAlternate3() {
		return resultObjectAlternate3;
	}

	public void setResultObjectAlternate3(Object resultObjectAlternate3) {
		this.resultObjectAlternate3 = resultObjectAlternate3;
	}

	/**
	 * @return the resultObjectAlternate4
	 */
	public Object getResultObjectAlternate4() {
		return resultObjectAlternate4;
	}

	/**
	 * @param resultObjectAlternate4 the resultObjectAlternate4 to set
	 */
	public void setResultObjectAlternate4(Object resultObjectAlternate4) {
		this.resultObjectAlternate4 = resultObjectAlternate4;
	}
}
