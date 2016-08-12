package com.shuyun.sbd.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Component: JSON 工具类
 * Description:
 * Date: 15/11/25
 *
 * @author yue.zhang
 */
public class JsonUtil {

    public static ObjectMapper mapper;

    public static void init() {
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
    }

    /**
     * 根据json格式转换对象
     * @param content
     * @param valueType
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T readValue(String content , Class<T> valueType) throws IOException {
        if(mapper == null){
            init();
        }
        return mapper.readValue(content,valueType);
    }

    /**
     * 根据json格式转换对象列表
     * 例子：JsonUtil.readCollectionValue(listJson, new TypeReference<List<XXXX>>() {});
     * @param content
     * @param collectionType
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T readCollectionValue(String content , TypeReference<T> collectionType) throws IOException {
        if(mapper == null){
            init();
        }
        return mapper.readValue(content,collectionType);
    }

    /**
     * 将对象转换成json格式
     * @param value
     * @return
     * @throws JsonProcessingException
     */
    public static String writeValueAsString(Object value) throws JsonProcessingException {
        if(mapper == null){
            init();
        }
        return mapper.writeValueAsString(value);
    }

}
