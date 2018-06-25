package com.exttool;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * 图片水印
 * 
 * @blog http://sjsky.iteye.com
 * @author Michael
 */
public class MarkTool {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String bigUrl = "D:\\test\\src";
		MarkConfig config = new MarkConfig();
		config.setAlpha(0.5f);
		config.setSrcImgType("1");// 1-本地 ，2 -网络
		config.setColor("#FF69B4");
		config.setMarkText("www.amylove.cn");
		// config.setFontSize(200);
		// config.setOutputImageDir("d:/test/output3");
		config.setRootPath(bigUrl);
		// // 给图片添加水印
		// MarkTool.markImageByIcon(httpiconPath, srcImgPath, targetDir1);
		// MarkTool.batchMarkImageByIcon(httpiconPath, srcImgDir, targetDir1);
		MarkTool.batchMarkImageByText(config);
		// // // 给图片添加水印,水印旋转-45
		// MarkTool.markImageByIcon(httpiconPath, srcImgPath, targetDir2, -45);
		// MarkTool.markImageByText("水印！水印！水印！水印！", httpSrcImgPath, targetDir1, null);
		// MarkTool.markImageByText("水印！水印！水印！水印！", srcImgPath, targetDir1, null);
		// MarkTool.markImageByText("水印！水印！水印！水印！", srcImgPath, targetDir2, 45);
	}

	/**
	 * 批量将某个目录下的所有图片都打上水印图标
	 * 
	 * @param iconPath
	 * @param srcImgPathDir
	 * @param targerPathDir
	 */
	private static String batchMarkImageByIcon(MarkConfig config) {
		if (config.getPreview() == 1) {// 预览模式，只处理一张样图
			String fname = getRandomPreviewPic();
			String f = config.getPreviewSrcDir() + "/" + fname;
			markImageByIcon(config, f);
			return "ok";
		}
		File f = null;
		List<String> httpSrcImgPath = config.getHttpSrcImgPath();
		if (!httpSrcImgPath.isEmpty()) { // 说明是来自网络的图片源
			for (String httpUrl : httpSrcImgPath) {
				// 循环处理每张图片
				markImageByIcon(config, httpUrl);
			}
		} else {
			f = new File(config.getRootPath());
			File[] listFiles = f.listFiles();
			for (File file : listFiles) {
				if (file.isFile()
						&& (file.getName().toLowerCase().endsWith(".jpg") || file.getName().toLowerCase().endsWith(".jpeg") || file.getName().toLowerCase().endsWith(".png")
								|| file.getName().toLowerCase().endsWith(".gif") || file.getName().toLowerCase().endsWith(".gif") || file.getName().toLowerCase().endsWith(".bmp"))) {
					// 循环处理每张图片
					markImageByIcon(config, file.getPath());
				}
			}
		}
		return "ok";
	}

	private static String getRandomPreviewPic() {
		int[] intarr = { 1, 2, 3, 4, 5, 6 };
		long randNum = Math.round(Math.random() * 6);
		int round = (int) (randNum > 5 ? 5 : randNum);
		System.out.println("##---" + round);
		String fname = "preview" + intarr[round] + ".png";
		return fname;
	}

	/**
	 * 批量将某个目录下的所有图片都打上水印文字
	 * 
	 * @param iconPath
	 * @param srcImgPathDir
	 * @param targerPathDir
	 * @throws Exception
	 */
	public static String batchMarkImageByText(MarkConfig config) throws Exception {
		File f = null;
		List<String> httpSrcImgPath = config.getHttpSrcImgPath();
		if (!httpSrcImgPath.isEmpty()) { // 说明是来自网络的图片源
		// for (String httpUrl : httpSrcImgPath) {
		// //循环处理每张图片
		// markImageByText(config, httpUrl);
		// }
		} else {
			f = new File(config.getRootPath());
			File[] listFiles = f.listFiles();
			for (File file : listFiles) {// 遍历目录
				if (file.isDirectory()) {
					File[] pics = file.listFiles();
					for (File picFile : pics) {// 依次处理图片
						if (picFile.isFile()
								&& (picFile.getName().toLowerCase().endsWith(".jpg") || picFile.getName().toLowerCase().endsWith(".jpeg") || picFile.getName().toLowerCase().endsWith(".png")
										|| picFile.getName().toLowerCase().endsWith(".gif") || picFile.getName().toLowerCase().endsWith(".gif") || picFile.getName().toLowerCase().endsWith(".bmp"))) {
							if(!picFile.getName().split("\\.")[0].endsWith("_ok")){
								// 循环处理每张图片
								markImageByText(config, picFile);
							}
						}
					}
				}
			}
		}
		return "ok";
	}

	//
	// /**
	// * 给图片添加水印
	// *
	// * @param iconPath 水印图片路径
	// * @param srcImgPath 源图片路径
	// * @param targerPath 目标图片路径
	// */
	// private static void markImageByIcon(String iconPath, String srcImgPath, String targerPath) {
	// markImageByIcon(iconPath, srcImgPath, targerPath, null);
	// }
	/**
	 * 给图片添加水印、可设置水印图片旋转角度
	 * 
	 * @param iconPath 水印图片路径
	 * @param srcImgPath 源图片路径
	 * @param targerDir 目标图片存放路径
	 * @param degree 水印图片旋转角度
	 */
	private static void markImageByIcon(MarkConfig config, String srcImgPath) {
		OutputStream os = null;
		try {
			File f = new File(config.getOutputImageDir());
			if (!f.exists()) {
				f.mkdirs();
			}
			// srcImgPath = "http://img04.taobaocdn.com/imgextra/i4/240544375/T2m5BBXAJXXXXXXXXX_!!240544375.jpg";
			// srcImgPath = "http://img04.taobaocdn.com/imgextra/i4/240544375/T2705wXcNbXXXXXXXX_!!240544375.jpg";
			Image srcImg = getImage(srcImgPath);
			BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
			// 得到画笔对象
			// Graphics g= buffImg.getGraphics();
			Graphics2D g = buffImg.createGraphics();
			// 设置对线段的锯齿状边缘处理
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
			// 水印图象的路径 水印一般为gif或者png的，这样可设置透明度
			ImageIcon imgIcon = getMarkIcon(config.getIconPath(), config.getIconWidth(), config.getIconHeight());
			// 得到Image对象。
			Image img = imgIcon.getImage();
			float alpha = config.getAlpha(); // 透明度
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
			// 表示水印图片的位置
			int txtX = 0;
			int txtY = 0;
			int iconWidth = img.getWidth(null);
			int iconHeight = img.getHeight(null);
			// 根据位置来设置水印起始坐标
			int location = config.getLocation();
			switch (location) {
			case 0:
				txtX = (buffImg.getWidth() - iconWidth) / 2;
				txtY = (buffImg.getHeight() - iconHeight) / 2;
				break;
			case 1:
				txtX = 0;
				txtY = 0;
				break;
			case 2:
				txtX = buffImg.getWidth() - iconWidth;
				txtY = 0;
				break;
			case 3:
				txtX = 0;
				txtY = buffImg.getHeight() - iconHeight;
				break;
			case 4:
				txtX = buffImg.getWidth() - iconWidth;
				txtY = buffImg.getHeight() - iconHeight;
				break;
			case 5: // 自定义坐标
				txtX = config.getX() > buffImg.getWidth() ? (buffImg.getWidth() / 2) : config.getX();
				txtY = config.getY() > buffImg.getWidth() ? (buffImg.getWidth() / 2) : config.getY();
				break;
			default:
				break;
			}
			// 设置水印旋转
			if (config.getDegree() > 0) {
				g.rotate(Math.toRadians(config.getDegree()), txtX, txtY);
			}
			g.drawImage(img, txtX, txtY, null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
			g.dispose();
			String outputPath = "";
			if (config.getPreview() == 1) {// 预览模式
				File reviewOuput = new File(config.getOutputImageDir() + "/preview/");
				if (!reviewOuput.exists()) {
					reviewOuput.mkdirs();
				}
				outputPath = config.getOutputImageDir() + "/preview/preview.png";
				os = new FileOutputStream(outputPath);
			} else {
				String rootPath = config.getRootPath();
				if (config.getSrcImgType().equals("3")) {
					File productPicPath = new File(rootPath + "/products/");
					if (!productPicPath.exists()) {
						productPicPath.mkdirs();
					}
					outputPath = rootPath + "/products/" + getShopImgName(config, srcImgPath);
					os = new FileOutputStream(outputPath);
				} else {
					os = new FileOutputStream(config.getOutputImageDir() + "/" + getMarkedImageName(srcImgPath));
				}
			}
			// 生成图片
			ImageIO.write(buffImg, "JPG", os);
			buffImg.flush();
			System.out.println("图片完成添加Icon印章。。。。。。");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != os)
					os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 给单张图片添加文字水印、可设置水印文字旋转角度
	 * 
	 * @param text 水印文字
	 * @param srcImgPath 源图片路径
	 * @param targerDir 目标图片路径
	 * @param degree 水印图片旋转角度
	 * @throws Exception
	 */
	private static void markImageByText(MarkConfig config, File bigImg) throws Exception {
		OutputStream os = null;
		try {
			// File f = new File(srcImgPath);
			// if(!f.exists()){
			// f.mkdirs();
			// }
			// srcImgPath = "http://img01.taobaocdn.com/imgextra/i1/240544375/T2eSxBXhXOXXXXXXXX-240544375.jpg";
			// srcImgPath =
			// "http://a.hiphotos.baidu.com/album/w%3D230/sign=21c49574c2cec3fd8b3ea076e689d4b6/faedab64034f78f06fe0f24b78310a55b2191c9a.jpg";
			Image srcImg = getImage(bigImg.getPath());
			if(srcImg == null){
				return;
			}
			BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
			// 得到画笔对象
			Graphics2D g = buffImg.createGraphics();
			// 设置对线段的锯齿状边缘处理
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
			int txtX = 0;
			int txtY = 0;
			// 根据位置来设置水印起始坐标
			int location = config.getLocation();
			// 文字的大概宽度 0.8是打折系数
			int fontWidth = (int) Math.round(config.getMarkText().length() * config.getFontSize() * 0.6);
			int fontHeight = (int) Math.round(config.getFontSize() * 0.6);
			switch (location) {
			case 0:
				txtX = (buffImg.getWidth() / 2 - fontWidth / 2) < 0 ? 0 : (buffImg.getWidth() / 2 - fontWidth / 2);
				txtY = buffImg.getHeight() / 2 - fontHeight / 2;
				break;
			case 1:
				txtX = 30;
				txtY = fontHeight;
				break;
			case 2:
				txtX = buffImg.getWidth() - fontWidth;
				txtY = fontHeight;
				break;
			case 3:
				txtX = 30;
				txtY = buffImg.getHeight() - fontHeight;
				break;
			case 4:
				txtX = buffImg.getWidth() - fontWidth;
				txtY = buffImg.getHeight() - fontHeight;
				break;
			case 5: // 自定义坐标
				txtX = config.getX() > buffImg.getWidth() ? (buffImg.getWidth() / 2) : config.getX();
				txtY = config.getY() > buffImg.getWidth() ? (buffImg.getWidth() / 2) : config.getY();
				break;
			default:
				break;
			}
			float alpha = config.getAlpha(); // 透明度
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
			// 表示水印图片的位置
			Font font = new Font("黑体", Font.PLAIN, config.getFontSize());
			g.setFont(font);
			g.setColor(Color.decode(config.getColor()));
			// 设置水印旋转
			if (config.getDegree() > 0) {
				g.rotate(Math.toRadians(config.getDegree()), txtX, txtY);
			}
			g.drawString(config.getMarkText(), txtX, txtY);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
			g.dispose();
			String outputPath = "";
			if (config.getPreview() == 1) {// 预览模式
				File reviewOuput = new File(config.getOutputImageDir() + "/preview/");
				if (!reviewOuput.exists()) {
					reviewOuput.mkdirs();
				}
				outputPath = config.getOutputImageDir() + "/preview/preview.png";
			} else {
				outputPath = bigImg.getParent() + "/" + getMarkedImageName(bigImg.getPath());
			}
			os = new FileOutputStream(outputPath);
			// 生成图片
			ImageIO.write(buffImg, "JPG", os);
			System.out.println("图片完成添加文字印章。。。。。。");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("");
		} finally {
			try {
				if (null != os)
					os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取水印图片资源，兼容http网络资源
	 * 
	 * @param iconImgPath
	 * @param height
	 * @param width
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ProtocolException
	 */
	private static ImageIcon getMarkIcon(String iconImgPath, int width, int height) throws MalformedURLException, IOException, ProtocolException {
		ImageIcon iconImg = null;
		if (iconImgPath.startsWith("http://")) {
			URL url = new URL(iconImgPath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置请求方式为"GET"
			conn.setRequestMethod("GET");
			// 超时响应时间为5秒
			conn.setConnectTimeout(5 * 1000);
			// 通过输入流获取图片数据
			InputStream inStream = conn.getInputStream();
			// 说明是网络资源
			iconImg = new ImageIcon(ImageIO.read(inStream));
		} else {// 本地资源
			iconImg = new ImageIcon(iconImgPath);
		}
		int newHeight = height;
		int newWidth = width;
		Image smallImage = iconImg.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_FAST);
		// 再由修改后的Image来生成合适的Icon
		ImageIcon smallIcon = new ImageIcon(smallImage);
		return smallIcon;
	}

	/**
	 * 获得已经处理好的店铺商品文件名 numiid_ok.jpg
	 * 
	 * @param config
	 * @param srcImgPath
	 * @return
	 */
	public static String getShopImgName(MarkConfig config, String srcImgPath) {
		Map<String, String> shopUrls = config.getShopUrls();
		Set<String> keySet = shopUrls.keySet();
		String id = "";
		for (String numiid : keySet) {
			if (shopUrls.get(numiid).equals(srcImgPath)) {
				id = numiid;
			}
		}
		String[] split = null;
		if (srcImgPath.startsWith("http://")) {
			split = srcImgPath.split("/");
		}
		return id + "." + split[split.length - 1].split("\\.")[1];
	}

	/**
	 * 获得已经处理好的文件名
	 * 
	 * @param srcImgPath
	 * @return
	 */
	public static String getMarkedImageName(String srcImgPath) {
		String[] split = null;
		if (srcImgPath.startsWith("http://")) {
			split = srcImgPath.split("/");
		} else {
			split = srcImgPath.split("\\" + File.separatorChar);
		}
		return split[split.length - 1].split("\\.")[0] + "_ok" + "." + split[split.length - 1].split("\\.")[1];
	}

	/**
	 * 获取图片资源，兼容http网络资源
	 * 
	 * @param srcImgPath
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ProtocolException
	 */
	private static Image getImage(String srcImgPath) throws MalformedURLException, IOException, ProtocolException {
		Image srcImg = null;
		if (srcImgPath.startsWith("http://")) {
			URL url = new URL(srcImgPath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置请求方式为"GET"
			conn.setRequestMethod("GET");
			// 超时响应时间为5秒
			conn.setConnectTimeout(5 * 1000);
			// 通过输入流获取图片数据
			InputStream inStream = conn.getInputStream();
			// 说明是网络资源
			srcImg = ImageIO.read(inStream);
		} else {// 本地资源
			System.out.println("srcImg==" + srcImgPath);
			srcImg = ImageIO.read(new File(srcImgPath));
		}
		return srcImg;
	}

	/**
	 * 批量处理图片
	 * 
	 * @param setupConfig
	 * @throws Exception
	 */
	public static String batchMarkImage(MarkConfig config) throws Exception {
		System.out.println("用户key=" + config.getUserkey());
		System.out.println("水印类型=" + config.getMarkType());
		System.out.println("用户根目录=" + config.getRootPath());
		System.out.println("用户输出目录=" + config.getOutputImageDir());
		// 清理输出目录
		cleanOutput(config.getOutputImageDir());
		if ("1".equals(config.getMarkType())) {
			return batchMarkImageByText(config);
		} else if ("2".equals(config.getMarkType())) {
			return batchMarkImageByIcon(config);
		} else {
			return null;
		}
	}

	private static void cleanOutput(String outputImageDir) {
		File f = new File(outputImageDir);
		if (f.exists()) {
			if (f.isDirectory()) {
				File[] listFiles = f.listFiles();
				for (File file : listFiles) {
					if (file.isFile()) {
						file.delete();
					}
				}
			}
		}
	}

	public static void reduceImage(MarkConfig config) {
		String picDir = config.getRootPath();
		File f = new File(config.getRootPath());
		File[] listFiles = f.listFiles();
		for (File file : listFiles) {// 遍历目录
			if (file.isDirectory()) {
				File[] pics = file.listFiles();
				for (File picFile : pics) {// 依次处理图片
					if (picFile.isFile()
							&& (picFile.getName().toLowerCase().endsWith(".jpg") || picFile.getName().toLowerCase().endsWith(".jpeg") || picFile.getName().toLowerCase().endsWith(".png")
									|| picFile.getName().toLowerCase().endsWith(".gif") || picFile.getName().toLowerCase().endsWith(".gif") || picFile.getName().toLowerCase().endsWith(".bmp"))) {
						if(picFile.getName().split("\\.")[0].endsWith("_ok")){
							continue;
						}
						// 循环处理每张图片
						ScaleImage sc = new ScaleImage();
						try {
							sc.saveImageAsJpg(picFile.getPath(), picFile.getParent() + "/thumb.jpg", 88, 100);
							System.out.println("缩略图生成完毕..");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
}