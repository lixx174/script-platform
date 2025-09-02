package com.j.infra.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.j.application.model.Result;
import com.j.infra.configuration.JacksonConfiguration;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;

import java.io.PrintWriter;
import java.util.function.Supplier;

/**
 * @author Jinx
 */
public class ManualResponseSupport {

    private static final ObjectMapper om = new JacksonConfiguration().objectMapper();

    @SneakyThrows
    public static void doJsonResponse(HttpServletResponse response, Supplier<Result<?>> resultSupplier) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Result<?> result = resultSupplier.get();
        response.setStatus(result == null ? HttpStatus.INTERNAL_SERVER_ERROR.value() : result.getCode());

        PrintWriter writer = response.getWriter();
        writer.write(om.writeValueAsString(result));
        writer.flush();
        writer.close();
    }
}
