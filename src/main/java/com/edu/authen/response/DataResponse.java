package com.edu.authen.response;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DataResponse {
    private  Object message ;
    private  HttpStatus status;
    private  ZonedDateTime timestamp;
}
