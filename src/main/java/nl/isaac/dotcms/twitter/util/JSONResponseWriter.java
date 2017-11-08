package nl.isaac.dotcms.twitter.util;

/**
* dotCMS Twitter plugin by ISAAC - The Full Service Internet Agency is licensed
* under a Creative Commons Attribution 3.0 Unported License
* - http://creativecommons.org/licenses/by/3.0/
* - http://www.geekyplugins.com/
*
* @copyright Copyright (c) 2017 ISAAC Software Solutions B.V. (http://www.isaac.nl)
*/

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.dotcms.repackage.com.google.gson.Gson;

public class JSONResponseWriter {

	public static void writeObjectToResponse(String jsonPFunction, Object object, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");

		Gson gson = new Gson();
		String json = gson.toJson(object);
		json = jsonPFunction + "(" + json + ");";
		PrintWriter out = response.getWriter();
		out.print(json);
		out.close();
	}
}
