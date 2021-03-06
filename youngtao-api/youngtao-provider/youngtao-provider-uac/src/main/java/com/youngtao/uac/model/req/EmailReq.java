package com.youngtao.uac.model.req;

import com.youngtao.core.context.AuthType;
import lombok.Data;

/**
 * @author ankoye@qq.com
 * @date 2021/04/02
 */
@Data
public class EmailReq {
    /**
     * 发送邮箱
     */
    private String toAddr;
    /**
     * 1: 用户，2：商家
     */
    private Integer type = AuthType.USER;
}
