
package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentPrefs;
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

/**
 * @author mpoll
 *
 */

@Entity
@SequenceGenerator(name="SEQ_INCIDENT_PREFS", sequenceName="SEQ_INCIDENT_PREFS")
@Table(name="isw_incident_prefs")
public class IncidentPrefsImpl extends PersistableImpl implements IncidentPrefs {

   @Id
   @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_INCIDENT_PREFS")
   private Long id = 0L;
   
   @OneToOne(targetEntity=IncidentImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name = "INCIDENT_ID", insertable = true, updatable = true, unique = false, nullable = false)
   @OnDelete(action=OnDeleteAction.NO_ACTION)
   private Incident incident;
   
   @Column(name="INCIDENT_ID", insertable = false, updatable = false, nullable = false)
   private Long incidentId;
   
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
   public IncidentPrefsImpl() {
      super();
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl#getId()
    */
   @Override
   public Long getId() {
      return this.id;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl#setId(java.lang.Long)
    */
   @Override
   public void setId(Long id) {
      this.id = id;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IncidentPrefs#getFieldLabel()
    */
   @Override
   public String getFieldLabel() {
      return fieldLabel;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IncidentPrefs#getIncident()
    */
   @Override
   public Incident getIncident() {
      return incident;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IncidentPrefs#getIncidentId()
    */
   @Override
   public Long getIncidentId() {
      return incidentId;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IncidentPrefs#getPosition()
    */
   @Override
   public Integer getPosition() {
      return position;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IncidentPrefs#getSectionName()
    */
   @Override
   public IncidentPrefsSectionNameEnum getSectionName() {
      return sectionName;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IncidentPrefs#isSelected()
    */
   @Override
   public Boolean isSelected() {
      return selected;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IncidentPrefs#setFieldLabel(java.lang.String)
    */
   @Override
   public void setFieldLabel(String fieldLabel) {
      //TODO:  Double check the requirements for null here.  -dbudge
//      if (fieldLabel == null) {
//         throw new IllegalArgumentException("fieldLabel cannot be null");
//      }
      this.fieldLabel = fieldLabel;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IncidentPrefs#setIncident(gov.nwcg.isuite.core.domain.Incident)
    */
   @Override
   public void setIncident(Incident incident) {
      this.incident = incident;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IncidentPrefs#setIncidentId(java.lang.Long)
    */
   @Override
   public void setIncidentId(Long incidentId) {
      this.incidentId = incidentId;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IncidentPrefs#setPosition(java.lang.Integer)
    */
   @Override
   public void setPosition(Integer position) {
      this.position = position;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IncidentPrefs#setSectionName(gov.nwcg.isuite.framework.types.IncidentPrefsSectionNameEnum)
    */
   @Override
   public void setSectionName(IncidentPrefsSectionNameEnum sectionName) {
      if (sectionName == null) {
         throw new IllegalArgumentException("sectionName cannot be null");
      }
      this.sectionName = sectionName;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IncidentPrefs#setSelected(java.lang.Boolean)
    */
   @Override
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
      IncidentPrefsImpl o = (IncidentPrefsImpl)obj;
      return new EqualsBuilder()
      .append(new Object[]{id,incidentId,sectionName,fieldLabel,position,selected},
            new Object[]{o.id,o.incidentId,o.sectionName,o.fieldLabel,o.position,o.selected})
            .appendSuper(super.equals(o))
            .isEquals();
   }   

   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   public int hashCode() {
      return new HashCodeBuilder(31,33)
      .append(super.hashCode())
      .append(new Object[]{id,incidentId,sectionName,fieldLabel,position,selected})
      .toHashCode();
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
      return new ToStringBuilder(this)
      .append("id", id)
      .append("incidentId", incidentId)
      .append("sectionName", sectionName)
      .append("fieldLabel", fieldLabel)
      .append("position", position)
      .append("selected", selected)
      .appendSuper(super.toString())
      .toString();
   }

}
