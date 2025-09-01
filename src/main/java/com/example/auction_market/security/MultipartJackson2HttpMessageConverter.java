package com.example.auction_market.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
public class MultipartJackson2HttpMessageConverter extends AbstractJackson2HttpMessageConverter {
    public MultipartJackson2HttpMessageConverter(ObjectMapper objectMapper) {
        // octet-stream으로 온 바디를 Jackson으로 읽을 수 있게 등록
        super(objectMapper, MediaType.APPLICATION_OCTET_STREAM);
    }

    @Override public boolean canWrite(Class<?> clazz, MediaType mediaType) { return false; }
    @Override public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) { return false; }
    @Override protected boolean canWrite(MediaType mediaType) { return false; }
}
