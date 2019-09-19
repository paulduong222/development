/**
 * Indicates that this object is transferable between site and incident.
 * <p>
 * Since Transferable objects can exist in multiple repositories (databases or
 * xml etc.) they must have some kind of identifier that is independant of the
 * database id.
 * </p>
 */
package gov.nwcg.isuite.framework.core.domain;



/**
 * @author dougAnderson
 */
public interface Transferable {

	/**
	 * Return the identity string of this object.
	 * <p>
	 * Since Transferable objects can exist in multiple repositories (databases
	 * or xml etc.) they must have some kind of identifier that is independent
	 * of the database id.
	 * </p>
	 * 
	 * @return the identity of this object
	 */
	public String getIdentity();

   public void setIdentity(String identity);

	
	 
	 
}
