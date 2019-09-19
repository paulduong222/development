package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.IncidentGroupQSKind;
import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.persistence.hibernate.query.IncidentGroupQSKindQuery;
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
@SequenceGenerator(name="SEQ_INCIDENT_GROUP_QS_KIND", sequenceName="SEQ_INCIDENT_GROUP_QS_KIND")
@Table(name="isw_incident_group_qs_kind")
@NamedQueries(value = {
         @NamedQuery(name=IncidentGroupQSKindQuery.DELETE_ALL_WITH_INCIDENT_GROUP_ID,query=IncidentGroupQSKindQuery.DELETE_ALL_WITH_INCIDENT_GROUP_ID_QUERY)
})
public class IncidentGroupQSKindImpl extends PersistableImpl implements IncidentGroupQSKind {

   @Id
   @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_INCIDENT_GROUP_QS_KIND")
   private Long id = 0L;
   
   @OneToOne(targetEntity=IncidentGroupImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name = "INCIDENT_GROUP_ID", insertable = true, updatable = true, unique = false, nullable = true)
   private IncidentGroup incidentGroup;
   
   @Column(name="INCIDENT_GROUP_ID", insertable = false, updatable = false, nullable = true)
   private Long incidentGroupId;
   
   @OneToOne(targetEntity=KindImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name = "INCIDENT_GROUP_KIND_ID", insertable = true, updatable = true, unique = false, nullable = false)
   @OnDelete(action=OnDeleteAction.NO_ACTION)
   private Kind kind;
   
   @Column(name="INCIDENT_GROUP_KIND_ID", insertable = false, updatable = false, nullable = false)
   private Long kindId;

   /**
    * 
    */
   public IncidentGroupQSKindImpl() {
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
    * @see gov.nwcg.isuite.core.domain.IncidentQSKind#getIncidentGroup()
    */
   @Override
   public IncidentGroup getIncidentGroup() {
      return incidentGroup;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IncidentQSKind#getIncidentGroupId()
    */
   @Override
   public Long getIncidentGroupId() {
      return incidentGroupId;
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
    * @see gov.nwcg.isuite.core.domain.IncidentQSKind#setIncidentGroup(gov.nwcg.isuite.core.domain.IncidentGroup)
    */
   @Override
   public void setIncidentGroup(IncidentGroup incidentGroup) {
      this.incidentGroup = incidentGroup;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IncidentQSKind#setIncidentGroupId(java.lang.Long)
    */
   @Override
   public void setIncidentGroupId(Long incidentGroupId) {
      this.incidentGroupId = incidentGroupId;
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
      IncidentGroupQSKindImpl o = (IncidentGroupQSKindImpl)obj;
      return new EqualsBuilder()
      .append(new Object[]{id,incidentGroupId,kindId},
            new Object[]{o.id,o.incidentGroupId,o.kindId})
            .appendSuper(super.equals(o))
            .isEquals();
   }   

   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   public int hashCode() {
      return new HashCodeBuilder(31,33)
      .append(super.hashCode())
      .append(new Object[]{id,incidentGroupId,kindId})
      .toHashCode();
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
      return new ToStringBuilder(this)
      .append("id", id)
      .append("incidentGroupId", incidentGroupId)
      .append("kindId", kindId)
      .appendSuper(super.toString())
      .toString();
   }   
}
