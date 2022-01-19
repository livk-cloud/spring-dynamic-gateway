package com.livk.common.core.util;

import lombok.experimental.UtilityClass;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * RequestUtil
 * </p>
 *
 * @author livk
 * @date 2021/11/2
 */
@UtilityClass
public class RequestUtil {

	public HttpServletRequest getRequest() {
		var requestAttributes = RequestContextHolder.getRequestAttributes();
		var servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
		Assert.notNull(servletRequestAttributes,"attributes not null!");
		return servletRequestAttributes.getRequest();
	}

	public HttpSession getSession() {
		return RequestUtil.getRequest().getSession();
	}

	public String getParameter(String name) {
		return RequestUtil.getRequest().getParameter(name);
	}

	public String getHeader(String headerName) {
		return RequestUtil.getRequest().getHeader(headerName);
	}

	public Map<String, String> getHeaders() {
		var request = RequestUtil.getRequest();
		var map = new LinkedHashMap<String, String>();
		var enumeration = request.getHeaderNames();
		if (enumeration != null) {
			while (enumeration.hasMoreElements()) {
				var key = enumeration.nextElement();
				var value = request.getHeader(key);
				map.put(key, value);
			}
		}
		return map;
	}

}
