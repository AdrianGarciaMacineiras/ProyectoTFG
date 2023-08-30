package com.sngular.skilltree.contract;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Instance not found");
        log.error("Message nor readable", ex);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleInstanceNotFoundException(EntityNotFoundException ex, WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Instance not found");
        log.error("Entity Not Found ", ex);

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

   /* @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleInstanceNotFoundException(RuntimeException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Instance not found");
        log.error("Exception Runtime ", ex);

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }*/
}
