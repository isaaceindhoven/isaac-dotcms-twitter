package nl.isaac.dotcms.twitter.servlet;

/**
* dotCMS Twitter plugin by ISAAC - The Full Service Internet Agency is licensed
* under a Creative Commons Attribution 3.0 Unported License
* - http://creativecommons.org/licenses/by/3.0/
* - http://www.geekyplugins.com/
*
* @copyright Copyright (c) 2017 ISAAC Software Solutions B.V. (http://www.isaac.nl)
*/

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.isaac.dotcms.twitter.pojo.CustomStatus;
import nl.isaac.dotcms.twitter.pojo.TwitterConfiguration;
import nl.isaac.dotcms.twitter.util.JSONResponseWriter;
import nl.isaac.dotcms.twitter.util.TwitterSettings;
import nl.isaac.dotcms.twitter.util.TwitterUtil;

/**
 * This servlet will act like the old Twitter API 1.0 Search call.
 * @author Danny Gloudemans
 *
 */
public class SearchServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		TwitterSettings twitterSettings = new TwitterSettings();

		int count = 20;
		String jsonP = req.getParameter("callback");
		String[] queries = null;
		String query = "";

		if (req.getParameter("from") != null) {
			queries = req.getParameterValues("from");

			for(String queryString : queries) {
				query += queryString;
			}
		}

		if (req.getParameter("count") != null) {
			count = Integer.parseInt(req.getParameter("count"));
		}

		TwitterConfiguration twitterConfiguration = twitterSettings.getTwitterConfiguration(req);
		TwitterUtil twitterUtil = new TwitterUtil(twitterConfiguration.getTwitterConsumerKey(), twitterConfiguration.getTwitterSecretConsumerKey(), twitterConfiguration.getTwitterAccessToken(), twitterConfiguration.getTwitterSecretAccessToken());

		List<CustomStatus> statussen = twitterUtil.searchMultipleQueries(query, count);

		try {
			JSONResponseWriter.writeObjectToResponse(jsonP, statussen, resp);
		} catch (IOException e) {
			throw new RuntimeException(e.toString(), e);
		}
	}
}
