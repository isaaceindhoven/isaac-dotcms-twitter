package nl.isaac.dotcms.twitter.util;

/**
* dotCMS Twitter plugin by ISAAC - The Full Service Internet Agency is licensed 
* under a Creative Commons Attribution 3.0 Unported License
* - http://creativecommons.org/licenses/by/3.0/
* - http://www.geekyplugins.com/
* 
* @copyright Copyright (c) 2013 ISAAC Software Solutions B.V. (http://www.isaac.nl)
*/

import java.util.List;

import com.dotmarketing.cache.ContentTypeCacheImpl;
import com.dotmarketing.cache.FieldsCache;
import com.dotmarketing.exception.DotHibernateException;
import com.dotmarketing.portlets.structure.factories.FieldFactory;
import com.dotmarketing.portlets.structure.factories.StructureFactory;
import com.dotmarketing.portlets.structure.model.Field;
import com.dotmarketing.portlets.structure.model.Structure;
import com.dotmarketing.util.Logger;

/**
 * In this class the plugin will check if the fields already exists in the Host structure
 * if not the class will create these fields in the Host structure
 * @author Danny Gloudemans
 *
 */
public class TwitterFieldFactory {
	
	private boolean fieldLinedividerTwitterConfigurationExists 	= false;
	private boolean fieldTwitterConsumerKeyExists 				= false;
	private boolean fieldTwitterSecretConsumerKeyExists 		= false;
	private boolean fieldTwitterAccessTokenExists 				= false;
	private boolean fieldTwitterSecretAccessTokenExists 		= false;
	
	/**
	 * Add all the configuration fields that are required for the Twitter plugin
	 */
	public void createFieldsInHosts() {
		if(!fieldsExistsInHost()) {
			
			Logger.info(this, "********************START CREATE FIELDS FOR TWITTER PLUGIN*********************");
			Logger.info(this, "*The required fields for the Twitter plugin didn't exits in the Host structure*");
									
			Structure hostStructure = StructureFactory.getStructureByVelocityVarName("Host");
			
			addFieldToHostStructure(hostStructure, Configuration.HOSTFIELDNAME_TWITTERLINEDIVIDER, Field.FieldType.LINE_DIVIDER, Field.DataType.SECTION_DIVIDER, 95);
			addFieldToHostStructure(hostStructure, Configuration.HOSTFIELDNAME_TWITTERCONSUMERKEY, Field.FieldType.TEXT, Field.DataType.TEXT, 96);
			addFieldToHostStructure(hostStructure, Configuration.HOSTFIELDNAME_TWITTERSECRETCONSUMERKEY, Field.FieldType.TEXT, Field.DataType.TEXT, 97);
			addFieldToHostStructure(hostStructure, Configuration.HOSTFIELDNAME_TWITTERACCESSTOKEN, Field.FieldType.TEXT, Field.DataType.TEXT, 98);
			addFieldToHostStructure(hostStructure, Configuration.HOSTFIELDNAME_TWITTERSECRETACCESSTOKEN, Field.FieldType.TEXT, Field.DataType.TEXT, 99);
			
			// Update the cache, otherwise we can't save the values of the new fields
			FieldsCache.removeFields( hostStructure ); 
			ContentTypeCacheImpl contentTypeCache = new ContentTypeCacheImpl();
			contentTypeCache.remove(hostStructure);
			try {
				StructureFactory.saveStructure(hostStructure);
			} catch (DotHibernateException e) {
				throw new RuntimeException(e.toString(), e);
			} 
			FieldsCache.addFields(hostStructure, hostStructure.getFieldsBySortOrder()); 

			Logger.info(this, "*All the missing fields for the Twitter plugin are added to the Host structure*");
			Logger.info(this, "*********************END CREATE FIELDS FOR TWITTER PLUGIN**********************");
		}
	}
	
	/**
	 * Check if all the required configuration fields already exists in the Host structure
	 * @return boolean if all the configuration already exists in the host structure
	 */
	public boolean fieldsExistsInHost() {
		
		Structure hostStructure = StructureFactory.getStructureByVelocityVarName("Host");
		List<Field> fieldsInStructure = FieldFactory.getFieldsByStructure(hostStructure.getInode());
		
		for(Field f : fieldsInStructure) {
			if(Configuration.HOSTFIELDNAME_TWITTERCONSUMERKEY.equalsIgnoreCase(f.getFieldName())) {
				fieldTwitterConsumerKeyExists = true;
			} else if(Configuration.HOSTFIELDNAME_TWITTERSECRETCONSUMERKEY.equalsIgnoreCase(f.getFieldName())) {
				fieldTwitterSecretConsumerKeyExists = true;
			} else if(Configuration.HOSTFIELDNAME_TWITTERACCESSTOKEN.equalsIgnoreCase(f.getFieldName())) {
				fieldTwitterAccessTokenExists = true;
			} else if(Configuration.HOSTFIELDNAME_TWITTERSECRETACCESSTOKEN.equalsIgnoreCase(f.getFieldName())) {
				fieldTwitterSecretAccessTokenExists = true;
			} else if(Configuration.HOSTFIELDNAME_TWITTERLINEDIVIDER.equalsIgnoreCase(f.getFieldName())) {
				fieldLinedividerTwitterConfigurationExists = true;
			}
		}
		
		//If one of the fields doesn't exist, it will return false
		if(!fieldTwitterConsumerKeyExists || !fieldTwitterSecretConsumerKeyExists || !fieldTwitterAccessTokenExists
				|| !fieldTwitterSecretAccessTokenExists || !fieldLinedividerTwitterConfigurationExists) {
			return false;
		}
		
		//All fields exists in the host
		return true;
	}
	
	/**
	 * Check for each field if the field already exists in the Host structure,
	 * if not the method will add this new field to the Host structure
	 * @param hostStructure
	 * @param fieldName
	 * @param fieldtype
	 * @param fieldDataType
	 * @param order
	 */
	private void addFieldToHostStructure(Structure hostStructure, String fieldName, Field.FieldType fieldtype, Field.DataType fieldDataType, int order) {
		try {
			Field field = FieldFactory.getFieldByName("Host", fieldName);
					
			if(null == field.getVelocityVarName() || field.getVelocityVarName().isEmpty()) {

				Field newField = new Field(fieldName, fieldtype, fieldDataType, hostStructure, false, false, true, order, false, false, true);
				Logger.info(this, "*Added the field '"+fieldName+"' to the Host structure");
				FieldFactory.saveField(newField);
			}
		} catch (DotHibernateException e) {
			throw new RuntimeException(e.toString(), e);
		}
	}

}
