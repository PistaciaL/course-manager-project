package org.lr.config;

import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Bean
    public HttpMessageConverter fastJsonMessageConverters() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();

        /*
            SerializerFeature.PrettyFormat 格式化输出
            WriteMapNullValue  输出 map 中值为 null 的字段
            WriteNullStringAsEmpty  字符类型字段如果为 null,输出为 ”“
            WriteNullNumberAsZero   数值字段如果为 null,输出为 0
            WriteNullListAsEmpty    List字段如果为 null,输出为[]
            WriteNullBooleanAsFalse Boolean字段如果为 null,输出为 false
            DisableCircularReferenceDetect  消除对同一对象循环引用的问题
         */
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteEnumUsingToString);

        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");

        fastConverter.setFastJsonConfig(fastJsonConfig);
        fastConverter.setDefaultCharset(StandardCharsets.UTF_8);

        List<MediaType> mediaTypeList = new ArrayList<>();
        // 解决中文乱码问题，相当于在 Controller上的 @RequestMapping 中加了个属性produces = "application/json"
        mediaTypeList.add(MediaType.APPLICATION_JSON);
        fastConverter.setSupportedMediaTypes(mediaTypeList);

        return fastConverter;

    }

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(fastJsonMessageConverters());
    }
}

