package com.digivisions.stc.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Getter
public class GenericException extends RuntimeException {

    private HttpStatus status;


    @Builder
    public GenericException(HttpStatus status, String errorMessage) {
        super(errorMessage);
        this.status = status;

    }

}
