package com.youngtao.uac.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.youngtao.core.context.AuthType;
import com.youngtao.core.exception.CastException;
import com.youngtao.uac.common.constant.RedisKey;
import com.youngtao.uac.mapper.MerchantInfoMapper;
import com.youngtao.uac.mapper.UserInfoMapper;
import com.youngtao.uac.model.domain.MerchantInfoDO;
import com.youngtao.uac.model.domain.UserInfoDO;
import com.youngtao.uac.model.req.EmailReq;
import com.youngtao.uac.service.impl.EmailService;
import com.youngtao.web.cache.RedisManager;
import com.youngtao.web.support.ResponseWrapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author ankoye@qq.com
 * @date 2021/04/02
 */
@ResponseWrapper
@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;
    @Autowired
    private RedisManager<String> redisManager;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private MerchantInfoMapper merchantInfoMapper;

    @PostMapping("/register")
    public void sendRegisterCode(@RequestBody EmailReq request) {
        String key = null;
        if (request.getType() == AuthType.USER) {
            key = RedisKey.REGISTER_USER_CODE.format(request.getToAddr());
            UserInfoDO userInfoDO = userInfoMapper.selectOne(new QueryWrapper<UserInfoDO>()
                    .eq("email", request.getToAddr())
            );
            if (userInfoDO != null) {
                CastException.cast("该邮箱已被注册");
            }
        } else if (request.getType() == AuthType.MERCHANT) {
            key = RedisKey.REGISTER_MER_CODE.format(request.getToAddr());
            MerchantInfoDO merchantInfoDO = merchantInfoMapper.selectOne(new QueryWrapper<MerchantInfoDO>()
                    .eq("email", request.getToAddr())
            );
            if (merchantInfoDO != null) {
                CastException.cast("该邮箱已被注册");
            }
        } else {
            CastException.cast("注册类型错误");
        }
        String code = RandomStringUtils.randomNumeric(6);
        redisManager.set(key, code, 5, TimeUnit.MINUTES);
        emailService.sendTextMail(request.getToAddr(), "youngtao注册码", code);
    }

    @PostMapping("/forgetPassword")
    public void sendForgetPasswordCode(@RequestBody EmailReq request) {
        String key = null;
        if (request.getType() == AuthType.USER) {
            key = RedisKey.FORGET_PWD_USER_CODE.format(request.getToAddr());
            UserInfoDO userInfoDO = userInfoMapper.selectOne(new QueryWrapper<UserInfoDO>()
                    .eq("email", request.getToAddr())
            );
            if (userInfoDO == null) {
                CastException.cast("该邮箱未注册");
            }
        } else if (request.getType() == AuthType.MERCHANT) {
            key = RedisKey.FORGET_PWD_MER_CODE.format(request.getToAddr());
            MerchantInfoDO merchantInfoDO = merchantInfoMapper.selectOne(new QueryWrapper<MerchantInfoDO>()
                    .eq("email", request.getToAddr())
            );
            if (merchantInfoDO == null) {
                CastException.cast("该邮箱未注册");
            }
        } else {
            CastException.cast("注册类型错误");
        }
        String code = RandomStringUtils.randomNumeric(6);
        redisManager.set(key, code, 5, TimeUnit.MINUTES);
        emailService.sendTextMail(request.getToAddr(), "youngtao注册码", code);
    }
}
