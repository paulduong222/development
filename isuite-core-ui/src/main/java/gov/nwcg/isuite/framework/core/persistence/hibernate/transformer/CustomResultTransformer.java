package gov.nwcg.isuite.framework.core.persistence.hibernate.transformer;

import gov.nwcg.isuite.framework.types.DataTypes;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.transform.ResultTransformer;

public class CustomResultTransformer implements ResultTransformer {
	private Class<?> targetClass;
	
	private Collection<Scalar> _scalars = new ArrayList<Scalar>();

	private Collection<Projection> _projections = new ArrayList<Projection>();
	
	private Collection<String> _skipTransformations = new ArrayList<String>();
	
	public CustomResultTransformer(Class<?> targetClass){
		this.targetClass=targetClass;
	}

	/**
	 * Adds a projection conversion.
	 * 
	 * @param fromField
	 * @param toField
	 */
	public void addProjection(String fromField,String toField){
		this._projections.add(new Projection(fromField,toField));
	}
	
	/**
	 * Adds a field type conversion.
	 * 
	 * @param property
	 * @param toType
	 */
	public void addScalar(String property,String toType){
		this._scalars.add(new Scalar(property,toType));
	}
	
	/* (non-Javadoc)
	 * @see org.hibernate.transform.ResultTransformer#transformList(java.util.List)
	 */
	public List transformList(List list) {
		return list;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.transform.ResultTransformer#transformTuple(java.lang.Object[], java.lang.String[])
	 */
	public Object transformTuple(Object[] tuple, String[] projections) {
		Object targetObject=null;
		
		try{
			targetObject=Class.forName(targetClass.getName()).newInstance();
			
			PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(targetClass);

			PropertyDescriptor descriptor = null;
			
			for(int i=0;i<tuple.length;i++){
				boolean skipTransformation = false;
				
				/*
				 * Check if there is a custom conversion defined in _projections collection.
				 * If there is not a match found, use the default from the projections array
				 * that was passed into this method from hibernate.
				 */
				Projection projection = getProjection(projections[i]);
				if(null != projection){
					/*
					 * Check the targetClasses propertyDescriptors and see if
					 * there is a setter method for the aliased method (toField).
					 */
					descriptor = getPropertyDescriptor(descriptors,projection.toField);
					if(null==descriptor)
						throw new Exception("Unable to find setter method for alias: " + projection.toField);
				}else{
					/*
					 * Check the targetClasses propertyDescriptors and see if
					 * there is a setter method for the default toField hibernate passed in.
					 */
					descriptor = getPropertyDescriptor(descriptors,projections[i]);
					
					if(null==descriptor){
						/*
						 * check if property transformation should be skipped
						 */
						skipTransformation=this.isSkipTransformation(projections[i]);
						if(!skipTransformation)
							throw new Exception("Unable to find setter method for projection: " + projections[i]);
					}
				}

				if(!skipTransformation){
					if(null != tuple[i]){
						String classType=tuple[i].getClass().getName();
	
						int type = -1;
						
						Object obj = tuple[i];
	
						/*
						 * Determine if a special conversion is required
						 */
						Scalar scalar = getScalar(projections[i]);
						if(null!=scalar)
							type=DataTypes.getType(scalar.toType);
						else
							type=DataTypes.getType(classType);
	
						switch(type){
							case DataTypes._STRING:
								PropertyUtils.setProperty(targetObject, descriptor.getName(), TypeConverter.convertToString(obj) );
								break;
							case DataTypes._LONG:
								PropertyUtils.setProperty(targetObject, descriptor.getName(), TypeConverter.convertToLong(obj) );
								break;
							case DataTypes._INTEGER:
								PropertyUtils.setProperty(targetObject, descriptor.getName(), TypeConverter.convertToInteger(obj) );
								break;
							case DataTypes._INT:
								PropertyUtils.setProperty(targetObject, descriptor.getName(), TypeConverter.convertToInt(obj) );
								break;
							case DataTypes._BOOLEAN:
								PropertyUtils.setProperty(targetObject, descriptor.getName(), TypeConverter.convertToBoolean(obj) );
								break;
							case DataTypes._BIGDECIMAL:
								PropertyUtils.setProperty(targetObject, descriptor.getName(), TypeConverter.convertToBigDecimal(obj) );
								break;
							case DataTypes._DATE:
								PropertyUtils.setProperty(targetObject, descriptor.getName(), TypeConverter.convertToDate(obj) );
								break;
							case DataTypes._TIMESTAMP:
								PropertyUtils.setProperty(targetObject, descriptor.getName(), TypeConverter.convertToTimestamp(obj) );
								break;
							case DataTypes._TIME:
								PropertyUtils.setProperty(targetObject, descriptor.getName(), TypeConverter.convertToTime(obj) );
								break;
							case DataTypes._BIGINTEGER:
								PropertyUtils.setProperty(targetObject, descriptor.getName(), TypeConverter.convertToBigInteger(obj) );
								break;
							case DataTypes._DOUBLE:
								PropertyUtils.setProperty(targetObject, descriptor.getName(), TypeConverter.convertToDouble(obj) );
								break;
							case DataTypes._SQLDATE:
								PropertyUtils.setProperty(targetObject, descriptor.getName(), TypeConverter.convertToDate(obj) );
								break;
						}
					}else
						PropertyUtils.setProperty(targetObject,descriptor.getName(),null);
				}
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return targetObject;
	}

	private boolean isSkipTransformation(String property){
		for(String s : this._skipTransformations){
			if(s.equalsIgnoreCase(property))
				return true;
		}
		return false;
	}
	
	/**
	 * Returns the scalar object if found.
	 * 
	 * @param property
	 * @return
	 */
	private Scalar getScalar(String property){
		for(Scalar s : _scalars){
			if(s.property.equalsIgnoreCase(property))
				return s;
		}
		return null;
	}
	
	/**
	 * Returns the projection object if found.
	 * 
	 * @param fromField
	 * @return
	 */
	private Projection getProjection(String fromField){
		for(Projection p : _projections){
			if(p.fromField.equalsIgnoreCase(fromField))
				return p;
		}
		return null;
	}

	/**
	 * Returns the propertyDescriptor in the propertyDescriptors array if found.
	 * 
	 * @param descriptors
	 * @param toField
	 * @return
	 */
	private PropertyDescriptor getPropertyDescriptor(PropertyDescriptor[] descriptors,String toField){
		for(PropertyDescriptor pd : descriptors){
			if(pd.getName().equalsIgnoreCase(toField)){
				return pd;
			}
		}
		return null;
	}
	
	/**
	 * Inner class to hold scalar conversion definitions.
	 * 
	 * @author dprice
	 */
	private class Scalar{
		public String property="";
		public String toType="";
		
		public Scalar(String property,String toType){
			this.property=property;
			this.toType=toType;
		}
	}
	
	/**
	 * Inner class to hold field projections conversion definitions.
	 * 
	 * @author dprice
	 */
	private class Projection{
		public String fromField="";
		public String toField="";
		
		public Projection(String fromField,String toField){
			this.fromField=fromField;
			this.toField=toField;
		}
	}

	public Collection<String> getSkipTransformations() {
		return _skipTransformations;
	}

	public void addSkipTransformation(String propertyName) {
		_skipTransformations.add(propertyName);
	}
}
