package nl.isaac.dotcms.util.osgi;

/**
* dotCMS Twitter plugin by ISAAC - The Full Service Internet Agency is licensed 
* under a Creative Commons Attribution 3.0 Unported License
* - http://creativecommons.org/licenses/by/3.0/
* - http://www.geekyplugins.com/
* 
* @copyright Copyright (c) 2013 ISAAC Software Solutions B.V. (http://www.isaac.nl)
*/

import org.apache.velocity.tools.view.context.ViewContext;
import org.osgi.framework.BundleContext;

import com.dotmarketing.osgi.GenericBundleActivator;

/**
 * Provides a convenience method for adding viewtools.
 * @author Maarten
 *
 */
public abstract class ExtendedGenericBundleActivator extends GenericBundleActivator {

	@Override
	protected void initializeServices(BundleContext context) throws Exception {
		super.initializeServices(context);
		
	}

	protected void addViewTool(BundleContext context, Class<?> viewtoolClass, String key, ViewToolScope scope) {
		OSGiSafeServletToolInfo viewtool = new OSGiSafeServletToolInfo();
		viewtool.setClassname(viewtoolClass);
		viewtool.setKey(key);
		switch (scope) {
		case APPLICATION:
			viewtool.setScope(ViewContext.APPLICATION);
			break;
		case REQUEST:
			viewtool.setScope(ViewContext.REQUEST);
			break;
		case RESPONSE:
			viewtool.setScope(ViewContext.RESPONSE);
			break;
		case SESSION:
			viewtool.setScope(ViewContext.SESSION);
			break;
		default:
			throw new RuntimeException("Unknown viewtoolscope: " + scope);
		}
		registerViewToolService(context, viewtool);
	}
}
