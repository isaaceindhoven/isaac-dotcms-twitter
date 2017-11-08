package nl.isaac.dotcms.twitter.pojo;

/**
* dotCMS Twitter plugin by ISAAC - The Full Service Internet Agency is licensed
* under a Creative Commons Attribution 3.0 Unported License
* - http://creativecommons.org/licenses/by/3.0/
* - http://www.geekyplugins.com/
*
* @copyright Copyright (c) 2017 ISAAC Software Solutions B.V. (http://www.isaac.nl)
*/

public class TwitterConfiguration {

	private String twitterConsumerKey;
	private String twitterSecretConsumerKey;
	private String twitterAccessToken;
	private String twitterSecretAccessToken;

	public TwitterConfiguration(String twitterConsumerKey,
			String twitterSecretConsumerKey, String twitterAccessToken,
			String twitterSecretAccessToken) {
		super();
		this.twitterConsumerKey = twitterConsumerKey;
		this.twitterSecretConsumerKey = twitterSecretConsumerKey;
		this.twitterAccessToken = twitterAccessToken;
		this.twitterSecretAccessToken = twitterSecretAccessToken;
	}

	public String getTwitterConsumerKey() {
		return twitterConsumerKey;
	}
	public String getTwitterSecretConsumerKey() {
		return twitterSecretConsumerKey;
	}
	public String getTwitterAccessToken() {
		return twitterAccessToken;
	}
	public String getTwitterSecretAccessToken() {
		return twitterSecretAccessToken;
	}
}
