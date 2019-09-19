package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Url;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.ForeignKey;
import org.springframework.security.GrantedAuthority;

/**
 *  
 * @author bsteiner
 */
@Entity
@Table(name="isw_url")
public class UrlImpl extends PersistableImpl implements Url {

   @Id
   @GeneratedValue()
   @Column(name = "ID", length=19)
   private Long id = 0L;
   	
	@Column(name="URL", length=2048)
	private String url;
   
   @ManyToMany(fetch=FetchType.EAGER, cascade = CascadeType.PERSIST,targetEntity=IsuiteAuthorityImpl.class)
   @JoinTable(
        name="isw_url_authority",
        joinColumns={@JoinColumn(name="URL_ID")},
        inverseJoinColumns={@JoinColumn(name="AUTHORITY_ID")}
    )
   @ForeignKey(name="FK_URL_AUTHORITY__URL", inverseName="FK_URL_AUTHORITY__AUTHORITY")
	private Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
   
   /**
    * Default Constructor
    */
   public UrlImpl() {}
	
   /**
    * Constructor
    * 
    * @param url <code>String</code> value of a url
    * @param authorities <code>Collection</code> of <code>GrantedAuthority</code> objects.
    */
	public UrlImpl(String url, Collection<GrantedAuthority> authorities) {
		setPath(url);
		setAuthorityNames(authorities);
	}

   /* 
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Persistable#getId()
    */
   public Long getId() {
      return this.id;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Persistable#setId(java.lang.Long)
    */
   public void setId(Long id) {
      this.id = id;
   }
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.access.Url#getPath()
	 */
	public String getPath() {
		return url;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.access.Url#getAuthorityNames()
	 */
	public Collection<GrantedAuthority> getAuthorityNames() {
		return this.authorities;
	}
	
	/**
	 * Accessor for url.
	 * @param url used for page, cannot be null
	 * @see #getUrl()
	 */
	private final void setPath(String url) {
		if (url == null) {
			throw new IllegalArgumentException("url cannot be null");
		}
		this.url = url;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.access.Url#setAuthorityNames(java.util.Collection)
	 */
	private final void setAuthorityNames(Collection<GrantedAuthority> authorities) {
		if (authorities == null) {
			throw new IllegalArgumentException("roles collection cannot be null");
		}
		this.authorities = authorities;
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
	      UrlImpl o = (UrlImpl)obj;
	      return new EqualsBuilder()
	      	.append(new Object[]{id,url},
	      			new Object[]{o.id,o.url})
	  	    .appendSuper(super.equals(o))
	      	.isEquals();
	   }   
	   
	   /* (non-Javadoc)
	    * @see java.lang.Object#hashCode()
	    */
	   public int hashCode() {
		  return new HashCodeBuilder(31,33)
		  	.append(super.hashCode())
		  	.append(new Object[]{id,url})
		  	.toHashCode();
	   }

	   /* (non-Javadoc)
	    * @see java.lang.Object#toString()
	    */
	   public String toString() {
		   return new ToStringBuilder(this)
		       .append("id", id)
		       .append("url", url)
		       .appendSuper(super.toString())
		       .toString();
	   }   
	
}
