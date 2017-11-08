package nl.isaac.dotcms.twitter.pojo;

/**
* dotCMS Twitter plugin by ISAAC - The Full Service Internet Agency is licensed
* under a Creative Commons Attribution 3.0 Unported License
* - http://creativecommons.org/licenses/by/3.0/
* - http://www.geekyplugins.com/
*
* @copyright Copyright (c) 2017 ISAAC Software Solutions B.V. (http://www.isaac.nl)
*/

import java.util.Date;

import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Place;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.UserMentionEntity;

public class CustomStatus {

	private Date createdAt;
    private long id;
    private String id_str;
    private String text;
    private String source;
    private boolean isTruncated;
    private long inReplyToStatusId;
    private long inReplyToUserId;
    private boolean isFavorited;
    private String inReplyToScreenName;
    private GeoLocation geoLocation = null;
    private Place place = null;
    private long retweetCount;
    private boolean isPossiblySensitive;
    private boolean isRetweet;
    private boolean isRetweetedByMe;
    private RateLimitStatus rateLimitStatus;
    private int accessLevel;
    private User user;

    private long[] contributorsIDs;

    private Status retweetedStatus;
    private UserMentionEntity[] userMentionEntities;
    private URLEntity[] urlEntities;
    private HashtagEntity[] hashtagEntities;
    private MediaEntity[] mediaEntities;
    private long currentUserRetweetId = -1L;

    public CustomStatus(Status status) {
    	this.createdAt = status.getCreatedAt();
    	this.id = status.getId();
    	this.id_str = String.valueOf(status.getId());
    	this.text = status.getText();
    	this.source = status.getSource();
    	this.isTruncated = status.isTruncated();
    	this.inReplyToStatusId = status.getInReplyToStatusId();
    	this.inReplyToUserId = status.getInReplyToUserId();
    	this.isFavorited = status.isFavorited();
    	this.inReplyToScreenName = status.getInReplyToScreenName();
    	this.geoLocation = status.getGeoLocation();
    	this.place = status.getPlace();
    	this.retweetCount = status.getRetweetCount();
    	this.isPossiblySensitive = status.isPossiblySensitive();

    	this.contributorsIDs = status.getContributors();

    	this.retweetedStatus = status.getRetweetedStatus();
    	this.userMentionEntities = status.getUserMentionEntities();
    	this.urlEntities = status.getURLEntities();
    	this.hashtagEntities = status.getHashtagEntities();
    	this.mediaEntities = status.getMediaEntities();
    	this.currentUserRetweetId = status.getCurrentUserRetweetId();

    	this.isRetweet = status.isRetweet();
    	this.isRetweetedByMe = status.isRetweetedByMe();

    	this.rateLimitStatus = status.getRateLimitStatus();
    	this.accessLevel = status.getAccessLevel();
    	this.user = status.getUser();
    }

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getId_str() {
		return id_str;
	}

	public void setId_str(String id_str) {
		this.id_str = id_str;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public boolean isTruncated() {
		return isTruncated;
	}

	public void setTruncated(boolean isTruncated) {
		this.isTruncated = isTruncated;
	}

	public long getInReplyToStatusId() {
		return inReplyToStatusId;
	}

	public void setInReplyToStatusId(long inReplyToStatusId) {
		this.inReplyToStatusId = inReplyToStatusId;
	}

	public long getInReplyToUserId() {
		return inReplyToUserId;
	}

	public void setInReplyToUserId(long inReplyToUserId) {
		this.inReplyToUserId = inReplyToUserId;
	}

	public boolean isFavorited() {
		return isFavorited;
	}

	public void setFavorited(boolean isFavorited) {
		this.isFavorited = isFavorited;
	}

	public String getInReplyToScreenName() {
		return inReplyToScreenName;
	}

	public void setInReplyToScreenName(String inReplyToScreenName) {
		this.inReplyToScreenName = inReplyToScreenName;
	}

	public GeoLocation getGeoLocation() {
		return geoLocation;
	}

	public void setGeoLocation(GeoLocation geoLocation) {
		this.geoLocation = geoLocation;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public long getRetweetCount() {
		return retweetCount;
	}

	public void setRetweetCount(long retweetCount) {
		this.retweetCount = retweetCount;
	}

	public boolean isPossiblySensitive() {
		return isPossiblySensitive;
	}

	public void setPossiblySensitive(boolean isPossiblySensitive) {
		this.isPossiblySensitive = isPossiblySensitive;
	}

	public long[] getContributors() {
		return contributorsIDs;
	}

	public void setContributorsIDs(long[] contributorsIDs) {
		this.contributorsIDs = contributorsIDs;
	}

	public Status getRetweetedStatus() {
		return retweetedStatus;
	}

	public void setRetweetedStatus(Status retweetedStatus) {
		this.retweetedStatus = retweetedStatus;
	}

	public UserMentionEntity[] getUserMentionEntities() {
		return userMentionEntities;
	}

	public void setUserMentionEntities(UserMentionEntity[] userMentionEntities) {
		this.userMentionEntities = userMentionEntities;
	}

	public URLEntity[] getURLEntities() {
		return urlEntities;
	}

	public void setUrlEntities(URLEntity[] urlEntities) {
		this.urlEntities = urlEntities;
	}

	public HashtagEntity[] getHashtagEntities() {
		return hashtagEntities;
	}

	public void setHashtagEntities(HashtagEntity[] hashtagEntities) {
		this.hashtagEntities = hashtagEntities;
	}

	public MediaEntity[] getMediaEntities() {
		return mediaEntities;
	}

	public void setMediaEntities(MediaEntity[] mediaEntities) {
		this.mediaEntities = mediaEntities;
	}

	public long getCurrentUserRetweetId() {
		return currentUserRetweetId;
	}

	public void setCurrentUserRetweetId(long currentUserRetweetId) {
		this.currentUserRetweetId = currentUserRetweetId;
	}

	public RateLimitStatus getRateLimitStatus() {
		return rateLimitStatus;
	}

	public int getAccessLevel() {
		return accessLevel;
	}

	public User getUser() {
		return user;
	}

	public boolean isRetweet() {
		return isRetweet;
	}

	public boolean isRetweetedByMe() {
		return isRetweetedByMe;
	}

	public long[] getContributorsIDs() {
		return contributorsIDs;
	}

	public URLEntity[] getUrlEntities() {
		return urlEntities;
	}

	public void setRetweet(boolean isRetweet) {
		this.isRetweet = isRetweet;
	}

	public void setRetweetedByMe(boolean isRetweetedByMe) {
		this.isRetweetedByMe = isRetweetedByMe;
	}

	public void setRateLimitStatus(RateLimitStatus rateLimitStatus) {
		this.rateLimitStatus = rateLimitStatus;
	}

	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
