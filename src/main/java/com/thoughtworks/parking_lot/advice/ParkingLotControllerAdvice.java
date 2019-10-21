package com.thoughtworks.parking_lot.advice;

import com.thoughtworks.parking_lot.dto.MessageResponse;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class ParkingLotControllerAdvice {

    public static final String PARKING_LOT_IS_FULL = "Parking lot is full!";

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public MessageResponse notFoundException(NotFoundException ex){
        MessageResponse errorResponse = new MessageResponse();
        errorResponse.setCode("404");
        errorResponse.setMessage(ex.getMessage());
        return errorResponse;
    }

    @ExceptionHandler(value = HttpClientErrorException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MessageResponse badRequest(HttpClientErrorException ex){
        MessageResponse errorResponse = new MessageResponse();
        errorResponse.setCode("400");
        errorResponse.setMessage(PARKING_LOT_IS_FULL);
        return errorResponse;
    }
}