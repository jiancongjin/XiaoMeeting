package cn.li.util;

import cn.li.dto.ResponseData;

/**
 * Created by coquettish on 2018/5/7.
 */
public class ResponseDataUtil {
    public static synchronized ResponseData build(Integer status, String message, Object result){
        ResponseData responseData = new ResponseData();
        responseData.setState(status);
        responseData.setInfo(message);
        responseData.setObject(result);
        return responseData;
    }
}
