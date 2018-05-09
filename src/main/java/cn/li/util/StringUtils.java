package cn.li.util;

import java.util.UUID;

/**
 * Created by coquettish on 2018/5/7.
 */
public class StringUtils {
    //全部不为空返回true
    public static boolean isEmpty(Object... args){
        boolean flag = true;
        for(Object arg:args){
            if(arg != null){
                if(arg instanceof String){
                    if(arg.toString().trim().length() <=0){
                        flag = false;
                        break;
                    }
                }
            }else {
                flag = false;
                break;
            }
        }
        return flag;
    }
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid.toUpperCase();
    }

    public static void main(String[] args) {
        System.out.println(StringUtils.isEmpty());
    }
}
