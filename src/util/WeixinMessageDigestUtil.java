package util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
 * SHA1 水印算法工具类
 * AJ 2013-04-12
 */
public final class WeixinMessageDigestUtil {

	private static WeixinMessageDigestUtil _instance = null;

	private MessageDigest alga;

	private WeixinMessageDigestUtil() {
		try {
			alga = MessageDigest.getInstance("SHA-1");
		} catch (Exception e) {
			throw new InternalError("init MessageDigest error:" + e.getMessage());
		}
	}

	public static WeixinMessageDigestUtil getInstance() {
		if (_instance == null)
			_instance = new WeixinMessageDigestUtil();
		return _instance;
	}

	public static String byte2hex(byte[] b) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < b.length; i++) {
			tmp = (Integer.toHexString(b[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}

	public String encipher(String strSrc) {
		String strDes = null;
		byte[] bt = strSrc.getBytes();
		alga.update(bt);
		strDes = byte2hex(alga.digest()); // to HexString
		return strDes;
	}

	public static String stringMD5(String input) {
		try {
			// 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			// 输入的字符串转换成字节数组
			byte[] inputByteArray = input.getBytes("UTF-8");
			// inputByteArray是输入字符串转换得到的字节数组
			messageDigest.update(inputByteArray);
			// 转换并返回结果，也是字节数组，包含16个元素
			byte[] resultByteArray = messageDigest.digest();
			// 字符数组转换成字符串返回
			return byte2hex(resultByteArray).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
//		String signature = "b7982f21e7f18f640149be5784df8d377877ebf9";
//		String timestamp = "1365760417";
//		String nonce = "1365691777";
//		String[] ArrTmp = { "token", timestamp, nonce };
//		Arrays.sort(ArrTmp);
//		StringBuffer sb = new StringBuffer();
//		for (int i = 0; i < ArrTmp.length; i++) {
//			sb.append(ArrTmp[i]);
//		}
//		String pwd = WeixinMessageDigestUtil.getInstance().encipher(sb.toString());
//		if (signature.equals(pwd)) {
//			System.out.println("token 验证成功~!");
//		} else {
//			System.out.println("token 验证失败~!");
//		}
		
		String s = "appid=wxd930ea5d5a258f4f&auth_code=123456&body=test&device_info=123&mch_id=1900000109&nonce_str=960f228109051b9969f76c82bde183ac&out_trade_no=1400755861&spbill_create_ip=127.0.0.1&total_fee=1&key=8934e7d15453e97507ef794cf7b0519d";
		System.out.println(stringMD5(s));
		
	}
}