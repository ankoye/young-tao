package com.youngtao.core.result;

/**
 * @author ankoye@qq.com
 */
public enum RpcResCode {

    /* 成功状态码 */
    SUCCESS(0, "成功"),
    ERROR(1, "失败"),

    /* 参数错误：10001-19999 */
    PARAM_IS_INVALID(10001, "参数无效"),
    PARAM_IS_BLANK(10002, "参数为空"),
    PARAM_TYPE_BIND_ERROR(10003, "参数类型错误"),
    PARAM_NOT_COMPLETE(10004, "参数缺失"),


    /* 用户错误：20001-29999*/
    USER_NOT_LOGGED_IN(20001, "用户未登录"),
    TOKEN_INVALID(20002, "无效Token"),
    USER_LOGIN_ERROR(20003, "账号或密码错误"),
    LOGIN_AUTHENTICATION_ERROR(20004, "登录认证失败"),
    USER_ACCOUNT_FORBIDDEN(20005, "账号已被禁用"),
    USER_NOT_EXIST(20006, "用户不存在"),
    USER_HAS_EXISTED(20007, "用户已存在"),
    USER_REGISTER_ERROR(20008, "用户注册错误"),
    USER_UPDATE_ERROR(20009, "修改用户信息错误"),

    /* 业务错误：30001-39999 */
    SERVICE_ERROR(30001, "业务繁忙，请稍后重试"),

    /* 系统错误：40001-49999 */
    SYSTEM_INNER_ERROR(40001, "系统繁忙，请稍后重试"),

    /* 数据错误：50001-599999 */
    RESULE_DATA_NONE(50001, "数据未找到"),
    DATA_IS_WRONG(50002, "数据有误"),
    DATA_ALREADY_EXISTED(50003, "数据已存在"),

    /* 接口错误：60001-69999 */
    INTERFACE_INNER_INVOKE_ERROR(60001, "内部系统接口调用异常"),
    INTERFACE_OUTTER_INVOKE_ERROR(60002, "外部系统接口调用异常"),
    INTERFACE_FORBID_VISIT(60003, "该接口禁止访问"),
    INTERFACE_ADDRESS_INVALID(60004, "接口地址无效"),
    INTERFACE_REQUEST_TIMEOUT(60005, "接口请求超时"),
    INTERFACE_EXCEED_LOAD(60006, "接口负载过高"),
    INTERFACE_UNSUPPORTED_METHOD(60007, "请求类型不支持"),

    /* 权限错误：70001-79999 */
    PERMISSION_NO_ACCESS(70001, "无访问权限"),
    EMAIL_ERROR(70002, "请输入正确的邮箱"),

    /* 文件上传 */
    UPLOAD_ERROR(80001, "上传失败"),

    SESSION_TIME_OUT(90001, "Session超时");

    private Integer code;

    private String message;

    RpcResCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    public static String getMessage(String name) {
        for (RpcResCode item : RpcResCode.values()) {
            if (item.name().equals(name)) {
                return item.message;
            }
        }
        return name;
    }

    public static Integer getCode(String name) {
        for (RpcResCode item : RpcResCode.values()) {
            if (item.name().equals(name)) {
                return item.code;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name();
    }

}
