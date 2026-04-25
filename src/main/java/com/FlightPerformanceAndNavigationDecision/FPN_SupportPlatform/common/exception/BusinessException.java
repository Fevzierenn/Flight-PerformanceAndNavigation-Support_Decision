package com.FlightPerformanceAndNavigationDecision.FPN_SupportPlatform.common.exception;

public class BusinessException extends RuntimeException {
    
    public BusinessException(String message){
        super(message);
    }

    public BusinessException(String message, Throwable cause){
        super(message, cause);
    }
}
