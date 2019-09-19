package gov.nwcg.isuite.framework.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;

public class TypeConverter {
	private static final Boolean _BOOLEAN=false;
	private static final String _STRING="";
	private static final Integer _INTEGER=1;
	private static final Long _LONG=0L;
	private static final Date _DATE=new Date();
	private static final java.sql.Date _SQL_DATE = new java.sql.Date(0);
	private static final Timestamp _TIMESTAMP=new java.sql.Timestamp(new java.util.Date().getTime());
	private static final int _INT=-1;
	private static final BigDecimal _BIGDECIMAL=BigDecimal.valueOf(1);
	private static final BigInteger _BIGINTEGER=BigInteger.valueOf(0);
	private static final Double _DOUBLE=Double.valueOf(0.0);
	private static final Short _SHORT=Short.valueOf("0");
	
	public static String convertToTime(Object source) throws Exception {
		if(null == source)
			return "";
		
		String sourceType = source.getClass().getName();

		if(_TIMESTAMP.getClass().getName().equals(sourceType)){
			if(null != source){
				Date dt = (Date)source;
				return DateUtil.toDateString(dt, DateUtil.HH_MM);
			}
		}

		if(_SQL_DATE.getClass().getName().equals(sourceType)){
			if(null != source){
				Date dt = (Date)source;
				return DateUtil.toDateString(dt, DateUtil.HH_MM);
			}
		}

		return "";
	}

	/**
	 * Convert an unknown object into a string.
	 * 
	 * @param source
	 * @return
	 * @throws Exception
	 */
	public static String convertToString(Object source) throws Exception {
		if(null == source)
			return "";
		
		String sourceType = source.getClass().getName();

		if("org.hibernate.lob.SerializableClob".equals(sourceType)){
			String strReturn="";
			InputStream inputStream=null;
			BufferedReader reader=null;
			
			try{
				Clob clob = (Clob)source;
				
				if(null != clob){
					inputStream=clob.getAsciiStream();
					if(null != inputStream){
						reader = new BufferedReader(new InputStreamReader(inputStream));
						StringBuilder sb = new StringBuilder();
						String line = null;
		
						while ((line = reader.readLine()) != null) {
							sb.append(line);
						}
						
						inputStream.close();				
						
						strReturn=sb.toString();
					}
				}
			}catch(Exception ignore){
				
			}finally{
				try{
					if(null != reader)
						reader.close();
				}catch(Exception ee){
				}
				
				try{
					if(null != inputStream)
						inputStream.close();
				}catch(Exception ee){
				}
			}
			
			return strReturn;
		}
		
		if(_STRING.getClass().getName().equals(sourceType)){
			return (String)source;
		}

		if(_TIMESTAMP.getClass().getName().equals(sourceType)){
			if(null != source){
				Date dt = (Date)source;
				return DateUtil.toDateString(dt, DateUtil.MM_SLASH_DD_SLASH_YYYY);
			}
		}

		if(_SQL_DATE.getClass().getName().equals(sourceType)){
			if(null != source){
				Date dt = (Date)source;
				return DateUtil.toDateString(dt, DateUtil.MM_SLASH_DD_SLASH_YYYY);
			}
		}

		if(_BIGDECIMAL.getClass().getName().equals(sourceType)){
			BigDecimal val = (BigDecimal)source;
			if(DecimalUtil.hasValue(val))
				return String.valueOf(val);
			else
				return "0";
		}

		if(_BIGINTEGER.getClass().getName().equals(sourceType)){
			BigInteger val = (BigInteger)source;
			if(null != val)
				return String.valueOf(val);
			else
				return "0";
		}
	
		if(_DOUBLE.getClass().getName().equals(sourceType)){
			Double val = (Double)source;
			if(null != val)
				return String.valueOf(source);
			else
				return "0";
		}
			
		if(_BOOLEAN.getClass().getName().equals(sourceType)){
			String val = String.valueOf((Boolean)source);
			if( (val.equals("1") || (val.equalsIgnoreCase("TRUE"))))
				return "TRUE";
			else
				return "FALSE";
		}

		if(_INTEGER.getClass().getName().equals(sourceType)){
			Integer val = (Integer)source;
			return String.valueOf(val.intValue());
		}
		
		if(_LONG.getClass().getName().equals(sourceType)){
			Long val = (Long)source;
			return String.valueOf(val.longValue());
		}
		
		if(_SHORT.getClass().getName().equals(sourceType)){
			if(null != source){
				return String.valueOf(source);
			}
		}
		
		return "";
	}
	
	/**
	 * Convert an unknown object into a Boolean.
	 * 
	 * @param source
	 * @return
	 * @throws Exception
	 */
	public static Boolean convertToBoolean(Object source) throws Exception {
		if(null==source)
			return Boolean.FALSE;
	
		String sourceType = source.getClass().getName();

		if(_BOOLEAN.getClass().getName().equals(sourceType)){
			return (Boolean)source;
		}

		if(_STRING.getClass().getName().equals(sourceType)){
			String val = (String)source;
			if( (val.equals("1")) || (val.equalsIgnoreCase("TRUE")) )
				return Boolean.TRUE;
			else
				return Boolean.FALSE;
		}
		
		if(_INTEGER.getClass().getName().equals(sourceType)){
			Integer val = (Integer)source;
			if(val==1)
				return Boolean.TRUE;
			else
				return Boolean.FALSE;
		}
		
		if(_LONG.getClass().getName().equals(sourceType)){
			Long val = (Long)source;
			if(val==1L)
				return Boolean.TRUE;
			else
				return Boolean.FALSE;
		}

		if(_BIGDECIMAL.getClass().getName().equals(sourceType)){
			BigDecimal val = (BigDecimal)source;
			if(val.longValue()==1L)
				return Boolean.TRUE;
			else
				return Boolean.FALSE;
		}
		
		// todo: other conversions

		return Boolean.FALSE;
	}
	
