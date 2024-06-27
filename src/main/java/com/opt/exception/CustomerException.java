package com.opt.exception;


public class CustomerException extends RuntimeException {
    private final ResultCode code;

    public CustomerException(ResultCode code) {
        super(code.toString());
        this.code = code;
    }

    public CustomerException(String message, ResultCode code) {
        super(message);
        this.code = code;
    }

    public CustomerException(String message, Throwable cause, ResultCode code) {
        super(message, cause);
        this.code = code;
    }

    public ResultCode getCode() {
        return code;
    }

}
