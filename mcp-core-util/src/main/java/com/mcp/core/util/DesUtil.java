package com.mcp.core.util;

import java.security.Security;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/*字符串 DESede(3DES) 加密
 * ECB模式/使用PKCS7方式填充不足位,目前给的密钥是192位
 * 3DES（即Triple DES）是DES向AES过渡的加密算法（1999年，NIST将3-DES指定为过渡的
 * 加密标准），是DES的一个更安全的变形。它以DES为基本模块，通过组合分组方法设计出分组加
 * 密算法，其具体实现如下：设Ek()和Dk()代表DES算法的加密和解密过程，K代表DES算法使用的
 * 密钥，P代表明文，C代表密表，这样，
 * 3DES加密过程为：C=Ek3(Dk2(Ek1(P)))
 * 3DES解密过程为：P=Dk1((EK2(Dk3(C)))
 * */
public class DesUtil {

    /**
     * @param args在java中调用sun公司提供的3DES加密解密算法时，需要使
     * 用到$JAVA_HOME/jre/lib/目录下如下的4个jar包：
     *jce.jar
     *security/US_export_policy.jar
     *security/local_policy.jar
     *ext/sunjce_provider.jar
     */

    private static final String Algorithm = "DES"; //定义加密算法,可用 DES,DESede,Blowfish

    //keybyte为加密密钥，长度为24字节
    //src为被加密的数据缓冲区（源）
    public static byte[] encryptMode(byte[] keybyte, byte[] src) throws Exception {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
            //加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);//在单一方面的加密或解密
    }

    //keybyte为加密密钥，长度为24字节
    //src为被加密的数据缓冲区（源）
    public static byte[] encryptTrippleMode(byte[] keybyte, byte[] src) throws Exception {
        //生成密钥
        SecretKey deskey = new SecretKeySpec(keybyte, "DESede");
        IvParameterSpec iv = new IvParameterSpec(new byte[8]);
        //加密
        Cipher c1 = Cipher.getInstance("DESede/CFB/PKCS5Padding");
        c1.init(Cipher.ENCRYPT_MODE, deskey, iv);
        //c1.init(Cipher.ENCRYPT_MODE, deskey);
        return c1.doFinal(src);//在单一方面的加密或解密
    }

    //keybyte为加密密钥，长度为24字节
    //src为加密后的缓冲区
    public static byte[] decryptTrippleMode(byte[] keybyte, byte[] src) throws Exception {
        //生成密钥
        SecretKey deskey = new SecretKeySpec(keybyte, "DESede");
        IvParameterSpec iv = new IvParameterSpec(new byte[8]);
        //DESede/CBC/PKCS5Padding
        //解密
        Cipher c1 = Cipher.getInstance("DESede/CFB/PKCS5Padding");
        c1.init(Cipher.DECRYPT_MODE, deskey, iv);
        //c1.init(Cipher.DECRYPT_MODE, deskey);
        return c1.doFinal(src);
    }

    //keybyte为加密密钥，长度为24字节
    //src为加密后的缓冲区
    public static byte[] decryptMode(byte[] keybyte, byte[] src) throws Exception {
        //生成密钥
        SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
        //解密
        Cipher c1 = Cipher.getInstance(Algorithm);
        c1.init(Cipher.DECRYPT_MODE, deskey);
        return c1.doFinal(src);
    }

    /**
     * 获得随机长度的key
     * @param length
     * @return
     * @throws Exception
     */
    public static String getKey(int length) throws Exception
    {
        Random random = new Random();
        byte[] byteArray = new byte[length];
        random.nextBytes(byteArray);
        return Base64.encode(byteArray);
    }

    public static void main(String[] args) throws Exception {
        /*String md5 = MD5Util.MD5("{}2014-07-02T11:30:36.702+0800LTPOuque8OU=");
        System.out.println(md5);
        long cur = System.currentTimeMillis();
        String key = getKey(8);
        System.out.println(key);
        // TODO Auto-generated method stub
        //添加新安全算法,如果用JCE就要把它添加进去
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        final byte[] keyBytes = Base64.decode(key);
        String szSrc = "This is a DES test. 测试";
        System.out.println("加密前的字符串:" + szSrc);
        String secretBaseContent = Base64.encode(encryptMode(keyBytes, szSrc.getBytes("utf-8")));
        System.out.println("加密后的字符串:" + secretBaseContent);
        byte[] srcBytes = decryptMode(keyBytes, Base64.decode(secretBaseContent));
        System.out.println("解密后的字符串:" + (new String(srcBytes, "utf-8")));
        System.out.println("gap:" + (System.currentTimeMillis() - cur));*/

        String key = getKey(8);
        System.out.println(key);
        /*String encode = "utf-8";
        String content = "{\"schemeId\":\"000000000000000150\",\"coupon_pay\":\"0\",\"integral_pay\":\"0\",\"sign_pay\":\"1\",\"totalAmt\":\"200\",\"b2c_pay\":\"0\",\"url\":\"http://icbc.mobilelottery.cn:8080/B2C/gotoPay2\"}";
        String key = "335bdfd179d94657abde3752d7a0bb6c";
        byte[] keyByte = Base64.decode(key);
        //byte[] keyByte = key.getBytes(encode);
        System.out.println(keyByte.length);
        System.out.println(content);
        String secretBaseContent = Base64.encode(DesUtil.encryptMode(keyByte, content.getBytes(encode), "DESede/ECB/PKCS5Padding"));
        System.out.println(secretBaseContent);

        String decodedStr = new String(DesUtil.decryptMode(keyByte, Base64.decode(secretBaseContent), ""), encode);
        System.out.println(decodedStr);*/

    }

}
