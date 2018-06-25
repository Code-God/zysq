package util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class ZipUtil {

	protected static byte[] buf = new byte[1024];

	/**
	 * 私有构造函数防止被构建
	 */
	private ZipUtil() {
	}

	/**
	 * 遍历目录并添加文件.
	 * 
	 * @param jos - JAR 输出流
	 * @param file - 目录文件名
	 * @param pathName - ZIP中的目录名
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private static void recurseFiles(final JarOutputStream jos, final File file, final String pathName) throws IOException,
			FileNotFoundException {
		// 文件夹则往下遍历
		if (file.isDirectory()) {
			final String sPathName = pathName + file.getName() + "/";
			jos.putNextEntry(new JarEntry(sPathName));
			final String[] fileNames = file.list();
			if (fileNames != null) {
				for (int i = 0; i < fileNames.length; i++) {
					recurseFiles(jos, new File(file, fileNames[i]), sPathName);
				}
			}
		}
		// 读取文件到ZIP/JAR文件条目
		else {
			// 使用指定名称创建新的 ZIP/JAR 条目
			final JarEntry jarEntry = new JarEntry(pathName + file.getName());
			final FileInputStream fin = new FileInputStream(file);
			final BufferedInputStream in = new BufferedInputStream(fin);
			// 开始写入新的 ZIP 文件条目并将流定位到条目数据的开始处。
			jos.putNextEntry(jarEntry);
			int len;
			while ((len = in.read(buf)) >= 0) {
				// 将字节数组写入当前 ZIP 条目数据
				jos.write(buf, 0, len);
			}
			in.close();
			// 关闭当前 ZIP 条目并定位流以写入下一个条目
			jos.closeEntry();
		}
	}

	/**
	 * 创建 ZIP/JAR 文件.
	 * 
	 * @param directory - 要添加的目录
	 * @param zipFile - 保存的 ZIP 文件名
	 * @param zipFolderName - ZIP 中的路径名
	 * @param level - 压缩级别(0~9)
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void makeDirectoryToZip(final File directory, final File zipFile, final String zipFolderName,
			final int level) throws IOException, FileNotFoundException {
		FileOutputStream fos = null;
		try {
			// 输出文件流
			fos = new FileOutputStream(zipFile);
		} catch (final Exception e) {
			// 建立打包后的空文件
			new File(zipFile.getParent()).mkdirs();
			zipFile.createNewFile();
			fos = new FileOutputStream(zipFile);
		}
		// 使用指定的 Manifest 创建新的 JarOutputStream。清单作为输出流的第一个条目被写入
		final JarOutputStream jos = new JarOutputStream(fos, new Manifest());
		jos.setLevel(checkZipLevel(level));
		final String[] fileNames = directory.list();
		if (fileNames != null) {
			for (int i = 0; i < fileNames.length; i++) {
				// 对一级目录下的所有文件或文件夹进行处理
				recurseFiles(jos, new File(directory, fileNames[i]), zipFolderName == null ? "" : zipFolderName);
			}
		}
		// 关闭 ZIP 输出流和正在过滤的流。
		jos.close();
	}

	/**
	 * 检查并设置有效的压缩级别,避免压缩级别设置错的异常
	 * 
	 * @param level - 压缩级别
	 * @return 有效的压缩级别或者默认压缩级别
	 */
	public static int checkZipLevel(final int level) {
		if (level < 0 || level > 9) {
			return 7;
		} else {
			return level;
		}
	}

	public static void main(final String args[]) throws FileNotFoundException, IOException {
		// makeDirectoryToZip();
		final String homeDir = System.getProperty("user.dir");
		final File zipFile = new File(homeDir, "download" + File.separatorChar + "test.zip");
		final File pagesDirectory = new File(homeDir, "src");
		System.out.println("Making zip file from folder /src to " + zipFile);
		ZipUtil.makeDirectoryToZip(pagesDirectory, zipFile, "", 9);
		System.out.println("Zip file " + zipFile + " has been made.");
	}
}