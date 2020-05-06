package cn.kafka.common.utils;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;

public abstract class JSONUtils {

    public static <T> T convert(Object data, Class<T> clazz) {
        if (data == null) {
            return null;
        }
        return JSON.parseObject(JSON.toJSONString(data), clazz);
    }

    public static String convertString(Object data) {
        if (data == null) {
            return null;
        }
        return JSON.toJSONString(data);
    }

    public static Object parseObject(String data) {
        if (StringUtils.isBlank(data)) {
            return null;
        }
        return JSON.parseObject(data);
    }
    
    public static void main(String[] args) {
        
        System.out.println(JSON.parseObject("{\"businessOrderNo\":\"20160830104845074542\",\"cashierOrderNo\":\"20160830104852707965\",\"tradeStatus\":\"SUCCESS\",\"tradeTime\":1472525332487}"));
        System.out.println(JSON.parseObject("{\"name\":\"zhangsan\"}"));
    }
    
}
