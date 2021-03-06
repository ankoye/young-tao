package com.youngtao.omc.model.req;

import lombok.Data;

import javax.validation.constraints.Min;

/**
 * @author ankoye@qq.com
 * @date 2021/04/06
 */
@Data
public class UpdateNumReq {
    private String id;

    @Min(value = 1, message = "num cannot be less than 1")
    private String num;
}
