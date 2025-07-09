package com.hungvt.courseservice.infrastructure.model.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseObject {

    private Object data;

    private String message;

    private HttpStatus status;

    public static <T> ResponseObject ofData(T data) {
        return new ResponseObject(data, null, HttpStatus.OK);
    }

    public static <T> ResponseObject ofData(T data, String message) {
        return new ResponseObject(data, message, HttpStatus.OK);
    }

    public static <T> ResponseObject ofData(T data, String message, HttpStatus status) {
        return new ResponseObject(data, message, status);
    }

}
