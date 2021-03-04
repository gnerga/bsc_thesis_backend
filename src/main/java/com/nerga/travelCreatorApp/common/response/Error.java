package com.nerga.travelCreatorApp.common.response;

import io.vavr.collection.Seq;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@Getter
public class Error extends Response<String> {

    private final String code;


    public Error(HttpStatus status, String code) {
        super(status);
        this.code = code;
    }

    public static Error badRequest(String code) {
        return new Error(HttpStatus.BAD_REQUEST, code);
    }
    public static Error notFound(String code) {return new Error(HttpStatus.NOT_FOUND, code);}
    public static Error Unauthorized(String code){return new Error(HttpStatus.UNAUTHORIZED, code);}

    public static Error concatCodes(Seq<Error> errors) {
        var errorCodes = errors.map(e -> e.code).reduceLeft((a,b) -> a.concat(System.lineSeparator()+b));
        return new Error(errors.head().status, errorCodes);
    }

    @Override
    public ResponseEntity<String> toResponseEntity() {
        return new ResponseEntity<>(code, status);
    }
}
