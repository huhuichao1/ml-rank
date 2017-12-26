package hhc.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class JSONUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JSONUtil.class);

    /**
     * bean->JSONObject
     * 
     * @param bean
     * @return
     */
    public static JSONObject bean2JSONObject(Object bean) {
        if (bean == null) {
            return null;
        }
        try {
            return (JSONObject) JSONObject.toJSON(bean);
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return null;
    }

    /**
     * string->JSONObject
     * 
     * @param json
     * @return
     */
    public static JSONObject string2JSONObject(String json) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return JSONObject.parseObject(json);
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return null;
    }

    /**
     * bean->string JSONObject->string
     * 
     * @param bean
     * @return
     */
    public static String bean2String(Object bean) {
        if (bean == null) {
            return null;
        }
        try {
            return JSONObject.toJSONString(bean);
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return null;
    }

    /**
     * JSONObject->bean
     * 
     * @param jSONObject
     * @param clazz
     * @return
     */
    public static <T> T jSONObject2Bean(JSONObject jSONObject, Class<T> clazz) {
        if (jSONObject == null || clazz == null) {
            return null;
        }
        try {
            return JSONObject.toJavaObject(jSONObject, clazz);
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return null;
    }

    /**
     * string->bean
     * 
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T string2Bean(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json) || clazz == null) {
            return null;
        }
        try {
            return JSONObject.parseObject(json, clazz);
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return null;
    }

    /**
     * string->list
     * 
     * @param json
     * @param clazz
     * @return
     */
    public static <T> List<T> string2List(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json) || clazz == null) {
            return null;
        }
        try {
            return JSONObject.parseArray(json, clazz);
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return null;
    }
}
