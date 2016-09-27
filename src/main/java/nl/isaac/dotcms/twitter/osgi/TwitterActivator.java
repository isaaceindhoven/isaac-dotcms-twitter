package nl.isaac.dotcms.twitter.osgi;

/**
* dotCMS Twitter plugin by ISAAC - The Full Service Internet Agency is licensed
* under a Creative Commons Attribution 3.0 Unported License
* - http://creativecommons.org/licenses/by/3.0/
* - http://www.geekyplugins.com/
*
* @copyright Copyright (c) 2013 ISAAC Software Solutions B.V. (http://www.isaac.nl)
*/

import java.util.Date;

import javax.servlet.ServletException;

import nl.isaac.dotcms.twitter.servlet.SearchServlet;
import nl.isaac.dotcms.twitter.servlet.TimelineServlet;
import nl.isaac.dotcms.twitter.util.TwitterFieldFactory;
import nl.isaac.dotcms.twitter.viewtool.TwitterViewTool;
import nl.isaac.dotcms.util.osgi.ExtendedGenericBundleActivator;
import nl.isaac.dotcms.util.osgi.ViewToolScope;

import org.apache.felix.http.api.ExtHttpService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.NamespaceException;
import org.osgi.util.tracker.ServiceTracker;

import com.dotmarketing.filters.CMSFilter;
import com.dotmarketing.util.Logger;

/**
 * OSGI activator, this class will be called when the plugin starts through the OSGI portlet
 * @author Danny Gloudemans
 *
 */
public class TwitterActivator extends ExtendedGenericBundleActivator {

	private SearchServlet searchServlet;
	private TimelineServlet timelineServlet;
	private ServiceTracker<ExtHttpService, ExtHttpService> tracker;

	public void start(BundleContext context) throws Exception {
		Logger.info(this, "Twitter: Activator.start()");

		try {
			TwitterFieldFactory twitterFieldFactory = new TwitterFieldFactory();

			// Initialize the required OSGI services
			initializeServices(context);

			// ViewTool
			addViewTool(context, TwitterViewTool.class, "isaacTwitterTool", ViewToolScope.REQUEST);

			// Register the servlet and filter
			tracker = new ServiceTracker<ExtHttpService, ExtHttpService>(context, ExtHttpService.class, null) {
				@Override public ExtHttpService addingService(ServiceReference<ExtHttpService> reference) {
					ExtHttpService extHttpService = super.addingService(reference);
					searchServlet = new SearchServlet();
					timelineServlet = new TimelineServlet();
					try {
						extHttpService.registerServlet("/search.json", searchServlet, null, null);
						extHttpService.registerServlet("/1/statuses/user_timeline.json", timelineServlet, null, null);
					} catch (ServletException e) {
						throw new RuntimeException("Failed to register servlet and filter", e);
					} catch (NamespaceException e) {
						throw new RuntimeException("Failed to register servlet and filter", e);
					}
					Logger.info(this, "Registered servlet and filter " + new Date(System.currentTimeMillis()));
					return extHttpService;
				}
				@Override public void removedService(ServiceReference<ExtHttpService> reference, ExtHttpService extHttpService) {
					extHttpService.unregisterServlet(searchServlet);
					extHttpService.unregisterServlet(timelineServlet);
					super.removedService(reference, extHttpService);
				}
			};
			tracker.open();
			CMSFilter.addExclude("/search.json");
			CMSFilter.addExclude("/1/statuses/user_timeline.json");

			addRewriteRule("^/search.json$", "/app/search.json", "forward", "TwitterSearchServlet");
			addRewriteRule("^/1/statuses/user_timeline.json$", "/app/1/statuses/user_timeline.json", "forward", "TwitterTimelineServlet");

			//Check if required fields exists in the Host Structure, if not add them
			twitterFieldFactory.createFieldsInHosts();
		} catch (Throwable t) {
			Logger.error(this, "Error while initializing Twitter plugin. Calling stop() to unregister plugin", t);
			stop(context);
		}
	}

	public void stop(BundleContext context) throws Exception {
		Logger.info(this, "Twitter: Activator.stop()");

		CMSFilter.removeExclude("/1/statuses/user_timeline.json");
		CMSFilter.removeExclude("/app/1/statuses/user_timeline.json");
		CMSFilter.removeExclude("/search.json");
		CMSFilter.removeExclude("/app/search.json");
		tracker.close();

		unregisterServices(context);
	}
}