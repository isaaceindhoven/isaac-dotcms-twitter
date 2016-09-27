# dotCMS Twitter OAuth

In mid 2013 Twitter switched off their old API and replaced it by a brand new one: [v1.1](https://dev.twitter.com/rest/public). Even though the new API has many cool new features it does pose a problem for dotCMS site builders because it uses [OAuth](https://dev.twitter.com/oauth/overview/faq) to authenticate websites that want to use the API. This means that website developers need to have a server side component that does all the OAuth protocol handling. This plugin provides this functionality in a multi-tenant environment.

Previous releases of this plugin for older dotCMS versions can be found [here](../../releases).

## Features

These are the plugin's key features: 

* The OAuth keys can be managed from the admin interface.
* These keys are defined per host to limit the effect of blocked keys to the host that caused the blocking.
* The plugin is available for the new dynamic (OSGi) based one.
* For backwards compatibility the current version uses the old v1.0 URL structure. This reduces the amount of rework that needs to be done to existing implementations. The new paths will be included in a future version.
* Currently only the *user_timeline* and *search* API calls have been implemented.

## Installation

To use it, you obviously first need to install the plugin. To install it take these steps:

* Clone this repository.
* Open the console and go the the folder containing pom.xml
* Execute the following maven command: **mvn clean package**
* The build should succeed and a folder named "target" will be created.
* Open the "target" folder and check if the **.jar** file exists.
* Open dotCMS and go to the dotCMS Dynamic Plugins page by navigating to "System" > "Dynamic Plugins".
* Click on "Exported Packages" and add these packages to the list (make sure to add a comma to the last package in the existing list):

```java
com.dotmarketing.plugin.business,
com.dotmarketing.portlets.fileassets.business,
com.dotmarketing.portlets.languagesmanager.business,
com.dotmarketing.portlets.languagesmanager.model,
com.dotmarketing.beans,
com.liferay.portal
```

* Click on "Save Packages".
* Click on "Upload Plugin" and select the .jar file located in the "/target" folder.
* Click on "Upload Plugin" and the plugin should install and automatically start.
* To check if the plugin is working, visit the following URL and replace **your-url-here** with your own site address: **http://your-url-here/app/servlets/monitoring/isaac-dotcms-twitter**

## Configuration and usage

#### Getting the OAuth keys from twitter

You need a few keys to get the plugin to work:

* Twitter consumer key
* Twitter secret consumer key
* Twitter access token
* Twitter secret access token

These can all be created in Twitter's development website. Follow these steps:

* Go to [dev.twitter.com](https://dev.twitter.com) and log in with your twitter credentials.
* Go to "My Applications" > "Manage Your Apps".
* Create a new application and name it according to the host you want to embed the twitter information on. Each dotCMS host should have its own Twitter application. This prevents one "bad host" from damaging the other hosts.
* You'll find the keys on the detail page of your newly created application.

#### Storing the keys in dotCMS

The plugin automatically created four new fields in the host definition. Open the host by navigating to "System" > "Sites" and add the keys to the fields found below.

![](https://cloud.githubusercontent.com/assets/10976988/18206388/c0cb6a02-7126-11e6-8a38-a175ab5d2406.png)

#### Write the JavaScript code

Currently the plugin supports two of the v1.1 API calls (using the old v1.0 URL pattern):

#### [user_timeline](https://dev.twitter.com/rest/reference/get/statuses/user_timeline)
You can call this using the URL below.

```
/1/statuses/user_timeline.json?screen_name={SCREENNAME}&count={COUNT}&callback={JSONP_METHOD}
```

#### [search](https://dev.twitter.com/docs/api/1.1/get/search/tweets)
You can call this using this URL:

```
/search.json?&from=from:{SEARCH} OR from:{SEARCH} OR from:{SEARCH}&count={COUNT}&callback={JSONP_METHOD}
```

## API

The API can be found [here](https://github.com/isaaceindhoven/DotCMS-Twitter/wiki/API-Documentation).


## Meta

[ISAAC - 100% Handcrafted Internet Solutions](https://www.isaac.nl) – [@ISAAC](https://twitter.com/isaaceindhoven) – [info@isaac.nl](mailto:info@isaac.nl)

Distributed under the [Creative Commons Attribution 3.0 Unported License](https://creativecommons.org/licenses/by/3.0/).