	/**
	 * Convert an unknown object into a BigDecimal.
	 * 
	 * @param source
	 * @return
	 * @throws Exception
	 */
	public static BigDecimal convertToBigDecimal(Object source) throws Exception {
		if(null==source)
			return BigDecimal.valueOf(0);
		
		String sourceType = source.getClass().getName();

		if(_STRING.getClass().getName().equals(sourceType)){
			return new BigDecimal((String)source);
		}
		
		if(_BIGDECIMAL.getClass().getName().equals(sourceType)){
			return (BigDecimal)source;
		}

		// todo: other conversions

		return null;
	}
	
	/**
	 * Convert an unknown object into a Integer.
	 * 
	 * @param source
	 * @return
	 * @throws Exception
	 */
	public static Integer convertToInteger(Object source) throws Exception {
		if(null==source)
			return 0;

		String sourceType = source.getClass().getName();

		if(_INTEGER.getClass().getName().equals(sourceType)){
			return (Integer)source;
		}

		if(_LONG.getClass().getName().equals(sourceType)){
			String s=String.valueOf(source);
			return Integer.valueOf(s);
		}
		
		if(_STRING.getClass().getName().equals(sourceType)){
			return Integer.valueOf((String)source);
		}

		if(_BIGDECIMAL.getClass().getName().equals(sourceType)){
			BigDecimal bd = (BigDecimal)source;
			return new Integer(bd.intValue());
		}
		
		if(_BIGINTEGER.getClass().getName().equals(sourceType)){
			String s = String.valueOf(source);
			return Integer.valueOf(s);
		}
		// todo: other conversions

		return null;
	}
	
	/**
	 * Convert an unknown object into a Long.
	 * 
	 * @param source
	 * @return
	 * @throws Exception
	 */
	public static Long convertToLong(Object source) throws Exception {
		if(null==source)
			return 0L;
		
		String sourceType = source.getClass().getName();

		if(_LONG.getClass().getName().equals(sourceType)){
			return (Long)source;
		}

		if(_BIGDECIMAL.getClass().getName().equals(sourceType)){
			return new Long( ((BigDecimal)source).longValue());
		}

		if(_BIGINTEGER.getClass().getName().equals(sourceType)){
			return new Long ( ((BigInteger)source).longValue());
		}
		
		if(_STRING.getClass().getName().equals(sourceType)){
			return new Long((String)source);
		}
		
		if(_INTEGER.getClass().getName().equals(sourceType)){
			return new Long(((Integer)source).longValue());
		}
		
		// todo: other conversions
		
		return null;
	}

	/**
	 * Convert an unknown object into a int.
	 * 
	 * @param source
	 * @return
	 * @throws Exception
	 */
	public static int convertToInt(Object source) throws Exception {
		if(null==source)
			return 0;
		
		String sourceType = source.getClass().getName();

		if(_LONG.getClass().getName().equals(sourceType)){
			Long lng = (Long)source;
			return lng.intValue();
		}
		
		if(_BIGDECIMAL.getClass().getName().equals(sourceType)){
			BigDecimal bd = (BigDecimal)source;
			return bd.intValue();
		}
		
		if(_BIGINTEGER.getClass().getName().equals(sourceType)){
			BigInteger bd = (BigInteger)source;
			return bd.intValue();
		}
		// todo: other conversions

		return Integer.parseInt((String)source);
	}
	
	/**
	 * Convert an unknown object into a Date.
	 * 
	 * @param source
	 * @return
	 * @throws Exception
	 */
	public static Date convertToDate(Object source) throws Exception {
		if(null==source)
			return null;

		String sourceType = source.getClass().getName();

		if(_DATE.getClass().getName().equals(sourceType)){
			return (Date)source;
		}
		
		if(_SQL_DATE.getClass().getName().equals(sourceType)) {
		   return (Date)source;
		}
		
		if(_TIMESTAMP.getClass().getName().equals(sourceType)) {
		   return (Date)source;
		}
		
		// todo: other conversions

		return null;
	}

	/**
	 * Convert an unknown object into a timestamp(Date).
	 * 
	 * @param source
	 * @return
	 * @throws Exception
	 */
	public static Date convertToTimestamp(Object source) throws Exception {
		if(null==source)
			return null;

		String sourceType = source.getClass().getName();

		if(_TIMESTAMP.getClass().getName().equals(sourceType)){
			Date dt = (Date)source;
			return new java.sql.Timestamp(dt.getTime());
		}
	
		// todo: other conversions
		
		return null;
	}
	
	/**
	 * Convert an unknown object into a BigInteger.
	 * 
	 * @param source
	 * @return
	 * @throws Exception
	 */
	public static BigInteger convertToBigInteger(Object source) throws Exception {
		if(null==source)
			return BigInteger.valueOf(0);
		
		String sourceType = source.getClass().getName();

		if(_BIGINTEGER.getClass().getName().equals(sourceType)){
			return (BigInteger)source;
		}

		// todo: other conversions

		return null;
	}
	
	/**
	 * Convert an unknown object into a Double.
	 * 
	 * @param source
	 * @return
	 * @throws Exception
	 */
	public static Double convertToDouble(Object source) throws Exception {
		if(null==source)
			return Double.valueOf(0.0);
		
		String sourceType = source.getClass().getName();
		
		if(_DOUBLE.getClass().getName().equals(sourceType)){
			return (Double)source;
		}
		
		if(_BIGDECIMAL.getClass().getName().equals(sourceType)){
			return ((BigDecimal)source).doubleValue();
		}
		
		//TODO: Other Conversions
		
		return null;
	}
	
}
