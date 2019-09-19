package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentQSKind;
import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.persistence.hibernate.query.IncidentQSKindQuery;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@SequenceGenerator(name="SEQ_INCIDENT_QS_KIND", sequenceName="SEQ_INCIDENT_QS_KIND")
@Table(name="isw_incident_qs_kind")
@NamedQueries(value = {
         @NamedQuery(name=IncidentQSKindQuery.DELETE_ALL_WITH_INCIDENT_ID,query=IncidentQSKindQuery.DELETE_ALL_WITH_INCIDENT_ID_QUERY)
})
public class IncidentQSKindImpl extends PersistableImpl implements IncidentQSKind {

   @Id
   @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_INCIDENT_QS_KIND")
   private Long id = 0L;
   
   @OneToOne(targetEntity=IncidentImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name = "INCIDENT_ID", insertable = true, updatable = true, unique = false, nullable = true)
   @OnDelete(action=OnDeleteAction.NO_ACTION)
   private Incident incident;
   
   @Column(name="INCIDENT_ID", insertable = false, updatable = false, nullable = true)
   private Long incidentId;
   
   @OneToOne(targetEntity=KindImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name = "INCIDENT_KIND_ID", insertable = true, updatable = true, unique = false, nullable = false)
   @OnDelete(action=OnDeleteAction.NO_ACTION)
   private Kind kind;
   
   @Column(name="INCIDENT_KIND_ID", insertable = false, updatable = false, nullable = false)
   private Long kindId;
   

   /**
    * 
    */
   public IncidentQSKindImpl() {
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
    * @see gov.nwcg.isuite.core.domain.IncidentQSKind#getIncident()
    */
   @Override
   public Incident getIncident() {
      return incident;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IncidentQSKind#getIncidentId()
    */
   @Override
   public Long getIncidentId() {
      return incidentId;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IncidentQSKind#getKind()
    */
   @Override
   public Kind getKind() {
      return kind;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IncidentQSKind#getKindId()
    */
   @Override
   public Long getKindId() {
      return kindId;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IncidentQSKind#setIncident(gov.nwcg.isuite.core.domain.Incident)
    */
   @Override
   public void setIncident(Incident incident) {
      this.incident = incident;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IncidentQSKind#setIncidentId(java.lang.Long)
    */
   @Override
   public void setIncidentId(Long incidentId) {
      this.incidentId = incidentId;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IncidentQSKind#setKind(gov.nwcg.isuite.core.domain.Kind)
    */
   @Override
   public void setKind(Kind kind) {
      this.kind = kind;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IncidentQSKind#setKindId(java.lang.Long)
    */
   @Override
   public void setKindId(Long kindId) {
      this.kindId = kindId;
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
      IncidentQSKindImpl o = (IncidentQSKindImpl)obj;
      return new EqualsBuilder()
      .append(new Object[]{id,incidentId,kindId},
            new Object[]{o.id,o.incidentId,o.kindId})
            .appendSuper(super.equals(o))
            .isEquals();
   }   

   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   public int hashCode() {
      return new HashCodeBuilder(31,33)
      .append(super.hashCode())
      .append(new Object[]{id,incidentId,kindId})
      .toHashCode();
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
      return new ToStringBuilder(this)
      .append("id", id)
      .append("incidentId", incidentId)
      .append("kindId", kindId)
      .appendSuper(super.toString())
      .toString();
   }   
}
