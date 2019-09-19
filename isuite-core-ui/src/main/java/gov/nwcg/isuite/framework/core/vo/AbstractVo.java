package gov.nwcg.isuite.framework.core.vo;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;



/**
 * Represents an abstract base vo object
 * 
 * @author bsteiner
 */
@JsonAutoDetect(fieldVisibility = Visibility.PUBLIC_ONLY)
public abstract class AbstractVo {

	protected Long id;
	private String userLoginName;
	private AuditableVo auditableVo=new AuditableVo();
	private String sourcePage="";

	private String tempString1;
	
	public AbstractVo() {}

	public AbstractVo(Persistable entity) {
		if (null != entity) {
			this.setId(entity.getId());
			this.auditableVo = new AuditableVo(entity);
		}
	}

	protected void setAuditableVo(AuditableVo vo){
		auditableVo=vo;
	}

	/**
	 * Returns the id.
	 * 
	 * @return
	 * 		the id to return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 * 			the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns the user login name.
	 * 
	 * @return 
	 * 		the userLoginName to return
	 */
	public String getUserLoginName() {
		return userLoginName;
	}

	/**
	 * Sets the user login name.
	 * 
	 * @param userLoginName 
	 * 				the userLoginName to set
	 */
	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}

	/**
	 * Sets the created by user.
	 * 
	 * @param val
	 * 			the created by user to set
	 */
	public void setCreatedBy(String val){
		auditableVo.setCreatedBy(val);
	}

	/**
	 * Returns the created by user.
	 * 
	 * @return
	 * 		the created by user to return
	 */
	public String getCreatedBy(){
		return auditableVo.getCreatedBy();
	}

	/**
	 * Sets the created date.
	 * 
	 * @param dt
	 * 		the created date to set
	 */
	public void setCreatedDate(Date dt){
		auditableVo.setCreatedDate(dt);
	}

	/**
	 * Convenience method used in sql transformation to get around hibernate
	 * result filter mapping issue.
	 * 
	 * @param createdDate
	 * 			the created date to set
	 */
	public void setAltCreatedDate(Date createdDate) {
		setCreatedDate(createdDate);
	}

	/**
	 * Returns the created date.
	 * 
	 * @return
	 * 		the created date to return
	 */
	public Date getCreatedDate(){
		return auditableVo.getCreatedDate();
	}

	/**
	 * Constructs a <code>String</code> with all attributes
	 * in name = value format.
	 *
	 * @return a <code>String</code> representation 
	 * of this object.
	 */
	@Override
	public String toString()
	{
		final String nl = System.getProperty("line.separator");

		StringBuffer retValue = new StringBuffer();
		String tab = "\t";

		retValue
		.append(tab).append("<id>").append(this.id).append("</id>").append(nl)
		.append(tab).append("<userLoginName>").append(this.userLoginName).append("</userLoginName>").append(nl);

		return retValue.toString();
	}

	protected static Persistable getPersistableObject(Persistable[] persistableObjects, Class persistableType){
		if(null != persistableObjects){
			for(Persistable persistableObject : persistableObjects){
				if(null != persistableObject){
					//System.out.println(persistableObject.getClass().getSimpleName());
					if(persistableObject.getClass().getSimpleName().equals(persistableType.getSimpleName())){
						return persistableObject;
					}else if(persistableObject.getClass().getSimpleName().startsWith(persistableType.getSimpleName()) ){
						return persistableObject;
					}
				}
			}
		}
		return null;
	}

	protected static List<? extends Object> getPersistableObjectList(Persistable[] persistableObjects, Class persistableType){
		List<Persistable> list = new ArrayList<Persistable>();
		for(Persistable persistableObject : persistableObjects){
			if(persistableObject.getClass().getSimpleName().equals(persistableType.getSimpleName())){
				list.add(persistableObject);
			}
		}

		return list;
	}

	public static Collection<Long> toIds(Collection<? extends AbstractVo> vos) throws Exception{
		Collection<Long> ids = new ArrayList<Long>();
		try{
			for(Object vo : vos){
				ids.add(((AbstractVo)vo).getId());
			}
		}
		catch (Exception e){
			System.out.println(e);
		}
		
		return ids;
	}

	/**
	 * Returns true if the vo has had no properties set.
	 * NOTE: this should be overridden by subclasses.
	 * 
	 * @return
	 * 		whether the vo has had any properties set.
	 */
	@JsonIgnore
	public Boolean isEmpty() {
		return false;
	}

	public static Boolean hasValue(AbstractVo vo){
		if(null != vo && LongUtility.hasValue(vo.getId()))
			return true;
		else
			return false;
	}

	@Deprecated
	public Persistable toEntity(Persistable entity) throws Exception{
		return null;
	}

	public String getSourcePage() {
		return sourcePage;
	}

	public void setSourcePage(String sourcePage) {
		this.sourcePage = sourcePage;
	}

	/**
	 * @return the tempString1
	 */
	public String getTempString1() {
		return tempString1;
	}

	/**
	 * @param tempString1 the tempString1 to set
	 */
	public void setTempString1(String tempString1) {
		this.tempString1 = tempString1;
	}
}
