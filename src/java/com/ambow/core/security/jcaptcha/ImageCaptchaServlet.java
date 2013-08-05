package com.ambow.core.security.jcaptcha;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * Servlet generates CAPTCHA jpeg images based on the JCAPTCHA package. It's
 * configured via spring, and requires a ImageCaptchaService bean with the
 * id=imageCaptchaService
 *
 * @author Jason Thrasher
 */
public class ImageCaptchaServlet extends HttpServlet {
	private static final long serialVersionUID = 3258417209566116145L;

	private String captchaServiceName = "imageCaptchaService";

	public void init(ServletConfig servletConfig) throws ServletException {
		if (StringUtils.isNotBlank(servletConfig
				.getInitParameter("captchaServiceName"))) {
			captchaServiceName = servletConfig
					.getInitParameter("captchaServiceName");
		}

		super.init(servletConfig);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		byte[] captchaChallengeAsJpeg = null;
		// the output stream to render the captcha image as jpeg into
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		try {
			// get the image captcha service defined via the SpringFramework
			ApplicationContext ctx = WebApplicationContextUtils
					.getWebApplicationContext(getServletContext());
			Object bean = ctx.getBean(captchaServiceName);
			ImageCaptchaService imageCaptchaService = (ImageCaptchaService) bean;

			// get the session id that will identify the generated captcha.
			// the same id must be used to validate the response, the session id
			// is a good candidate!
			String captchaId = request.getSession().getId();
			// call the ImageCaptchaService getChallenge method
			BufferedImage challenge = imageCaptchaService
					.getImageChallengeForID(captchaId, request.getLocale());

			// a jpeg encoder
			JPEGImageEncoder jpegEncoder = JPEGCodec
					.createJPEGEncoder(jpegOutputStream);
			jpegEncoder.encode(challenge);
		} catch (IllegalArgumentException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		} catch (CaptchaServiceException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

		// flush it in the response
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		ServletOutputStream responseOutputStream = response.getOutputStream();
		responseOutputStream.write(captchaChallengeAsJpeg);
		responseOutputStream.flush();
		responseOutputStream.close();
	}
}
