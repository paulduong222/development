package gov.nwcg.isuite.framework.types;

public class DataTypes {
	public static final int _STRING=0;
	public static final int _LONG=1;
	public static final int _INTEGER=2;
	public static final int _INT=3;
	public static final int _BOOLEAN=4;
	public static final int _BIGDECIMAL=5;
	public static final int _COLLECTION=6;
	public static final int _DATE=7;
	public static final int _TIMESTAMP=8;
	public static final int _BIGINTEGER=9;
	public static final int _DOUBLE=10;
	public static final int _TIME=11;
	public static final int _SQLDATE=12;
	
	private static final String[] supportedTypes = {"java.lang.String"
											,"java.lang.Long"
											,"java.lang.Integer"
											,"java.lang.int"
											,"java.lang.Boolean"
											,"java.math.BigDecimal"
											,"java.util.Collection"
											,"java.util.Date"
											,"java.sql.Timestamp"
											,"java.math.BigInteger"
											,"java.lang.Double"
											,"java.sql.Time"
											,"java.sql.Date"
	};

	public static int getType(String classType) throws Exception {
		for(int i=0;i<supportedTypes.length;i++){
			if(classType.equals(supportedTypes[i])){
				return i;
			}
		}
		
		throw new Exception("Data type not found: " + classType);
	}
}
