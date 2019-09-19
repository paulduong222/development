package gov.nwcg.isuite.framework.other;


import gov.nwcg.isuite.core.domain.Entry;
import gov.nwcg.isuite.framework.input.InputDataSource;
import gov.nwcg.isuite.framework.input.UpdateDataTypeEnum;

import java.io.Serializable;

/**
 * Represents data needed to retrieve an entry from persistence.
 * 
 * @author doug
 */
public class EntryInfo implements Serializable {
   private static final long serialVersionUID = 2826551627828124160L;
   private String identifier;
   private UpdateDataTypeEnum type;
   private Long databaseId;
   private InputDataSource source;
	
	/**
	 * Constructor.
	 * @param entry entry on which this entryInfo is based, can not be null
	 */
	public EntryInfo(Entry entry){
		if (entry == null) {
			throw new IllegalArgumentException("entry can not be null");
		}
		this.identifier = entry.getUniqueIdentifier();
		this.type = entry.getUpdateDataType();
		this.databaseId = entry.getId();
      this.source =  entry.getSource();
	}
   
   public EntryInfo(String identifier, UpdateDataTypeEnum type, Long databaseId, InputDataSource source) {
		this.identifier = identifier;
		this.type = type;
		this.databaseId = databaseId;
      this.source =  source;
   }
	
	/**
	 * Accessor for databaseId
	 * @return the databaseId
	 */
	public final Long getDatabaseId() {
		return databaseId;
	}
	/**
	 * Accessor for the unique identifier of the data.
	 * @return the identifier unique identifier
	 */
	public final String getIdentifier() {
		return identifier;
	}
	/**
	 * Accessor for the type of data.
	 * @return the type type of data
	 */
	public final UpdateDataTypeEnum getType() {
		return type;
	}

   /**
    * Accessor for source of entry.
    * @return the source
    */
   public final InputDataSource getSource() {
      return source;
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("(");
      buf.append(databaseId);
      buf.append(") ");
      buf.append(identifier);
      buf.append(", - ");
      buf.append(type);
      buf.append(" , ");
      buf.append(source);
      return buf.toString();
   }
   
}
