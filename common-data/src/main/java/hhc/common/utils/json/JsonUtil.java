package hhc.common.utils.json;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.util.*;


/**
 * Created by liusenhua on 2017/7/13.
 */
public class JsonUtil {
    // each thread has its own ObjectMapper instance
    private static ThreadLocal<ObjectMapper> objMapperLocal = new ThreadLocal<ObjectMapper>() {
        @Override
        public ObjectMapper initialValue() {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            objectMapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

            /**
             * 序列换成json时,将所有的long变成string
             * 因为js中得数字类型不能包含所有的java long值
             */
            SimpleModule simpleModule = new SimpleModule();
            simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
            simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
            objectMapper.registerModule(simpleModule);

            return objectMapper;
        }
    };

    public static ObjectMapper getObjectMapper() {
        return objMapperLocal.get();
    }

    public static String toJSONString(Object value) {
        String result = null;
        try {
            result = getObjectMapper().writeValueAsString(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Fix null string
        if ("null".equals(result)) {
            result = null;
        }
        return result;
    }

    public static <T> T parseObject(String jsonString, Class<T> clazz) {
        try {
            return getObjectMapper().readValue(jsonString, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * List<Map<String,Integer>> list2=parseObject(json,new
     * TypeReference<List<Map<String,Integer>>>(){});
     *
     * @param jsonString
     * @param valueTypeRef
     * @return
     */
    public static <T> T parseObject(String jsonString, TypeReference<T> valueTypeRef) {
        try {
            return (T) getObjectMapper().readValue(jsonString, valueTypeRef);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> parseArray(String jsonString, Class<T> clazz) {
        try {
            ObjectMapper mapper = getObjectMapper();
            return (List<T>) mapper.readValue(jsonString, mapper.getTypeFactory()
                    .constructParametricType(List.class, clazz));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> parseMap(String jsonString) {
        return parseObject(jsonString, Map.class);
    }

    public static void main(String[] args) {
        Message msg1 = new Message();
        msg1.uid = "1";
        msg1.opr_time = new Date();
        msg1.content = null;

        Message msg2 = new Message();
        msg2.uid = "2";
        msg2.opr_time = new Date();
        msg2.content = "hello world---2";

        List<Message> list = new ArrayList<Message>();
        list.add(msg1);
        list.add(msg2);
        String json = toJSONString(list);

        List<Message> newMsg = JsonUtil.parseArray(json, Message.class);
        System.out.println(newMsg);
        System.out.println((newMsg.get(0).uid));

        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("key1", 1);
        map.put("key2", 2);
        json = toJSONString(map);
        System.out.println(json);
        map = parseObject(json, new TypeReference<Map<String, Integer>>() {
        });
        System.out.println(map);

        Map<String, Integer> map1 = new HashMap<String, Integer>();
        map1.put("key1", 1);
        map1.put("key2", 2);
        Map<String, Integer> map2 = new HashMap<String, Integer>();
        map2.put("key3", 3);
        map2.put("key4", 4);
        List<Map<String, Integer>> list1 = new ArrayList<Map<String, Integer>>();
        list1.add(map1);
        list1.add(map2);
        json = toJSONString(list1);
        System.out.println(json);
        List<Map<String, Integer>> list2 = parseObject(json,
                new TypeReference<List<Map<String, Integer>>>() {
                });
        System.out.println(list2);

    }

    static class Message {
        String uid;
        Date opr_time;
        // @JsonIgnore
        String content;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public Date getOpr_time() {
            return opr_time;
        }

        public void setOpr_time(Date opr_time) {
            this.opr_time = opr_time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
