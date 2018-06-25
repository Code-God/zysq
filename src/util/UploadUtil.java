package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;

public class UploadUtil {
	
	// 存储图片的线程。
		public static ExecutorService pool = Executors.newFixedThreadPool(10);

		public static String UPLOADSIMAGES = "uploadsImages";
		private static final ResourceBundle RESOURCE_BUNDLE_URL = ResourceBundle
		.getBundle("cfg");
		/**
		 * 服务器空间地址
		 * 
		 * @param savePath
		 * @return
		 */
		public static String getSavePath(String savePath) {
			return ServletActionContext.getServletContext().getRealPath(savePath);
		}

		public static String getImgUrl() {
			String uri=RESOURCE_BUNDLE_URL.getString("readimgurl");
			return uri;
		}
		
		public static String getSaveUrl(){
			String uri=RESOURCE_BUNDLE_URL.getString("saveimgurl");
			return uri;
		}
		/**
		 * 获取服务器存储图片的地址。
		 * 
		 * @return
		 */
		public static String getSavePicPath() {
			String savePath = File.separator + UPLOADSIMAGES + File.separator;
			// 获取服务器地址
			savePath = UploadUtil.getSavePath(savePath);
			return savePath;
		}

		/**
		 * 图片的绝对路径
		 * 
		 * @param realPicPath
		 * @return
		 */
		public static String getAbsolutePicPath(String realPicPath) {
			String absolutePicPath = UploadUtil.getSavePath(realPicPath);
			return absolutePicPath;
		}

		
		public static String upLoadImage(File pic,String pictype) {
			// 获取储存的目录，直接获取系统存储路径，可修改
			String saveDir = getSaveUrl()+UPLOADSIMAGES+"\\"+pictype;
			String picName = UUID.randomUUID().toString() + ".png";
			String picSavePath = saveDir + File.separator + picName; // 绝对路径
			String realSavePath = UPLOADSIMAGES +"\\"+pictype+ File.separator + picName; // 相对路径
			realSavePath = realSavePath.replaceAll("\\\\","/");
			// 文件不存在，返回空
			if (pic == null)
				return null;
			File fileDirect = new File(saveDir);
			if (!fileDirect.exists()) {
				// 该目录不存在，则创建目录
				fileDirect.mkdirs();
			}
			try {
				FileOutputStream fos = new FileOutputStream(picSavePath);
				FileInputStream fis = new FileInputStream(pic);
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fis.close();
				fos.close();
				// 制成压缩图片
				pool.submit(new PictureCompress(picSavePath));
				return realSavePath;
			} catch (Exception e) {
				// TODO: handle exception
				return null;
			}
		}

		/**
		 * 判断图片是否存在，不存在就删除数据库的图片路径
		 * 
		 * @param path
		 *            相对路径
		 * @return
		 */
		public static String isPictureExist(String realPath) {
			String absolutePath;
			if (StringUtils.isEmpty(realPath))
				return null;
			absolutePath = getSaveUrl()+realPath;
			try {
				File file = new File(absolutePath);
				if (!file.exists()) {
					return null;
				}
				return realPath;
			} catch (Exception e) {
				return null;
			}
		}

		/**
		 * 删除图片
		 * @param picPath
		 * @return
		 */
		public static String deletePic(String picPath) {
			if(StringUtils.isEmpty(picPath))
				return "0";
//			String delPath = PictureOperate.getAbsolutePicPath(picPath);
			String delPath = getSaveUrl()+picPath;
			try {
				File file = new File(delPath);
				File smallFile=new File(delPath.replaceAll(".png", "SMALL.png"));
				if (file.exists()) {
					file.delete();
					if(smallFile.exists())
						smallFile.delete();
					return "1";
				}
				return "0";
			} catch (Exception e) {
				return "0";
			}
		}
	
}
