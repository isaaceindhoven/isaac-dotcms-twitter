package nl.isaac.dotcms.util.osgi;

/**
* dotCMS Twitter plugin by ISAAC - The Full Service Internet Agency is licensed 
* under a Creative Commons Attribution 3.0 Unported License
* - http://creativecommons.org/licenses/by/3.0/
* - http://www.geekyplugins.com/
* 
* @copyright Copyright (c) 2013 ISAAC Software Solutions B.V. (http://www.isaac.nl)
*/

import org.apache.velocity.tools.view.servlet.ServletToolInfo;

import com.dotmarketing.exception.DotRuntimeException;

/**
 * This class provides a way to use ServletToolInfo for every viewtool class defined in an OSGi plugin.
 * It works by setting hte context classloader to the classloader of the class to be set ({@link #setClassname(Class)}) which then delegates to {@link #setClassname(String)} with the classname of the class.
 * And that class in turn uses the context classloader to load the class again. Without this, {@link #setClassname(String)} can't find the viewtool class in the default classloader (since it isn't immediately part of the WebAppClassloader).
 * 
 * @author Maarten
 *
 */
class OSGiSafeServletToolInfo extends ServletToolInfo {
	public void setClassname(Class<?> clazz) {
		final Thread currentThread = Thread.currentThread();
		final ClassLoader current = currentThread.getContextClassLoader();
		currentThread.setContextClassLoader(clazz.getClassLoader());
		try {
			setClassname(clazz.getName());
		} catch (Exception e) {
			throw new DotRuntimeException("Failed to register viewtool via OSGi", e);
		} finally {
			currentThread.setContextClassLoader(current);
		}
	}
}