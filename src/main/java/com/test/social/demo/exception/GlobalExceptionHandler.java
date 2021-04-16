package com.test.social.demo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String REQUEST_CONTENTS_ERROR = "2";
    private static final String API_SERVER_ERROR = "3";

    /**
     * @RequestParam enum type 일치하지 않아 binding 못할 경우 발생
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    @ResponseBody
    public ResponseEntity<ExceptionalBody> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.error("{}", ex.getMessage(), ex);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ExceptionalBody.build()
                        .statusCode(REQUEST_CONTENTS_ERROR)
                        .message("전달한 인자의 형식이 올바르지 않습니다. -> " + ex.getMessage())
                        .create());
    }

    /**
     * @RequestBody 의 enum type 일치하지 않아 binding 못할 경우 발생
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("{}", ex.getMessage(), ex);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ExceptionalBody.build()
                        .statusCode(REQUEST_CONTENTS_ERROR)
                        .message("전달한 인자의 형식이 올바르지 않습니다. -> " + ex.getMessage())
                        .create());
    }

    /**
     * javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
     * HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
     * 주로 @RequestBody, @RequestPart 어노테이션에서 발생
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("{}", ex.getMessage(), ex);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ExceptionalBody.build()
                        .statusCode(REQUEST_CONTENTS_ERROR)
                        .message("전달한 인자의 값이 올바르지 않습니다. -> " + ex.getMessage())
                        .create());
    }

    /**
     * @RequestParam 에 required true로 선언된 필드가 없을시 발생
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("{}", ex.getMessage(), ex);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ExceptionalBody.build()
                        .statusCode(REQUEST_CONTENTS_ERROR)
                        .message("요청 인자가 누락되었습니다. -> " + ex.getMessage())
                        .create());
    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("{}", ex.getMessage(), ex);

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(ExceptionalBody.build()
                        .statusCode(REQUEST_CONTENTS_ERROR)
                        .message("지원하지 않은 HTTP Method입니다. -> " + ex.getMessage())
                        .create());
    }

    /**
     * 그 외 Exception 클래스를 상속받은 클래스 예외
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResponseEntity<ExceptionalBody> handleException(Exception ex) {
        log.error("{}", ex.getMessage(), ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionalBody.build()
                        .statusCode(API_SERVER_ERROR)
                        .message("에러가 발생하였습니다. -> " + ex.getMessage())
                        .create());
    }

    /**
     * 그 외 RuntimeException 클래스를 상속받은 클래스 예외
     */
    @ExceptionHandler({RuntimeException.class})
    @ResponseBody
    public ResponseEntity<ExceptionalBody> handleRuntimeException(RuntimeException ex) {
        log.error("{}", ex.getMessage(), ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionalBody.build()
                        .statusCode(API_SERVER_ERROR)
                        .message("에러가 발생하였습니다. -> " + ex.getMessage())
                        .create());
    }
}
