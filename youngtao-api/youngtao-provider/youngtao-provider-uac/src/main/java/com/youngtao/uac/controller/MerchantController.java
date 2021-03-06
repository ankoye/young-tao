package com.youngtao.uac.controller;

import com.youngtao.uac.model.req.RegisterReq;
import com.youngtao.uac.model.req.UpdateMerchantInfoReq;
import com.youngtao.uac.model.req.UpdatePasswordReq;
import com.youngtao.uac.service.MerchantService;
import com.youngtao.web.support.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ankoye@qq.com
 * @date 2021/04/17
 */
@ResponseWrapper
@RestController
@RequestMapping("/merchant")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @PostMapping("/register")
    public void register(@RequestBody RegisterReq request) {
        merchantService.register(request);
    }

    @PostMapping("/update")
    public void update(@RequestBody UpdateMerchantInfoReq request) {
        merchantService.updateInfo(request);
    }

    @PostMapping("/updatePassword")
    public void updatePassword(@RequestBody UpdatePasswordReq request) {
        merchantService.updatePassword(request);
    }

}
