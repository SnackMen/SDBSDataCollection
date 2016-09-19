package com.ws.util;


import org.codehaus.jackson.map.ObjectMapper;

/**
 * Created by laowang on 16-9-2.
 */
public class JsonUtils {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object object){
        String s = "";
        try{
            s=objectMapper.writeValueAsString(object);
        }catch (Exception e){
            e.printStackTrace();
        }
        return s;
    }
}