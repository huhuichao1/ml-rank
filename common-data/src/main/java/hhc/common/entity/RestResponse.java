package hhc.common.entity;



import java.io.Serializable;


public class RestResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long              time;
    private int               status           = ExceptionEnum.SUCCESS.getCode();
    private String            message          = ExceptionEnum.SUCCESS.getDesc();
    private T                 data;

    public RestResponse() {}

    public RestResponse(T data) {
        super();
        this.data = data;
    }
    public RestResponse(T data, Long time) {
        super();
        this.data = data;
        this.time = time;
    }

    public RestResponse(int status, String message, T data) {
        super();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
