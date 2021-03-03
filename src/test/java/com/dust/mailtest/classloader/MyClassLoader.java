package com.dust.mailtest.classloader;

import java.io.InputStream;

public class MyClassLoader extends ClassLoader {
	
	@Override
	public Class<?> loadClass(String paramString) throws ClassNotFoundException {
		try {
			String filename = paramString.substring(paramString.lastIndexOf(".") + 1) + ".class";
			InputStream is = this.getClass().getResourceAsStream(filename);
			if (is == null) {
				return super.loadClass(paramString);
			}
			byte[] b = new byte[is.available()];
			is.read(b);
			return defineClass(paramString, b, 0, b.length);
		} catch (Exception e) {
			throw new ClassNotFoundException(paramString);
		}
		
	}
	
	@Override
	public String toString() {
		return "com.dust.mailtest.classloader.MyClassLoader";
	}
}
