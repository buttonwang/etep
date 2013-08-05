/* 
 * BusinessException.java
 * 
 * Created on 2007-4-11
 * 
 * Copyright(C) 2007, by Ambow Research&Development Branch.
 * 
 * Original Author: Peng Qing
 * Contributor(s):
 * 
 * Changes 
 * -------
 * $Log: BusinessException.java,v $
 * Revision 1.1  2008/05/09 05:57:52  lixin
 * ���⿼����Ŀ
 *
 * Revision 1.1  2007/07/19 06:53:09  pengqing
 *
 */
package com.ambow.core.exception;

import static com.ambow.core.configuration.Constants.ERROR_BUNDLE_KEY;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.NestedRuntimeException;

import com.ambow.core.configuration.Constants;

/**
 * 业务异常基类. 带有错误代码与错误信息. 用户在生成异常时既可以直接赋予错误代码与错误信息. 也可以只赋予错误代码与错误信息参数.
 * 如ErrorCode=ORDER.LACK_INVENTORY ,errorArg=without EJB
 * 系统会从errors.properties中查出 ORDER.LACK_INVENTORY=Book <{0}> lack of inventory
 * 最后返回错误信息为 Book <without EJB> lack of inventory.
 * 
 * @author Peng Qing
 */
@SuppressWarnings("serial")
public class BusinessException extends NestedRuntimeException {

	/**
	 * 错误代码,默认为未知错误
	 */
	private String errorCode = "UNKNOW_ERROR";

	/**
	 * 错误信息中的参数
	 */
	protected String[] errorArgs = null;

	/**
	 * 兼容纯错误信息，不含error code,errorArgs的情况
	 */
	private String errorMessage = null;

	/**
	 * 错误信息的i18n ResourceBundle. 默认Properties文件名定义于{@link Constants#ERROR_BUNDLE_KEY}
	 */
	static private ResourceBundle rb = ResourceBundle.getBundle(
			ERROR_BUNDLE_KEY, LocaleContextHolder.getLocale());

	public BusinessException() {
		super("UNKNOW_ERROR");
	}

	public BusinessException(String errorCode, String... errorArgs) {
		super(errorCode);
		this.errorCode = errorCode;
		this.errorArgs = errorArgs;
	}

	public BusinessException(String errorCode, Throwable t, String... errorArgs) {
		super(errorCode, t);
		this.errorCode = errorCode;
		this.errorArgs = errorArgs;
	}

	public BusinessException(String errorMessage) {
		super("UNKNOW_ERROR");
		this.errorMessage = errorMessage;
	}

	public BusinessException(String errorMessage, Throwable t) {
		super("UNKNOW_ERROR", t);
		this.errorMessage = errorMessage;
	}

	/**
	 * 获得出错信息. 读取i18N properties文件中Error Code对应的message,并组合参数获得i18n的出错信息.
	 */
	public String getMessage() {
		if (StringUtils.isNotBlank(this.errorMessage)) {
			return errorMessage;
		}

		// 用errorCode查询Properties文件获得出错信息
		String message;
		try {
			message = rb.getString(errorCode);
		} catch (MissingResourceException mse) {
			message = "ErrorCode is: " + errorCode
					+ ", but can't get the message of the Error Code";
		}

		// 将出错信息中的参数代入到出错信息中
		if (errorArgs != null)
			message = MessageFormat.format(message, (Object[]) errorArgs);

		return message;

	}

	public String getErrorCode() {
		return errorCode;
	}

}
