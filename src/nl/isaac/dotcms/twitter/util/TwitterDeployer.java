package nl.isaac.dotcms.twitter.util;

/**
* dotCMS Twitter plugin by ISAAC - The Full Service Internet Agency is licensed 
* under a Creative Commons Attribution 3.0 Unported License
* - http://creativecommons.org/licenses/by/3.0/
* - http://www.geekyplugins.com/
* 
* @copyright Copyright (c) 2013 ISAAC Software Solutions B.V. (http://www.isaac.nl)
*/

import org.apache.velocity.tools.view.tools.ViewTool;

public class TwitterDeployer implements ViewTool {

	@Override
	public void init(Object arg0) {
		TwitterFieldFactory twitterFieldFactory = new TwitterFieldFactory();
		twitterFieldFactory.createFieldsInHosts();
	}

}
