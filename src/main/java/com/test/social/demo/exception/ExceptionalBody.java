package com.test.social.demo.exception;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class ExceptionalBody {

    @JsonValue
    private Map<String, Map<String, String>> extras = new LinkedHashMap<>();
    private Map<String, String> resultMap = new LinkedHashMap<>();

    private ExceptionalBody(String statusCode, String message) {
        extras.put("result", resultMap);
        resultMap.put("status_code", statusCode);
        resultMap.put("message", message);
    }

    public static ExceptionBodyBuilder build() {
        return new ExceptionBodyBuilder();
    }

    public static class ExceptionBodyBuilder {
        private String message;
        private String statusCode;

        public ExceptionBodyBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ExceptionBodyBuilder statusCode(String statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public ExceptionalBody create() {
            return new ExceptionalBody(statusCode, message);
        }
    }
}
