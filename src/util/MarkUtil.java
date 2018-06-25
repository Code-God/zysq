package util;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

public class MarkUtil {

	public static String getUserRootPath(HttpServletRequest request) {
		String rootPath = request.getSession().getServletContext().getRealPath(
				"/swfupload/files/" + getUserKey(request) + "/pics");
		File f = new File(rootPath);
		if (!f.exists()) {
			f.mkdirs();
		}
		return rootPath;
	}

	/**
	 * 用户key
	 * 
	 * @param request
	 * @return
	 */
	public static String getUserKey(HttpServletRequest request) {
		request.getSession().getAttributeNames();
		// 用户唯一KEY
		return request.getSession().getAttribute("user_id").toString();
	}
}
