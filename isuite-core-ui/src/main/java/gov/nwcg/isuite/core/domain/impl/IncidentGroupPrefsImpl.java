
package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.IncidentGroupPrefs;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.IncidentPrefsSectionNameEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@SequenceGenerator(name="SEQ_INCIDENT_GROUP_PREFS", sequenceName="SEQ_INCIDENT_GROUP_PREFS")
@Table(name="isw_incident_group_prefs")
public class IncidentGroupPrefsImpl extends PersistableImpl implements IncidentGroupPrefs {

   @Id
   @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_INCIDENT_GROUP_PREFS")
   private Long id = 0L;
   
   @OneToOne(targetEntity=IncidentGroupImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name = "INCIDENT_GROUP_ID", insertable = true, updatable = true, unique = false, nullable = false)
   private IncidentGroup incidentGroup;
   
   @Column(name="INCIDENT_GROUP_ID", insertable = false, updatable = false, nullable = false)
   private Long incidentGroupId;
   
   @Column(name = "SECTION_NAME", length = 30, nullable = false)
   @Enumerated(EnumType.STRING)
   private IncidentPrefsSectionNameEnum sectionName;
   
   @Column(name="FIELD_LABEL", length=30)
   private String fieldLabel;
   
   @Column(name = "POSITION", nullable = false)
   private Integer position;

   @Column(name="IS_SELECTED")
   private Boolean selected;
   
   /**
    * Default Constructor
    */
   public IncidentGroupPrefsImpl() {
      super();
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl#getId()
    */
   public Long getId() {
      return this.id;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl#setId(java.lang.Long)
    */
   public void setId(Long id) {
      this.id = id;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IncidentPrefs#getFieldLabel()
    */
   public String getFieldLabel() {
      return fieldLabel;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IncidentPrefs#getIncidentGroup()
    */
   public IncidentGroup getIncidentGroup() {
      return incidentGroup;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IncidentPrefs#getIncidentGroupId()
    */
   public Long getIncidentGroupId() {
      return incidentGroupId;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IncidentPrefs#getPosition()
    */
   public Integer getPosition() {
      return position;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IncidentPrefs#getSectionName()
    */
   public IncidentPrefsSectionNameEnum getSectionName() {
      return sectionName;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IncidentPrefs#isSelected()
    */
   public Boolean isSelected() {
      return selected;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IncidentPrefs#setFieldLabel(java.lang.String)
    */
   public void setFieldLabel(String fieldLabel) {
      this.fieldLabel = fieldLabel;
   }

   public void setIncidentGroup(IncidentGroup incidentGroup) {
      this.incidentGroup = incidentGroup;
   }

   public void setIncidentGroupId(Long incidentGroupId) {
      this.incidentGroupId = incidentGroupId;
   }

   public void setPosition(Integer position) {
      this.position = position;
   }

   public void setSectionName(IncidentPrefsSectionNameEnum sectionName) {
      if (sectionName == null) {
         throw new IllegalArgumentException("sectionName cannot be null");
      }
      this.sectionName = sectionName;
   }

   public void setSelected(Boolean selected) {
      this.selected = selected;
   }

   /*
    * (non-Javadoc)
    * 
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object obj) {
      if ( obj == null ) return false;
      if ( this == obj ) return true;
      if ( getClass() != obj.getClass() ) return false;
      IncidentGroupPrefsImpl o = (IncidentGroupPrefsImpl)obj;
      return new EqualsBuilder()
      .append(new Object[]{id,incidentGroupId,sectionName,fieldLabel,position,selected},
            new Object[]{o.id,o.incidentGroupId,o.sectionName,o.fieldLabel,o.position,o.selected})
            .appendSuper(super.equals(o))
            .isEquals();
   }   

   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   public int hashCode() {
      return new HashCodeBuilder(31,33)
      .append(super.hashCode())
      .append(new Object[]{id,incidentGroupId,sectionName,fieldLabel,position,selected})
      .toHashCode();
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
      return new ToStringBuilder(this)
      .append("id", id)
      .append("incidentGroupId", incidentGroupId)
      .append("sectionName", sectionName)
      .append("fieldLabel", fieldLabel)
      .append("position", position)
      .append("selected", selected)
      .appendSuper(super.toString())
      .toString();
   }

}
