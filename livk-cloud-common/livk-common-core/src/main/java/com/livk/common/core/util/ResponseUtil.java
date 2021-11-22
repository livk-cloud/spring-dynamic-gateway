package com.livk.common.core.util;

import com.livk.common.core.result.R;
import lombok.experimental.UtilityClass;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <p>
 * ResponseUtil
 * </p>
 *
 * @author livk
 * @date 2021/11/2
 */
@UtilityClass
public class ResponseUtil {

	public HttpServletResponse getResponse() {
		var requestAttributes = RequestContextHolder.getRequestAttributes();
		var servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
		assert servletRequestAttributes != null;
		return servletRequestAttributes.getResponse();
	}

	public void out(String message) {
		var response = ResponseUtil.getResponse();
		assert response != null;
		ResponseUtil.out(response, message);
	}

	/**
	 * 根据response写入返回值
	 * @param response response
	 * @param message 写入的信息
	 */
	public void out(HttpServletResponse response, String message) {
		var r = R.error(message);
		try (PrintWriter out = response.getWriter()) {
			out.print(JacksonUtil.toJson(r));
			out.flush();
		}
		catch (IOException exception) {
			exception.printStackTrace();
		}
	}

}
