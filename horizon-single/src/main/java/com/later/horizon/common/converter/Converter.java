package com.later.horizon.common.converter;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <a href="https://blog.csdn.net/weixin_43477531/article/details/114422604"/>
 */
public class Converter implements IConverter {

    private static MapperFacade Mapper_Facade;

    public Converter() {
        MapperFactory mapperFactory = getMapperFactory();
        // 注册数据类型转换
        ConverterFactory converterFactory = mapperFactory.getConverterFactory();
        converterFactory.registerConverter(new StringToStringArrayConverter());
        converterFactory.registerConverter(new MapToJsonStringArrayConverter());
        // 默认绑定全局转换
        Converter.Mapper_Facade = mapperFactory.getMapperFacade();
    }

    @Override
    public MapperFactory getMapperFactory() {
        return new DefaultMapperFactory.Builder().build();
    }

    /**
     * 简单的复制出新类型对象.
     * <p>
     * 通过source.getClass() 获得源Class
     * @param <S> 源对象类型
     * @param <D> 目标对象类型
     * @param source 源对象
     * @param destinationClass 目标类型
     * @return 目标对象
     */
    @Override
    public <S, D> D convert(S source, Class<D> destinationClass) {
        return Converter.Mapper_Facade.map(source, destinationClass);
    }

    @Override
    public <S, D> List<D> convert(Iterable<S> sources, Class<D> destinationClass) {
        return Converter.Mapper_Facade.mapAsList(sources, destinationClass);
    }

    static class StringToStringArrayConverter extends BidirectionalConverter<String[], String> {

        @Override
        public String convertTo(String[] strings, Type<String> destinationType, MappingContext mappingContext) {
            return String.join(",", strings);
        }

        @Override
        public String[] convertFrom(String source, Type<String[]> destinationType, MappingContext mappingContext) {
            return source.split(",");
        }
    }

    static class MapToJsonStringArrayConverter extends BidirectionalConverter<Map<String, Object>, String> {


        @Override
        public String convertTo(Map<String, Object> source, Type<String> destinationType, MappingContext mappingContext) {
            return JSON.toJSONString(source);
        }

        @Override
        public Map<String, Object> convertFrom(String source, Type<Map<String, Object>> destinationType, MappingContext mappingContext) {
            return new LinkedHashMap<>(JSONObject.parseObject(source));
        }
    }
}
