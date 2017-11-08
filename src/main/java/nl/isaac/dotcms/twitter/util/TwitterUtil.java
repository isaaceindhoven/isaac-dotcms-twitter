package nl.isaac.dotcms.twitter.util;

/**
* dotCMS Twitter plugin by ISAAC - The Full Service Internet Agency is licensed
* under a Creative Commons Attribution 3.0 Unported License
* - http://creativecommons.org/licenses/by/3.0/
* - http://www.geekyplugins.com/
*
* @copyright Copyright (c) 2017 ISAAC Software Solutions B.V. (http://www.isaac.nl)
*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.isaac.dotcms.twitter.pojo.CustomStatus;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Uses Twitter4J http://twitter4j.org/en/index.html
 * @author Danny Gloudemans
 *
 */
public class TwitterUtil {

	private Twitter twitter;

	public TwitterUtil(String twitterConsumerKey, String twitterSecretConsumerKey, String twitterAccessToken, String twitterSecretAccessToken) {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true) //Added for extra logging
		    .setOAuthConsumerKey(twitterConsumerKey)
		    .setOAuthConsumerSecret(twitterSecretConsumerKey)
		    .setOAuthAccessToken(twitterAccessToken)
		    .setOAuthAccessTokenSecret(twitterSecretAccessToken);
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
	}

	/**
	 * Get tweets from current User
	 * @param count
	 * @return
	 */
	public List<CustomStatus> getUserTimeline(int count) {
		List<CustomStatus> statuses = new ArrayList<>();

		try {
			statuses = formatStatus(twitter.getUserTimeline(new Paging(1, count)));
		} catch (TwitterException e) {
			throw new RuntimeException(e.toString(), e);
		}
		return statuses;
	}

	/**
	 * Get tweets for given screenname User
	 * @param count
	 * @return
	 */
	public List<CustomStatus> getUserTimeline(String screenname, int count) {
		List<CustomStatus> statuses = new ArrayList<>();

		try {
			statuses = formatStatus(twitter.getUserTimeline(screenname, new Paging(1, count)));
		} catch (TwitterException e) {
			throw new RuntimeException(e.toString(), e);
		}
		return statuses;
	}

	public List<CustomStatus> searchMultipleQueries(String query, int count) {
		List<CustomStatus> tempStatusList = new ArrayList<>();

		//Get a list of Status based on search
		tempStatusList.addAll(search(query));

		//This will remove all the duplicates
		Map<Long, CustomStatus> map = new HashMap<>();
		for (CustomStatus s : tempStatusList) {
			Long key = s.getId();
		     if (!map.containsKey(key)) {
		          map.put(key, s);
		     }
		}

		//Convert to ArrayList and sort on date
		List<CustomStatus> uniqueStatus = new ArrayList<>(map.values());
		Collections.sort(uniqueStatus, new StatusDateComparator());

		return uniqueStatus;
	}

	public List<CustomStatus> search(String from) {
		try {
		    Query query = new Query(from);
		    QueryResult result;

		    result = twitter.search(query);
		    List<CustomStatus> tweets = formatStatus(result.getTweets());
		    return tweets;

		} catch (TwitterException te) {
			throw new RuntimeException(te.toString(), te);
		}
	}

	private List<CustomStatus> formatStatus(List<Status> tweets) {
		List<CustomStatus> newTweets = new ArrayList<>();

		for(Status status : tweets) {
			newTweets.add(new CustomStatus(status));
		}
		return newTweets;
	}
}

class StatusDateComparator implements Comparator<CustomStatus> {
    @Override
	public int compare(CustomStatus p, CustomStatus q) {
        if (p.getCreatedAt().before(q.getCreatedAt())) {
            return -1;
        } else if (p.getCreatedAt().after(q.getCreatedAt())) {
            return 1;
        } else {
            return 0;
        }
    }
}

