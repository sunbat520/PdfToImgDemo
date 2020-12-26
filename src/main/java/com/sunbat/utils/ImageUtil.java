package com.sunbat.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.logging.Logger;

public class ImageUtil {


	public static final float PRE_DEFUALT = 0.85f;

	/**
	 * 生成缩略图
	 * 
	 * @param sourceFilePath
	 *            源文件路径
	 * @param targetFilePath
	 *            目标文件路径
	 * @param width
	 *            目标文件宽
	 * @param hight
	 *            目标文件高
	 * @throws Exception
	 */
	public static void generateThumbnails(String sourceFilePath,
			String targetFilePath, int width, int hight) throws Exception {

		thumb(sourceFilePath, targetFilePath, width, hight, PRE_DEFUALT);
	}

	/**
	 * @param sourceFilePath
	 * @param targetFilePath
	 * @param width
	 *            目标宽
	 * @param height
	 *            目标高
	 * @param per
	 *            百分比
	 */
	private static void thumb(String sourceFilePath, String targetFilePath,
			int width, int height, float per) throws Exception {
		Image src;
		try {
			src = ImageIO.read(new File(sourceFilePath)); // 构造Image对象

			// 得到源图宽,高
			int oldWidth = src.getWidth(null);
			int oldHeight = src.getHeight(null);
			int newWidth = 0;
			int newHeight = 0;

			double tmpWidth = (oldWidth * 1.00) / (width * 1.00);
			double tmpHeight = (oldHeight * 1.00) / (height * 1.00);

			// 如果图片目标尺寸与原图相同或者目标尺寸不是正方形则不留白处理
			if (oldWidth != width && oldHeight != height) {
				// 图片跟据长宽留白，成一个正方形图。
				BufferedImage oldpic;
				if (oldWidth > oldHeight) {
					oldpic = new BufferedImage(oldWidth, oldWidth,
							BufferedImage.TYPE_INT_RGB);
				} else if (oldWidth < oldHeight) {
					oldpic = new BufferedImage(oldHeight, oldHeight,
							BufferedImage.TYPE_INT_RGB);
				} else {
					oldpic = new BufferedImage(oldWidth, oldHeight,
							BufferedImage.TYPE_INT_RGB);
				}
				Graphics2D g = oldpic.createGraphics();
				g.setColor(Color.white);
				if (oldWidth > oldHeight) {
					g.fillRect(0, 0, oldWidth, oldWidth);
					g.drawImage(src, 0, (oldWidth - oldHeight) / 2, oldWidth,
							oldHeight, Color.white, null);
				} else if (oldWidth < oldHeight) {
					g.fillRect(0, 0, oldHeight, oldHeight);
					g.drawImage(src, (oldHeight - oldWidth) / 2, 0, oldWidth,
							oldHeight, Color.white, null);
				} else {
					g.drawImage(src.getScaledInstance(oldWidth, oldHeight,
							Image.SCALE_SMOOTH), 0, 0, null);
				}
				g.dispose();
				src = oldpic;
			}
			// 图片调整为方形结束
			// 计算新图宽,高
			if (oldWidth > width) {
				newWidth = (int) Math.round(oldWidth / tmpWidth);
			} else if (oldWidth < width) {
				newWidth = (int) Math.round(oldWidth / tmpWidth);
			} else {
				newWidth = oldWidth;
			}
			if (oldHeight > height) {
				newHeight = (int) Math.round(oldHeight / tmpHeight);
			} else if (oldHeight < height) {
				newHeight = (int) Math.round(oldHeight / tmpHeight);
			} else {
				newHeight = oldHeight;
			}
			BufferedImage tag = new BufferedImage(newWidth, newHeight,
					BufferedImage.TYPE_INT_RGB);
			// 绘制缩小后的图
			// tag.getGraphics().drawImage(src,0,0,new_w,new_h,null);
			tag.getGraphics().drawImage(
					src.getScaledInstance(newWidth, newHeight,
							Image.SCALE_SMOOTH), 0, 0, null);
			
			String[] suffixs = targetFilePath.split("/");
			String dirPath = "";
			
			for(int i = 0;i<suffixs.length-1;i++){
				dirPath=dirPath + suffixs[i]+"/";
			}
			
			
			File dirPathFile = new File(dirPath);

			if (!dirPathFile.exists()) {
				dirPathFile.mkdirs();
			}
			
			// 输出文件流
			FileOutputStream outPutStream = new FileOutputStream(targetFilePath);
			String flag=  sourceFilePath.substring(sourceFilePath.lastIndexOf(".")+1, sourceFilePath.length());//后缀名
			ImageIO.write(tag,flag,outPutStream);
			tag.flush();
//			JPEGImageEncoder encoder = JPEGCodec
//					.createJPEGEncoder(outPutStream);
//			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
//			/* 压缩质量 */
//			jep.setQuality(per, true);
//			encoder.encode(tag, jep);
			// 近JPEG编码
			// encoder.encode(tag);
			outPutStream.flush();
			outPutStream.close();
		} catch (IOException e) {
			System.err.print("生成缩略图异常 [sourceFilePath = " + sourceFilePath
					+ ", targetFilePath = " + targetFilePath + ", width = "
					+ width + ", height = " + height + ", per = " + per + "]"+
					e);
			throw e;
		}
	}

	/**
	 * 生成缩略图文件名
	 */
	public static String madeThumbFileName(String originalFilename, int width,
			int height) {

		return "t_" + width + "_" + height + "_" + originalFilename;
	}

