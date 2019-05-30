package com.itheima.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDataConverter implements Converter<String,Date>{

        private String pattern;
        public void setPattern(String pattern) {
            this.pattern = pattern;
        }
        @Override
        public Date convert(String source) {
            try{
                if(StringUtils.isEmpty(pattern)){
                    pattern = "yyyy-MM-dd";
                }
                if(StringUtils.isEmpty(source)){
                    throw new NullPointerException("日期不能为空");
                }
                DateFormat format = new SimpleDateFormat(pattern);
                return format.parse(source);
            }catch (Exception e){
                throw new IllegalArgumentException("日期格式不对，请输入正确的日期格式。格式为年-月- 日");
            }
        }
    }

