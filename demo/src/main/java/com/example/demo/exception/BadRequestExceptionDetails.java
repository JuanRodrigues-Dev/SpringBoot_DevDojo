package com.example.demo.exception;


import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
public class BadRequestExceptionDetails extends ExceptionDetails{

}
