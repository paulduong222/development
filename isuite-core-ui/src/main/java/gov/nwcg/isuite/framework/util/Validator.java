package gov.nwcg.isuite.framework.util;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.types.ErrorEnum;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

	/**
	 * Performs some validation on the string field.
	 * 
	 * @param fieldName
	 * 			the name of field being validated
	 * @param fieldValue
	 * 			the value of the field being validated
	 * @param maxLength
	 * 			the max number of characters allowed
	 * @param required
	 * 			flag indicating whether or not the field is required (not null)
	 * @throws ValidationException
	 */
	public static void validateStringField(String fieldName, String fieldValue,int maxLength,boolean required) throws ValidationException{
		
		if( (required) && (null==fieldValue) ){
			ErrorObject error = new ErrorObject(ErrorEnum._900002_REQUIRD_FIELD,fieldName);
			throw new ValidationException(error);
		}

		if(null!=fieldValue){
			if(fieldValue.length() > maxLength){
				ErrorObject error = new ErrorObject(ErrorEnum._900003_FIELD_EXCEEDS_MAXIMUM_ALLOWED,fieldName,String.valueOf(maxLength));
				throw new ValidationException(error);
			}
		}
	}

	/**
	 * Performs some validation on the string field.
	 * 
	 * @param fieldName
	 * 			the name of field being validated
	 * @param fieldValue
	 * 			the value of the field being validated
	 * @param maxLength
	 * 			the max number of characters allowed
	 * @param required
	 * 			flag indicating whether or not the field is required (not null)
	 * @throws ValidationException
	 */
	public static ErrorObject validateStringField2(String fieldName, String fieldValue,int maxLength,boolean required) {
		
		if( (required) && (!StringUtility.hasValue(fieldValue)) ){
			ErrorObject error = new ErrorObject("text.requiredFieldError",fieldName);
			return error;
		}

		if(null!=fieldValue){
			if(fieldValue.length() > maxLength){
				ErrorObject error = new ErrorObject(ErrorEnum._900003_FIELD_EXCEEDS_MAXIMUM_ALLOWED,fieldName,String.valueOf(maxLength));
				return error;
			}
		}
		
		return null;
	}

	public static ErrorObject validateVoField2(String fieldName, AbstractVo vo,boolean required) {
		if (required) {
			if(null==vo || !LongUtility.hasValue(vo.getId())){
				ErrorObject error = new ErrorObject("text.requiredFieldError",fieldName);
				return error;
			}
		}
		
		return null;
	}
	
	public static ErrorObject validateVoIdField(String fieldName, Long id,boolean required) {
		if (required) {
			if(null==id || !LongUtility.hasValue(id)){
				ErrorObject error = new ErrorObject("text.requiredFieldError",fieldName);
				return error;
			}
		}
		
		return null;
	}

	/**
	 * Performs some validation on the boolean field.
	 * 
	 * @param fieldName
	 * 			the name of field being validated
	 * @param fieldValue
	 * 			the value of the field being validated
	 * @param required
	 * 			flag indicating whether or not the field is required (not null)
	 * @throws ValidationException
	 */
	public static void validateBooleanField(String fieldName, Boolean fieldValue, boolean required) throws ValidationException {
		if( (required) && (null==fieldValue) ){
			ErrorObject error = new ErrorObject(ErrorEnum._900002_REQUIRD_FIELD,fieldName);
			throw new ValidationException(error);
		}
	}

	/**
	 * Performs some validation on the boolean field.
	 * 
	 * @param fieldName
	 * 			the name of field being validated
	 * @param fieldValue
	 * 			the value of the field being validated
	 * @param required
	 * 			flag indicating whether or not the field is required (not null)
	 * @throws ValidationException
	 */
	public static ErrorObject validateBooleanField2(String fieldName, Boolean fieldValue, boolean required)  {
		if( (required) && (null==fieldValue) ){
			ErrorObject error = new ErrorObject("text.requiredFieldError",fieldName);
			return error;
		}
		return null;
	}
	
	/**
	 * Performs some validation on the entity field.
	 * 
	 * @param fieldName
	 * 			the name of field being validated
	 * @param fieldValue
	 * 			the value of the field being validated
	 * @param required
	 * 			flag indicating whether or not the field is required (not null)
	 * @throws ValidationException
	 */
	public static void validateEntityField(String fieldName, Persistable entity, boolean required) throws ValidationException {
		if( (required) && (null==entity) ){
			ErrorObject error = new ErrorObject(ErrorEnum._900002_REQUIRD_FIELD,fieldName);
			throw new ValidationException(error);
		}
		
		if( (required) && (null==entity.getId()) ){
			ErrorObject error = new ErrorObject(ErrorEnum._900002_REQUIRD_FIELD,fieldName);
			throw new ValidationException(error);
		}
			
		
	}
	
	/**
	 * Performs some validation on the Long field.
	 * 
	 * @param fieldName
	 * 			the name of field being validated
	 * @param fieldValue
	 * 			the value of the field being validated
	 * @param required
	 * 			flag indicating whether or not the field is required (not null)
	 * @throws ValidationException
	 */
	public static void validateLongField(String fieldName, Long fieldValue,boolean required) throws ValidationException{
		
		if( (required) && (null==fieldValue) ){
			ErrorObject error = new ErrorObject(ErrorEnum._900002_REQUIRD_FIELD,fieldName);
			throw new ValidationException(error);
		}

		if( (!required) && (null!=fieldValue) && (fieldValue < 1L ) ){
			ErrorObject error = new ErrorObject(ErrorEnum._90000_ERROR,fieldName+" cannot be 0 or less.");
			throw new ValidationException(error);
		}
	}

	/**
	 * Performs some validation on the Long field.
	 * 
	 * @param fieldName
	 * 			the name of field being validated
	 * @param fieldValue
	 * 			the value of the field being validated
	 * @param required
	 * 			flag indicating whether or not the field is required (not null)
	 * @throws ValidationException
	 */
	public static ErrorObject validateLongField2(String fieldName, Long fieldValue,boolean required) {
		
		if( (required) && (!LongUtility.hasValue(fieldValue)) ){
			ErrorObject error = new ErrorObject("text.requiredFieldError",fieldName);
			return error;
		}

		if( (!required) && (null!=fieldValue) && (fieldValue < 1L ) ){
			ErrorObject error = new ErrorObject("info.generic",fieldName+" cannot be 0 or less.");
			return error;
		}
		
		return null;
	}
	
	/**
	 * Performs some validation on the Integer field.
	 * 
	 * @param fieldName
	 * 			the name of field being validated
	 * @param fieldValue
	 * 			the value of the field being validated
	 * @param required
	 * 			flag indicating whether or not the field is required (not null)
	 * @throws ValidationException
	 */
	public static void validateIntegerField(String fieldName, Integer fieldValue,boolean required) throws ValidationException{
		
		if( (required) && (null==fieldValue) ){
			ErrorObject error = new ErrorObject(ErrorEnum._900002_REQUIRD_FIELD,fieldName);
			throw new ValidationException(error);
		}

		if( (!required) && (null!=fieldValue) && (fieldValue < 1 ) ){
			ErrorObject error = new ErrorObject(ErrorEnum._90000_ERROR,fieldName+" cannot be 0 or less.");
			throw new ValidationException(error);
		}
		
	}
	
	/**
	 * Performs some validation on the Integer field.
	 * 
	 * @param fieldName
	 * 			the name of field being validated
	 * @param fieldValue
	 * 			the value of the field being validated
	 * @param required
	 * 			flag indicating whether or not the field is required (not null)
	 * @return
	 * @throws ValidationException
	 */
	public static ErrorObject validateIntegerField2(String fieldName, Integer fieldValue,boolean required) {
		
		if( (required) && (null==fieldValue) ){
			ErrorObject error = new ErrorObject(ErrorEnum._900002_REQUIRD_FIELD,fieldName);
			return error;
		}

		if( (null!=fieldValue) && (fieldValue < 1 ) ){
			ErrorObject error = new ErrorObject(ErrorEnum._GENERIC,fieldName+" cannot be 0 or less.");
			return error;
		}
		
		return null;
	}

	/**
	 * Performs some validation on the date field.
	 * 
	 * @param fieldName
	 * 			the name of field being validated
	 * @param fieldValue
	 * 			the value of the field being validated
	 * @param required
	 * 			flag indicating whether or not the field is required (not null)
	 * @throws ValidationException
	 */
	public static void validateDateField(String fieldName, Date fieldValue,boolean required) throws ValidationException{
		
		if( (required) && (null==fieldValue) ){
			ErrorObject error = new ErrorObject(ErrorEnum._900002_REQUIRD_FIELD,fieldName);
			throw new ValidationException(error);
		}
	}

	public static ErrorObject validateDateField2(String fieldName, Date fieldValue,boolean required) {
		
		if( (required) && (null==fieldValue) ){
			ErrorObject error = new ErrorObject("text.requiredFieldError",fieldName);
			return error;
		}
		
		return null;
	}
	
	/**
	 * Performs some validation on the email string field.
	 * 
	 * @param fieldName
	 * 			the name of field being validated
	 * @param fieldValue
	 * 			the value of the field being validated
	 * @param required
	 * 			flag indicating whether or not the field is required (not null)
	 * @throws ValidationException
	 */
	public static ErrorObject validateEmailField(String fieldName, String fieldValue, boolean required) {
		
		if( (required) && (!StringUtility.hasValue(fieldValue)) ){
			ErrorObject error = new ErrorObject("text.requiredFieldError",fieldName);
			return error;
		}

		if(null!=fieldValue && fieldValue != ""){
			String expression = "^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
			
			Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);  
			Matcher matcher = pattern.matcher(fieldValue);  
			if(!matcher.matches()){  
				ErrorObject error = new ErrorObject(ErrorEnum._EMAIL_FORMAT_ERROR,fieldName);
				return error; 
			}  
		}
		
		return null;
	}
	
	/**
	 * Performs some validation on the email string field.
	 * 
	 * @param fieldName
	 * 			the name of field being validated
	 * @param fieldValue
	 * 			the value of the field being validated
	 * @param required
	 * 			flag indicating whether or not the field is required (not null)
	 * @throws ValidationException
	 */
	public static ErrorObject validatePhoneNumberField(String fieldName, String fieldValue, boolean required) {
		
		if( (required) && (!StringUtility.hasValue(fieldValue)) ){
			ErrorObject error = new ErrorObject("text.requiredFieldError",fieldName);
			return error;
		}

		/*
		 * Phone number must be between 10 an 12 digits
		 * No non-digit characters
		 */
		if(null!=fieldValue && fieldValue != ""){
			if(fieldValue.length() < 10) {
				// invalid phone number length
				ErrorObject error = new ErrorObject(ErrorEnum._PHONE_NUMBER_NON_DIGIT_TOO_SHORT,fieldName,"10");
				return error;
			} else if(fieldValue.length() > 12) {
				// invalid phone number length
				ErrorObject error = new ErrorObject(ErrorEnum._PHONE_NUMBER_LENGTH_TOO_LONG,fieldName);
				return error;
			} else {
				for (int i = 0; i < fieldValue.length(); i++) {
		            if (!Character.isDigit(fieldValue.charAt(i))) {
		            	// Only digits are allowed
						ErrorObject error = new ErrorObject(ErrorEnum._PHONE_NUMBER_FORMAT_ERROR,fieldName);
						return error;
		            }
		        }
			} 
		}
		
		return null;
	}
	
	/**
	 * Performs some validation on the zip code string field.
	 * 
	 * @param fieldName
	 * 			the name of field being validated
	 * @param fieldValue
	 * 			the value of the field being validated
	 * @param required
	 * 			flag indicating whether or not the field is required (not null)
	 * @throws ValidationException
	 */
	public static ErrorObject validateZipCodeField(String fieldName, String fieldValue, boolean required) {
		
		if( (required) && (!StringUtility.hasValue(fieldValue)) ){
			ErrorObject error = new ErrorObject("text.requiredFieldError",fieldName);
			return error;
		}

		/*
		 * Zip Code must be 5 or 9 digits
		 * No non-digit characters
		 */
		if(StringUtility.hasValue(fieldValue)) {
			if(fieldValue.length() == 5 || fieldValue.length() == 9) {
				
			}else {
				ErrorObject error = new ErrorObject(ErrorEnum._ZIP_CODE_INVALID_LENGTH,fieldName);
				return error;
			}
		}
		
		return null;
	}

	/**
	 * Performs some validation on the Decimal field.
	 * 
	 * @param fieldName
	 * 			the name of field being validated
	 * @param fieldValue
	 * 			the value of the field being validated
	 * @param required
	 * 			flag indicating whether or not the field is required (not null)
	 * @throws ValidationException
	 */
	public static ErrorObject validateDecimalField2(String fieldName, java.math.BigDecimal fieldValue,boolean required) {
		
		if( (required) && (!DecimalUtil.hasValue(fieldValue)) ){
			ErrorObject error = new ErrorObject("text.requiredFieldError",fieldName);
			return error;
		}
	
		return null;
	}

	public static ErrorObject validateDecimalField3(String fieldName, java.math.BigDecimal fieldValue,boolean required) {
		
		if( (required) && (null == fieldValue) ){
			ErrorObject error = new ErrorObject("text.requiredFieldError",fieldName);
			return error;
		}
	
		return null;
	}
	
}
