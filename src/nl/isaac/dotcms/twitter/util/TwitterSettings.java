package nl.isaac.dotcms.twitter.util;

/**
* dotCMS Twitter plugin by ISAAC - The Full Service Internet Agency is licensed 
* under a Creative Commons Attribution 3.0 Unported License
* - http://creativecommons.org/licenses/by/3.0/
* - http://www.geekyplugins.com/
* 
* @copyright Copyright (c) 2013 ISAAC Software Solutions B.V. (http://www.isaac.nl)
*/

import javax.servlet.http.HttpServletRequest;

import nl.isaac.dotcms.twitter.pojo.TwitterConfiguration;

import com.dotmarketing.beans.Host;
import com.dotmarketing.business.web.WebAPILocator;
import com.dotmarketing.exception.DotDataException;
import com.dotmarketing.exception.DotSecurityException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;

/**
 * In this class the plugin get the configuration from the Host structure. 
 * If the configuration isn't available there will be a NullPointerException.
 * @author Danny Gloudemans
 *
 */
public class TwitterSettings {

	/**
	 * Get TwitterConfiguration from the Host. When the configuration isn't set there will be a NullPointerException
	 * @param request
	 * @return TwitterConfiguration
	 */
	public TwitterConfiguration getTwitterConfiguration(HttpServletRequest request) {
		//Extra check if all the fields still exist in the Host structure
		TwitterFieldFactory twitterFieldFactory = new TwitterFieldFactory();
		twitterFieldFactory.createFieldsInHosts();
		
		Host hostWithTwitterSettings;
		try {
			hostWithTwitterSettings = WebAPILocator.getHostWebAPI().getCurrentHost(request);
		} catch (PortalException e) {
			throw new RuntimeException(e.toString(), e);
		} catch (SystemException e) {
			throw new RuntimeException(e.toString(), e);
		} catch (DotDataException e) {
			throw new RuntimeException(e.toString(), e);
		} catch (DotSecurityException e) {
			throw new RuntimeException(e.toString(), e);
		}
		
		String twitterConsumerKey 		= checkIfValueIsNotEmpty(hostWithTwitterSettings, "twitterConsumerKey");
		String twitterSecretConsumerKey = checkIfValueIsNotEmpty(hostWithTwitterSettings, "twitterSecretConsumerKey");
		String twitterAccessToken 		= checkIfValueIsNotEmpty(hostWithTwitterSettings, "twitterAccessToken");
		String twitterSecretAccessToken = checkIfValueIsNotEmpty(hostWithTwitterSettings, "twitterSecretAccessToken");
		TwitterConfiguration twitterConfiguration = new TwitterConfiguration(twitterConsumerKey, twitterSecretConsumerKey, twitterAccessToken, twitterSecretAccessToken);
		return twitterConfiguration;
	}
	
	/**
	 * Check if Twitter configuration field is filled, if not there will be a NullPointerException
	 * @param hostWithTwitterSettings
	 * @param fieldVarName
	 * @return Value of Twitter Configuration
	 */
	private String checkIfValueIsNotEmpty(Host hostWithTwitterSettings, String fieldVarName) {
		String tempValue = hostWithTwitterSettings.getStringProperty(fieldVarName);
		if(tempValue == null || tempValue.isEmpty()) {
			throw new NullPointerException("The value of field '" + fieldVarName + "' is not filled in the Host");
		}
		return tempValue;		
	}
}