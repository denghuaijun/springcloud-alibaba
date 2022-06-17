package com.denghuaijun.utils;

/**
 * DistributionLockException
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/6/17 11:43
 * @Description 自定义分布式异常
 */
public class DistributionLockException extends Exception {

    private int code;

    private String message;

    public DistributionLockException(String message) {
        super();
        this.message = message;
    }

    public DistributionLockException(int code, String message) {
        super();
        this.code = code;
        this.message=message;
    }

    public DistributionLockException(Throwable cause, int code, String message) {
        super(cause);
        this.code = code;
        this.message=message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
