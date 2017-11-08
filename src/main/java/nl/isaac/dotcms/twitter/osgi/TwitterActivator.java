package nl.isaac.dotcms.twitter.osgi;

import org.osgi.framework.BundleContext;

import com.dotmarketing.util.Logger;

/**
* dotCMS Twitter plugin by ISAAC - The Full Service Internet Agency is licensed
* under a Creative Commons Attribution 3.0 Unported License
* - http://creativecommons.org/licenses/by/3.0/
* - http://www.geekyplugins.com/
*
* @copyright Copyright (c) 2017 ISAAC Software Solutions B.V. (http://www.isaac.nl)
*/

import nl.isaac.dotcms.twitter.servlet.SearchServlet;
import nl.isaac.dotcms.twitter.servlet.TimelineServlet;
import nl.isaac.dotcms.twitter.util.TwitterFieldFactory;
import nl.isaac.dotcms.twitter.viewtool.TwitterViewTool;
import nl.isaac.dotcms.util.osgi.ExtendedGenericBundleActivator;
import nl.isaac.dotcms.util.osgi.ViewToolScope;

/**
 * OSGI activator, this class will be called when the plugin starts through the OSGI portlet
 * @author Danny Gloudemans
 *
 */
public class TwitterActivator extends ExtendedGenericBundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		Logger.info(this, "Twitter: Activator.start()");

		try {
			TwitterFieldFactory twitterFieldFactory = new TwitterFieldFactory();

			// Initialize the required OSGI services
			initializeServices(context);

			// ViewTool
			addViewTool(context, TwitterViewTool.class, "isaacTwitterTool", ViewToolScope.REQUEST);

			// Register the servlet and filter
			addServlet(context, SearchServlet.class, "/search.json");
			addServlet(context, TimelineServlet.class, "/1/statuses/user_timeline.json");

			addRewriteRule("^/search.json$", "/app/search.json", "forward", "TwitterSearchServlet");
			addRewriteRule("^/1/statuses/user_timeline.json$", "/app/1/statuses/user_timeline.json", "forward", "TwitterTimelineServlet");

			//Check if required fields exists in the Host Structure, if not add them
			twitterFieldFactory.createFieldsInHosts();
		} catch (Throwable t) {
			Logger.error(this, "Error while initializing Twitter plugin. Calling stop() to unregister plugin", t);
			stop(context);
		}
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		Logger.info(this, "Twitter: Activator.stop()");
		unregisterServices(context);
	}
}