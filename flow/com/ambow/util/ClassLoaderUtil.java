package com.ambow.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.ambow.studyflow.exception.AsfException;

/**
 * provides centralized classloader lookup.
 */
public class ClassLoaderUtil {

	public static Class loadClass(String className) {
		try {
			return getClassLoader().loadClass(className);
		} catch (ClassNotFoundException e) {
			throw new AsfException("class not found '" + className + "'", e);
		}
	}

	public static ClassLoader getClassLoader() {
		return ClassLoaderUtil.class.getClassLoader();
	}

	public static InputStream getStream(String resource) {
		return getClassLoader().getResourceAsStream(resource);
	}

	public static Properties getProperties(String resource) {
		Properties properties = new Properties();
		try {
			properties.load(getStream(resource));
		} catch (IOException e) {
			throw new AsfException("couldn't load properties file '" + resource
					+ "'", e);
		}
		return properties;
	}

}
