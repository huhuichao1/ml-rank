package hhc.common.utils.stringUtils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by chengyang on 15/12/2
 */
public abstract class MD5Helper {
    /**
     * 按序拼接若干字符串, 生成对应的MD5
     */
    public static String md5(String... arg) {
        StringBuilder sb = new StringBuilder();
        for (String s : arg) {
            sb.append(s);
        }
        return DigestUtils.md5Hex(sb.toString());
    }
    
    public static void main(String[] args) {
//        System.out.println(MD5Helper.md5("laiwan@2017!"));
        System.out.println(MD5Helper.md5("888888"));
    }
}
