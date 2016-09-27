package nl.isaac.dotcms.twitter.util;

/**
* dotCMS Twitter plugin by ISAAC - The Full Service Internet Agency is licensed 
* under a Creative Commons Attribution 3.0 Unported License
* - http://creativecommons.org/licenses/by/3.0/
* - http://www.geekyplugins.com/
* 
* @copyright Copyright (c) 2013 ISAAC Software Solutions B.V. (http://www.isaac.nl)
*/

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.tools.view.context.ViewContext;
import org.apache.velocity.tools.view.tools.ViewTool;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.dotmarketing.beans.Host;
import com.dotmarketing.business.APILocator;
import com.dotmarketing.business.Role;
import com.dotmarketing.business.web.WebAPILocator;
import com.dotmarketing.exception.DotDataException;
import com.dotmarketing.exception.DotSecurityException;
import com.dotmarketing.util.Logger;
import com.dotmarketing.util.WebKeys;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.User;

public class RequestUtil implements ViewTool {
	private HttpServletRequest request;
	
	/**
	 * Only dotCMS should call this constructor, followed by an init()
	 */
	public RequestUtil() {};
	
	public RequestUtil(HttpServletRequest request) {
		this.request = request;
	}
	
	public void init(Object initData) {
		ViewContext context = (ViewContext) initData;
	    this.request = context.getRequest();		
	}
	
	private boolean isBackendLogin() {
		try {
			User backendUser = WebAPILocator.getUserWebAPI().getLoggedInUser(request);
			return backendUser != null && backendUser.isActive();
		} catch (Exception e) {
			Logger.warn(this, "Exception while checking for Admin", e);
			return false;
		}
	}
	
	
	public boolean isAdministratorLoggedIn() {
		try {
			User backendUser = WebAPILocator.getUserWebAPI().getLoggedInUser(request);
			Role adminRole = APILocator.getRoleAPI().loadCMSAdminRole();
			return APILocator.getRoleAPI().doesUserHaveRole(backendUser, adminRole);
		} catch (Exception e) {
			Logger.warn(this, "Exception while checking for Admin", e);
			return false;
		}
	}
	
	public boolean isLiveMode() {
		return !(isEditMode() || isPreviewMode()); 
	}
	
	public boolean isEditMode() {
		Object EDIT_MODE_SESSION = request.getSession().getAttribute(com.dotmarketing.util.WebKeys.EDIT_MODE_SESSION);
		if(EDIT_MODE_SESSION != null) {
			return Boolean.valueOf(EDIT_MODE_SESSION.toString());
		}
		return false; 
	}
	
	public boolean isPreviewMode() {
		Object PREVIEW_MODE_SESSION = request.getSession().getAttribute(com.dotmarketing.util.WebKeys.PREVIEW_MODE_SESSION);
		if(PREVIEW_MODE_SESSION != null) {
			return Boolean.valueOf(PREVIEW_MODE_SESSION.toString());
		}
		return false; 
	}
	
	public boolean isBackendViewOfPage() {
		return isBackendLogin();
	}
	
	public boolean isEditOrPreviewMode() {
		return isEditMode() || isPreviewMode();
	}
	
	public Host getCurrentHost() {
		try {
			return WebAPILocator.getHostWebAPI().getCurrentHost(request);
		} catch (PortalException e) {
			throw new RuntimeException(e);
		} catch (SystemException e) {
			throw new RuntimeException(e);
		} catch (DotDataException e) {
			throw new RuntimeException(e);
		} catch (DotSecurityException e) {
			throw new RuntimeException(e);
		}
	}

	public String getLanguage() {
		String languageId = (String) request.getSession().getAttribute(WebKeys.HTMLPAGE_LANGUAGE);
		if(languageId == null) {
			Logger.warn(this, "Can't detect language, returning default language");
			languageId = Long.valueOf(APILocator.getLanguageAPI().getDefaultLanguage().getId()).toString();
		}
		
		return languageId;
	}
	
	public static Integer getSelectedLanguage(HttpServletRequest request) {
		return (Integer)request.getSession().getAttribute(WebKeys.LANGUAGE);
	}
	
	public <T> T marshalRequestJsonToObject(Class<T> type) {
		return marshalRequestJsonToObject(request, type);
	}
	
	/**
	 * 
	 * @param request The request object
	 * @param type The class type to marshal the JSON to
	 * @return The marshaled object
	 */
	public static <T> T marshalRequestJsonToObject(HttpServletRequest request, Class<T> type) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(request.getReader(), type);
		} catch (JsonParseException e) {
			throw new RuntimeException("Cannot parse JSON from request", e);
		} catch (JsonMappingException e) {
			throw new RuntimeException("Cannot map JSON from request to a " + type, e);
		} catch (IOException e) {
			throw new RuntimeException("Unknown exception while parsing JSON from request", e);
		}
	}
	
	public Cookie getCookieByName(String name) {
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if (cookie.getName().equalsIgnoreCase(name)) {
					return cookie;
				}
			}	
		}
		return null;
	}
	
	public void setCookie(String name, String value, int maxAge, String domain, String path, HttpServletResponse response) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAge);
		cookie.setDomain(domain);
		cookie.setPath(path);
		response.addCookie(cookie);
	}
}
