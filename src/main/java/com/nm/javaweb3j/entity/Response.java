package com.nm.javaweb3j.entity;

public class Response {

    public enum SUCCESS_STATUS {
        TRUE,
        FALSE
    }

    private SUCCESS_STATUS successStatus;
    private Object msg;


    public Response(SUCCESS_STATUS successStatus, Object msg) {
        this.successStatus = successStatus;
        this.msg = msg;
    }

    public SUCCESS_STATUS getSuccessStatus() {
        return successStatus;
    }

    public void setSuccessStatus(SUCCESS_STATUS successStatus) {
        this.successStatus = successStatus;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

}
