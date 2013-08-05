package com.ambow.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class StaticUtil {

	/*
	 * public class MyClass ... { static AType aStaticInMyClass = null; static
	 * AnotherType anotherStaticInMyClass = null;
	 * 
	 * static { new StaticUtil.Initializer(MyClass.class) { public void init() { //
	 * initialize static members here aStaticInMyClass = ...;
	 * anotherStaticInMyClass = ...; } }; } ... }
	 */

	static Map initializers = Collections.synchronizedMap(new HashMap());

	public abstract static class Initializer {
		public Initializer(Class clazz) {
			add(clazz, this);
			init();
		}

		public abstract void init();
	}

	public static void add(Class clazz, Initializer initializer) {
		initializers.put(clazz, initializer);
	}

	public static void remove(Class clazz) {
		initializers.remove(clazz);
	}

	public static void reinitialize() {
		Iterator iter = initializers.values().iterator();
		while (iter.hasNext()) {
			Initializer initializer = (Initializer) iter.next();
			initializer.init();
		}
	}
}
