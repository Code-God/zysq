package util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class PictureCompress implements Runnable {
	String picturePath;
	
	public PictureCompress(String picturePath) {
		super();
		this.picturePath = picturePath;
	}

	/**
	 * 创建图片缩略图(等比缩放)
	 * 
	 * @param src
	 *            源图片文件完整路径
	 * @param dist
	 *            目标图片文件完整路径
	 * @param width
	 *            缩放的宽度
	 * @param height
	 *            缩放的高度
	 */
	public static void createThumbnail(String src, String dist, float width,
			float height) {
		try {
			File srcfile = new File(src);
			if (!srcfile.exists()) {
				System.out.println("文件不存在");
				return;
			}
			BufferedImage image = ImageIO.read(srcfile);

			// 获得缩放的比例
			double ratio = 1.0;
			// 判断如果高、宽都不大于设定值，则不处理
			if (image.getHeight() > height || image.getWidth() > width) {
				if (image.getHeight() > image.getWidth()) {
					ratio = height / image.getHeight();
				} else {
					ratio = width / image.getWidth();
				}
			}
			// 计算新的图面宽度和高度
			int newWidth = (int) (image.getWidth() * ratio);
			int newHeight = (int) (image.getHeight() * ratio);

			BufferedImage bfImage = new BufferedImage(newWidth, newHeight,
					BufferedImage.TYPE_INT_RGB);
			bfImage.getGraphics().drawImage(
					image.getScaledInstance(newWidth, newHeight,
							Image.SCALE_SMOOTH), 0, 0, null);

			FileOutputStream os = new FileOutputStream(dist);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
			encoder.encode(bfImage);
			os.close();
			//System.out.println("创建缩略图成功");
		} catch (Exception e) {
			System.out.println("创建缩略图发生异常" + e.getMessage());
		}
	}
	
    public static void createSmallPicture(String picturePath) {
    	String mPicturePath=picturePath.replaceAll(".png", "SMALL.png");
        createThumbnail(picturePath, mPicturePath, 120, 160);
    }

	public void run() {
		// TODO Auto-generated method stub
		createSmallPicture(picturePath);
		
	}
}
