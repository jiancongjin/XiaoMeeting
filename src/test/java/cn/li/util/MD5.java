package cn.li.util;

import cn.li.conf.XiaoMeetingConfig;
import org.junit.Test;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by coquettish on 2018/5/7.
 */
public class MD5 {

    public static String md5Password(String password) {

        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }

            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

    }

    public static String getResult(String inputStr) {
        System.out.println("=======加密前的数据:" + inputStr);
        BigInteger bigInteger = null;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] inputData = inputStr.getBytes();
            md.update(inputData);
            bigInteger = new BigInteger(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("MD5加密后:" + bigInteger.toString(16));
        return bigInteger.toString(16);
    }

    @Test
    public void test() {
        String str = "2016211892" + null + XiaoMeetingConfig.Key;
        System.out.println("=======加密前的数据:" + str);

        System.out.println("MD5加密后:" + MD5.md5Password(str));
    }

}
