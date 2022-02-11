package com.xxxx.server.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author ggz on 2022/2/11
 * 日期转换类
 */
@Component
public class DataConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String source) {
        try {
            return LocalDate.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println("转换错误！");
        }
        return null;
    }
}