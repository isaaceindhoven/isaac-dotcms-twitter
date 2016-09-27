package nl.isaac.dotcms.twitter.viewtool;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.isaac.dotcms.twitter.pojo.CustomStatus;
import nl.isaac.dotcms.twitter.pojo.TwitterConfiguration;
import nl.isaac.dotcms.twitter.util.TwitterSettings;
import nl.isaac.dotcms.twitter.util.TwitterUtil;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.view.context.ViewContext;
import org.apache.velocity.tools.view.tools.ViewTool;

public class TwitterViewTool implements ViewTool {
	
	/* http://codegolf.stackexchange.com/questions/464/shortest-url-regex-match-in-javascript
	 * http://jsfiddle.net/MikeGrace/gsJyr/
	(^|\s)                            : ensure that we are not matching an url 
	                                    embeded in an other string
	(https?:\/\/)?                    : the http or https schemes (optional)
	[\w-]+(\.[\w-]+)+\.?              : domain name with at least two components;
	                                    allows a trailing dot
	(:\d+)?                           : the port (optional)
	(\/\S*)?                          : the path (optional)
	*/
	private static final String url = "(^|\\s)((https?://)[\\w-]+(\\.[\\w-]+)+\\.?(:\\d+)?(/\\S*)?)";
	private static final String tweep = "(@\\w*)";
	private static final String hashtag = "(#([a-zA-Z0-9_]*))";
	
	
	private HttpServletRequest request;
	private Context context;


	@Override
	public void init(Object obj) {
		ViewContext context = (ViewContext) obj;
		this.context = (Context) obj;
	    this.request = context.getRequest();
	}
	
	/**
	 * Retrieves the timeline for the configured user with a maximum of 10 tweets.
	 * 
	 * @return List of CustomStatus objects.
	 */
	public List<CustomStatus> getTimeLine() {
		return getTimeLine(10, null);
	}
	
	/**
	 * Retrieves the timeline for the configured user with the passed number of tweets passed via <count>
	 * 
	 * @param count Number of tweets to retrieve.
	 * @return List of CustomStatus objects.
	 */
	public List<CustomStatus> getTimeLine(int count) {
		return getTimeLine(count, null);
	}
	
	/**
	 * Retrieves the timeline for the user with screenName with a
	 * 
	 * @param count Number of tweets to retrieve.
	 * @param screenName Screen name of the user for which the timeline should be retrieved.
	 * @return List of CustomStatus objects.
	 */
	public List<CustomStatus> getTimeLine(int count, String screenName) {
		List<CustomStatus> statussen = new ArrayList<CustomStatus>();
		TwitterSettings twitterSettings = new TwitterSettings();
		
		TwitterConfiguration twitterConfiguration = twitterSettings.getTwitterConfiguration(request);
		TwitterUtil twitterUtil = new TwitterUtil(twitterConfiguration.getTwitterConsumerKey(), twitterConfiguration.getTwitterSecretConsumerKey(), twitterConfiguration.getTwitterAccessToken(), twitterConfiguration.getTwitterSecretAccessToken());
		
		if(screenName != null) {
			statussen = twitterUtil.getUserTimeline(screenName, count);	
		} else {
			statussen = twitterUtil.getUserTimeline(count);			
		}

		return statussen;
	}
	
	/**
	 * This method takes a CustomStatus object and retrieves the tweet text and formats it.
	 * It adds the text 'RT' if it is a retweet and links for all urls, tweeps and hashtags in
	 * the tweet. 
	 * 
	 * See formatText() for details on the added links.
	 * 
	 * @param status CustomStatus object for which the formatted tweet needs to be returned.
	 * @return String containing the formatted tweet.
	 */
	public String formatTweetText(CustomStatus status) {
		if(status.isRetweet()) {
			return "RT " + formatText(status.getRetweetedStatus().getText());
		} else {
			return formatText(status.getText());
		}
	}
	
	/**
	 * Method to add url, tweep and hashtag html links.
	 * 
	 * @param text String containing the tweet text to format.
	 * @return String with added links with nofollow and target _blank attributes.
	 */
    public String formatText(String text) {
    	text = StringEscapeUtils.escapeHtml(text);
    	
        return text.replaceAll(url, "$1<a rel='nofollow' href='$2' target='_blank'>$2</a>")
            		.replaceAll(tweep, "<a rel='nofollow' href='http://twitter.com/$1' target='_blank'>$1</a>")
            		.replaceAll(hashtag, "<a rel='nofollow' href='https://twitter.com/hashtag/$2' target='_blank'>$1</a>");
    }
	
}
