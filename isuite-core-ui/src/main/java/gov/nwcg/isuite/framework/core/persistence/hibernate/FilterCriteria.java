package gov.nwcg.isuite.framework.core.persistence.hibernate;

public class FilterCriteria {
	public static final int TYPE_EQUAL=1;
	public static final int TYPE_ISNULL=2;
	public static final int TYPE_LIKE=3;
	public static final int TYPE_ILIKE=4;
	public static final int TYPE_ISNOTNULL=5;
	public static final int TYPE_IN_STRING=6;
	public static final int TYPE_NOT_EQUAL=7;
	public static final int TYPE_OR=8;
	
	private String field=null;
	private Object value=null;
	private String field2=null;
	private Object value2=null;
	private int type=0;
	private int fieldType=0;
	
	public static final int FIELD_INT=0;
	public static final int FIELD_LONG=1;
	public static final int FIELD_BOOLEAN=2;
	public static final int FIELD_STRING=3;
	
	public FilterCriteria(String field,Object value,int type){
		this.field=field;
		this.value=value;
		this.type=type;
	}

	public FilterCriteria(String field,Object value,int type, int fieldType){
		this(field,value,type);
		this.fieldType=fieldType;
	}
	
	public FilterCriteria(String field1, Object value1, String field2, Object value2, int type) {
		this.field = field1;
		this.value = value1;
		this.field2 = field2;
		this.value2 = value2;
		this.type = type;
	}
	
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	/**
	 * Returns the fieldType.
	 *
	 * @return 
	 *		the fieldType to return
	 */
	public int getFieldType() {
		return fieldType;
	}

	/**
	 * Sets the fieldType.
	 *
	 * @param fieldType 
	 *			the fieldType to set
	 */
	public void setFieldType(int fieldType) {
		this.fieldType = fieldType;
	}

	/**
	 * @param field2 the field2 to set
	 */
	public void setField2(String field2) {
		this.field2 = field2;
	}

	/**
	 * @return the field2
	 */
	public String getField2() {
		return field2;
	}

	/**
	 * @param value2 the value2 to set
	 */
	public void setValue2(Object value2) {
		this.value2 = value2;
	}

	/**
	 * @return the value2
	 */
	public Object getValue2() {
		return value2;
	}

}
