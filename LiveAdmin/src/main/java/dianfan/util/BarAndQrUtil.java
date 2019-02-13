package dianfan.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import common.propertymanager.PropertyUtil;

/**
 * 条形码和二维码编码解码
 */
public class BarAndQrUtil {
	// 二维码保存路径设置
	private final static String QRCONFIGPATH = PropertyUtil.getProperty("qrimgpath", "upload/qr");
	private final static String QRSAVEPATH = PropertyUtil.getProperty("qrimgroot", "C:/") + QRCONFIGPATH;
	// 条形码保存路径设置
	private final static String BARCONFIGPATH = PropertyUtil.getProperty("barimgpath", "upload/bar");
	private final static String BARSAVEPATH = PropertyUtil.getProperty("barimgroot", "C:/") + BARCONFIGPATH;

	/**
	 * 条形码编码
	 * 
	 * @param contents
	 * @param width
	 * @param height
	 */
	public static void encode(String contents, int width, int height, String filename) {
		int codeWidth = 3 + // start guard
				(7 * 6) + // left bars
				5 + // middle guard
				(7 * 6) + // right bars
				3; // end guard
		codeWidth = Math.max(codeWidth, width);
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.EAN_13, codeWidth, height,
					null);

			if (!new File(BARSAVEPATH + filename).exists()) {
				MatrixToImageWriter.writeToFile(bitMatrix, "png",
						new File(BARSAVEPATH + "/" + filename));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 条形码解码
	 * 
	 * @param qrsavePath
	 * @return String
	 */
	public static String decode(String filename) {
		BufferedImage image = null;
		Result result = null;
		try {
			image = ImageIO.read(new File(BARSAVEPATH + "/" + filename));
			if (image == null) {
				return null;
			}
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

			result = new MultiFormatReader().decode(bitmap, null);
			return result.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 二维码编码
	 * 
	 * @param contents
	 * @param width
	 * @param height
	 * @param filename
	 * @throws WriterException 
	 * @throws IOException 
	 */
	public static void encode2(String contents, int width, int height, String filepath) throws WriterException, IOException {
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		// 指定纠错等级
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		// 指定编码格式
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height, hints);
		MatrixToImageWriter.writeToFile(bitMatrix, PropertyUtil.getProperty("qr_ext"), new File(filepath));
	}

	/**
	 * 二维码解码
	 * 
	 * @param filename
	 * @return String
	 */
	public static String decode2(String filename) {
		BufferedImage image = null;
		Result result = null;
		try {
			image = ImageIO.read(new File(QRSAVEPATH + filename));
			if (image == null) {
				return null;
			}
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

			Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
			hints.put(DecodeHintType.CHARACTER_SET, PropertyUtil.getProperty("encoding"));

			result = new MultiFormatReader().decode(bitmap, hints);
			return result.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// /**
	// * @param args
	// */
	// public static void main(String[] args) {
	//
	// // 条形码
	// String imgPath = "target\\zxing_EAN13.png";
	// String contents = "6923450657713";
	// int width = 105, height = 50;
	//
	// BarAndQrUtil.encode(contents, width, height, imgPath);
	// System.out.println("finished zxing EAN-13 encode.");
	//
	// String decodeContent = BarAndQrUtil.decode(imgPath);
	// System.out.println("解码内容如下：" + decodeContent);
	// System.out.println("finished zxing EAN-13 decode.");
	//
	// // 二维码
	// String imgPath2 = "target\\zxing.png";
	// String contents2 = "Hello Gem, welcome to Zxing!" + "\nBlog [
	// http://jeeplus.iteye.com ]"
	// + "\nEMail [ jeeplus@163.com ]";
	// int width2 = 300, height2 = 300;
	//
	// BarAndQrUtil.encode2(contents2, width2, height2, imgPath2);
	// System.out.println("finished zxing encode.");
	//
	// String decodeContent2 = BarAndQrUtil.decode2(imgPath2);
	// System.out.println("解码内容如下：" + decodeContent2);
	// System.out.println("finished zxing decode.");
	//
	// }

}