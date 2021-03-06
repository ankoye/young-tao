package com.youngtao.core.result;

import java.io.Serializable;

/**
 * api request request
 *
 * @author ankoye@qq.com
 */
public class RpcResult<T> extends Result<T> implements Serializable {
    private static final long serialVersionUID = 4268670999252934435L;

    private static final int SUCCESS_CODE = RpcResCode.SUCCESS.code();

    private Integer code;

    private String message;

    private T data;

    public RpcResult() {
    }

    public RpcResult(RpcResCode resultCode) {
        this.setResultCode(resultCode);
    }

    public RpcResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public RpcResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> RpcResult<T> success() {
        return new RpcResult<>(RpcResCode.SUCCESS);
    }

    public static <T> RpcResult<T> success(T data) {
        RpcResult<T> result = new RpcResult<>(RpcResCode.SUCCESS);
        result.setData(data);
        return result;
    }

    public static <T> RpcResult<T> error() {
        return new RpcResult<>(RpcResCode.ERROR);
    }

    public static <T> RpcResult<T> error(T data) {
        RpcResult<T> result = new RpcResult<>(RpcResCode.ERROR);
        result.setData(data);
        return result;
    }

    public static <T> RpcResult<T> error(RpcResCode resultCode) {
        return new RpcResult<>(resultCode);
    }

    public static <T> RpcResult<T> error(RpcResCode resultCode, T data) {
        RpcResult<T> result = new RpcResult<>(resultCode);
        result.setData(data);
        return result;
    }

    public static <T> RpcResult<T> error(String errorMsg, T data) {
        RpcResult<T> result = new RpcResult<>(-1, errorMsg);
        result.setData(data);
        return result;
    }

    @Override
    public boolean isSuccess() {
        return this.code == SUCCESS_CODE;
    }

    private void setResultCode(RpcResCode code) {
        this.code = code.code();
        this.message = code.message();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
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

    @Override
    public String toString() {
        return "Result = { code: " + code +
                ", message: " + message +
                ", data: " + data + " }";
    }
}
