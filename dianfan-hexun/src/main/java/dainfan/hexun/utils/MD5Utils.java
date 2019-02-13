package dainfan.hexun.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * @ClassName MD5Utils
 * @Description MD5加密算法
 * @author cjy
 * @date 2017年12月29日 上午9:48:47
 */
public class MD5Utils {
	    private MD5Utils() {}
	    // 全局数组
	    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
	            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	    // 返回形式为数字跟字符串
	    private static String byteToArrayString(byte bByte) {
	        int iRet = bByte;
	        // System.out.println("iRet="+iRet);
	        if (iRet < 0) {
	            iRet += 256;
	        }
	        int iD1 = iRet / 16;
	        int iD2 = iRet % 16;
	        return strDigits[iD1] + strDigits[iD2];
	    }

	    // 转换字节数组为16进制字串
	    private static String byteToString(byte[] bByte) {
	        StringBuffer sBuffer = new StringBuffer();
	        for (int i = 0; i < bByte.length; i++) {
	            sBuffer.append(byteToArrayString(bByte[i]));
	        }
	        return sBuffer.toString();
	    }

	    /**
	     * @Title: getMD5
	     * @Description: 获取MD5值（小写）
	     * @param str 需要生成MD5的字符串
	     * @return
	     * @throws:
	     * @time: 2017年12月29日 上午9:47:16
	     */
	    public static String getMD5(String str) {
	        String resultString = null;
	        try {
	            resultString = new String(str);
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            // md.digest() 该函数返回值为存放哈希值结果的byte数组
	            resultString = byteToString(md.digest(str.getBytes()));
	        } catch (NoSuchAlgorithmException ex) {
	            ex.printStackTrace();
	        }
	        return resultString;
	    }
	    /**
	     * @Title: getUpperMD5
	     * @Description: 获取MD5值（大写）
	     * @param str 需要生成MD5的字符串
	     * @return
	     * @throws:
	     * @time: 2017年12月29日 上午9:47:36
	     */
	    public static String getUpperMD5(String str) {
	    	return getMD5(str).toUpperCase();
	    }
	    
	}