/*
 * MD5.java
 *
 * Created on 2007-9-19
 *
 * Copyright(C) 2007, by Ambow Develope & Research Branch.
 *
 * Original Author: gonglijian
 * Contributor(s):
 *
 * Changes
 * -------
 * $Log: MD5.java,v $
 * Revision 1.1  2008/05/09 05:57:52  lixin
 * ���⿼����Ŀ
 *
 * Revision 1.1  2007/09/19 07:06:40  gonglijian
 * *** empty log message ***
 *
 */
package com.ambow.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.ambow.core.exception.BusinessException;

public class MD5 {

	public static String crypt(String str) {
		if (str == null || str.length() == 0) {
			throw new IllegalArgumentException(
					"String to encript cannot be null or zero length");
		}

		StringBuffer hexString = new StringBuffer();

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] hash = md.digest();

			for (int i = 0; i < hash.length; i++) {
				if ((0xff & hash[i]) < 0x10) {
					hexString.append("0"
							+ Integer.toHexString((0xFF & hash[i])));
				} else {
					hexString.append(Integer.toHexString(0xFF & hash[i]));
				}
			}
		} catch (NoSuchAlgorithmException e) {
			throw new BusinessException("" + e);
		}

		return hexString.toString();
	}
}
