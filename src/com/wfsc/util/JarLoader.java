package com.wfsc.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.NoSuchElementException;

import sun.misc.Resource;
import sun.misc.URLClassPath;

/**
 * Jar 文件装载器
 * 
 * @author York
 */
public class JarLoader extends ClassLoader {

	/**
	 * URL目录
	 */
	private URLClassPath ucp;

	/**
	 * 类装载器
	 */
	private ClassLoader parent;

	/**
	 * 构造实例
	 * 
	 * @param archiveNames
	 * @throws FileNotFoundException
	 */
	public JarLoader(String[] archiveNames) throws FileNotFoundException {
		this(archiveNames, null);
	}

	/**
	 * 构造实例
	 * 
	 * @param archiveNames
	 * @param parent
	 * @throws FileNotFoundException
	 */
	public JarLoader(String[] archiveNames, ClassLoader parent) throws FileNotFoundException {
		super(null);
		this.parent = parent;
		URL[] urls = new URL[archiveNames.length];
		for (int i = 0; i < archiveNames.length; i++) {
			try {
				urls[i] = new URL("file:" + archiveNames[i]);
			} catch (MalformedURLException e) {
				throw new FileNotFoundException(archiveNames[i]);
			}
		}
		ucp = new URLClassPath(urls);
	}

	/**
	 * 查找类定义
	 * 
	 * @see java.lang.ClassLoader#findClass(java.lang.String)
	 */
	protected Class findClass(final String name) throws ClassNotFoundException {
		String path = name.replace('.', '/').concat(".class");
		Resource res = ucp.getResource(path, false);
		if (res != null) {
			try {
				return defineClass(name, res);
			} catch (IOException e) {
				throw new ClassNotFoundException(name, e);
			}
		}
		if (parent != null) {
			return parent.loadClass(name);
		} else {
			throw new ClassNotFoundException(name);
		}
	}

	/**
	 * 查找类定义
	 * 
	 * @param name a
	 * @param res a
	 * @return a
	 * @throws IOException a
	 */
	private Class defineClass(String name, Resource res) throws IOException {
		// Now read the class bytes and define the class
		byte[] b = res.getBytes();
		return defineClass(name, b, 0, b.length);
	}

	/**
	 * 工具方法
	 * 
	 * @see java.lang.ClassLoader#findResource(java.lang.String)
	 */
	public URL findResource(final String name) {
		URL url = ucp.findResource(name, true);
		return url != null ? ucp.checkURL(url) : null;
	}

	/**
	 * 工具方法
	 * 
	 * @see java.lang.ClassLoader#findResources(java.lang.String)
	 */
	public Enumeration findResources(final String name) throws IOException {
		final Enumeration e = ucp.findResources(name, true);
		return new Enumeration() {

			private URL url = null;

			private boolean next() {
				if (url != null) {
					return true;
				}
				do {
					URL u = (URL) ((!e.hasMoreElements()) ? null : e.nextElement());
					if (u == null)
						break;
					url = ucp.checkURL(u);
				} while (url == null);
				return url != null;
			}

			public Object nextElement() {
				if (!next()) {
					throw new NoSuchElementException();
				}
				URL u = url;
				url = null;
				return u;
			}

			public boolean hasMoreElements() {
				return next();
			}
		};
	}

	/**
	 * 工具方法
	 * 
	 * @see java.lang.ClassLoader#getResource(java.lang.String)
	 */
	public URL getResource(String name) {
		URL url = findResource(name);
		if (url == null && parent != null) {
			url = parent.getResource(name);
		}
		return url;
	}

	/**
	 * 工具方法
	 * 
	 * @see java.lang.ClassLoader#getResourceAsStream(java.lang.String)
	 */
	public InputStream getResourceAsStream(String name) {
		URL url = getResource(name);
		try {
			return url != null ? url.openStream() : null;
		} catch (IOException e) {
			return null;
		}
	}
}
