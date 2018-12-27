package com.sankuai.mall.iot.chuanyunapi.util;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Created by xukai
 */
public abstract class JsonHelper {
    private static final Logger log = LoggerFactory.getLogger(JsonHelper.class);

    public static JsonNode toTree(String json) throws IOException {
        return MAPPER.readTree(json);
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(String json) throws IOException {
        if (StringUtils.isEmpty(json)) {
            return new HashMap<String, Object>(0);
        }
        return MAPPER.readValue(json, Map.class);
    }

    public static <T> T toBean(String json, Class<T> clazz) throws IOException {
        if (json == null || "".equals(json)) return null;
        JsonParser parser = JSON_FACTORY.createParser(json);
        parser.setCodec(MAPPER);
        T t = parser.readValueAs(clazz);
        parser.close();
        return t;
    }

    public static String toJson(Object object) throws IOException {
        return useMapper(object, MAPPER);
    }

    public static String toJsonWithoutNull(Object object) throws IOException {
        return useMapper(object, MAPPER2);
    }
    public static String toJsonWithoutException(Object object)  {
        try {
            return useMapper(object, MAPPER2);
        } catch (IOException e) {
            log.error("", e);
        }
        return "";
    }

    private static String useMapper(Object object, ObjectMapper mapper) throws IOException {
        StringWriter writer = new StringWriter();
        JsonGenerator generator = JSON_FACTORY.createGenerator(writer);
        generator.setCodec(mapper);
        generator.writeObject(object);
        generator.close();
        writer.close();
        return writer.toString();
    }

    private static final JsonFactory JSON_FACTORY = new JsonFactory();
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final ObjectMapper MAPPER2 = new ObjectMapper();

    static {
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        MAPPER2.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MAPPER.setTimeZone(TimeZone.getDefault());
        MAPPER2.setTimeZone(TimeZone.getDefault());
    }

}