	/**
	 * 生成文件名
	 */
	public static String madeFileName(String originalFilename) {

		return new Long(System.currentTimeMillis()).toString()
				+ originalFilename.substring(originalFilename.lastIndexOf("."),
						originalFilename.length());
	}

	/**
	 * 获取图片域名
	 */
	public static String getCustomerImgDomain(String domain, String folder,
			String customerId) {

		return domain + folder + "/" + customerId + "/";
	}

	//base64字符串转化成图片
	public static boolean genImageFromBase64(String imgStr, String destPath) {
		//对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) //图像数据为空
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			//Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {//调整异常数据
					b[i] += 256;
				}
			}
			//生成jpeg图片
			OutputStream out = new FileOutputStream(destPath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static String getBase64FromImg(String destPath){
		InputStream is = null;
		byte[] data = null;
		BASE64Encoder encoder = new BASE64Encoder();
		try{
			is = new FileInputStream(destPath);
			data = new byte[is.available()];
			is.read(data);
			is.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		return encoder.encode(data);
	}

	public static String getBase64FromImgUnderSize(String destPath){
		InputStream is = null;
		byte[] data = null;
		BASE64Encoder encoder = new BASE64Encoder();
		try{
			is = new FileInputStream(destPath);
			data = new byte[is.available()];
			byte[] underSizeData = compressUnderSize(data, 4*1024*1024);
			is.read(underSizeData);
			is.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		return encoder.encode(data);
	}


	public static void main(String[] args) {
        String base64FromNetImg = getBase64FromNetImg("https://file.pacss.hxqcgf.com/fs109/doContract/HD420010/38224f3a-3e4b-449f-aa64-56aebbc54a13.png");
        System.out.println(base64FromNetImg);
        genImageFromBase64(base64FromNetImg,"D:\\temp\\1.png");
	}

	/**
	 * 本地文件（图片、excel等）转换成Base64字符串
	 *
	 * @param imgPath
	 */
	public static String convertFileToBase64(String imgPath) {
		byte[] data = null;
		// 读取图片字节数组
		try {
			InputStream in = new FileInputStream(imgPath);
			System.out.println("文件大小（字节）="+in.available());
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组进行Base64编码，得到Base64编码的字符串
		BASE64Encoder encoder = new BASE64Encoder();
		String base64Str = encoder.encode(data);
		return base64Str;
	}

	/**
	 * 将base64字符串，生成文件
	 */
	public static File convertBase64ToFile(String fileBase64String, String filePath, String fileName) {

		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(filePath);
			if (!dir.exists()) {//判断文件目录是否存在
				dir.mkdirs();
			}

			BASE64Decoder decoder = new BASE64Decoder();
			byte[] bfile = decoder.decodeBuffer(fileBase64String);

			file = new File(filePath + File.separator + fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(bfile);
			return file;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * 功能描述:
	 *  先从本地读取绝对路径文件图片，读取不到就从文件服务器上读取
	 * @param rootPath : 文件上传本地根目录
	 * @param bowseUrl : 文件服务器地址
	 * @param relativePathImg : 文件在服务器相对路径
	 * @Return: java.lang.String
	 * @Date: 2020-12-17 上午 10:33
	 * @throws
	 */
	public static String getBase64ImgFromLocalOrNet(String rootPath,String bowseUrl, String relativePathImg){
		String fullDirPath = rootPath+relativePathImg;
		File dirPathFile = new File(fullDirPath);
		if (dirPathFile.exists()) {
			return getBase64FromImg(fullDirPath);
		}else{
			fullDirPath = bowseUrl+relativePathImg;
			return getBase64FromNetImg(fullDirPath);
		}
	}


	public static String getBase64FromNetImg(String netImagePath){
		InputStream in = null;
		byte[] data = null;
		BASE64Encoder encoder = new BASE64Encoder();
		try{
			// 创建URL
			URL url = new URL(netImagePath);
			// 创建链接
			final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setConnectTimeout(25000);
			conn.setReadTimeout(25000);
			conn.setRequestMethod("GET");
			conn.connect();
			in = conn.getInputStream();
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		return encoder.encode(data);
	}


	/**
	 * 将图片压缩到指定大小以内
	 *
	 * @param srcImgData 源图片数据
	 * @param maxSize 目的图片大小
	 * @return 压缩后的图片数据
	 */
	public static byte[] compressUnderSize(byte[] srcImgData, long maxSize) {
		double scale = 0.9;
		byte[] imgData = Arrays.copyOf(srcImgData, srcImgData.length);

		if (imgData.length > maxSize) {
			do {
				try {
					imgData = compress(imgData, scale);

				} catch (IOException e) {
					throw new IllegalStateException("压缩图片过程中出错，请及时联系管理员！", e);
				}

			} while (imgData.length > maxSize);
		}

		return imgData;
	}

	/**
	 * 按照 宽高 比例压缩
	 *
	 * @param srcImgData 待压缩图片输入流
	 * @param scale 压缩刻度
	 * @return 压缩后图片数据
	 * @throws IOException 压缩图片过程中出错
	 */
	public static byte[] compress(byte[] srcImgData, double scale) throws IOException {
		BufferedImage bi = ImageIO.read(new ByteArrayInputStream(srcImgData));
		int width = (int) (bi.getWidth() * scale); // 源图宽度
		int height = (int) (bi.getHeight() * scale); // 源图高度

		Image image = bi.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics g = tag.getGraphics();
		g.setColor(Color.RED);
		g.drawImage(image, 0, 0, null); // 绘制处理后的图
		g.dispose();

		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		ImageIO.write(tag, "JPEG", bOut);

		return bOut.toByteArray();
	}

}
