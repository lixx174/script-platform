package com.j.infra.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * @author jinx
 */
@Configuration
public class JacksonConfiguration {

    public final static DateTimeFormatter STANDER_DTF = ofPattern("yyyy-MM-dd HH:mm:ss");
    public final static DateTimeFormatter STANDER_DF = ofPattern("yyyy-MM-dd");

    @Bean
    public ObjectMapper objectMapper() {
        return JsonMapper.builder()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
                .build()
                .registerModule(
                        new JavaTimeModule()
                                .addSerializer(LocalDate.class, new LocalDateSerializer(STANDER_DF))
                                .addDeserializer(LocalDate.class, new LocalDateDeserializer(STANDER_DF))
                                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(STANDER_DTF))
                                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(STANDER_DTF))
                )
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }
}
