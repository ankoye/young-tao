package com.youngtao.uac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.youngtao.core.context.AuthType;
import com.youngtao.core.exception.CastException;
import com.youngtao.core.util.TokenUtils;
import com.youngtao.uac.mapper.MerchantInfoMapper;
import com.youngtao.uac.mapper.UserInfoMapper;
import com.youngtao.uac.model.data.AuthToken;
import com.youngtao.uac.model.domain.MerchantInfo;
import com.youngtao.uac.model.domain.UserInfo;
import com.youngtao.uac.model.request.LoginRequest;
import com.youngtao.uac.service.AuthService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ankoye@qq.com
 * @date 2021/03/30
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private MerchantInfoMapper merchantInfoMapper;

    @Override
    public AuthToken login(LoginRequest request) {
        if (request.getType() == AuthType.USER) {
            UserInfo user = userInfoMapper.login(request.getAccount(), request.getPassword());
            if (user == null) {
                CastException.cast("登录失败");
            }
            String token = TokenUtils.generateToken(user.getUserId());
            AuthToken authToken = new AuthToken();
            authToken.setAccessToken(token);
            return authToken;
        } else if (request.getType() == AuthType.MERCHANT) {
            MerchantInfo merchant = merchantInfoMapper.login(request.getAccount(), request.getPassword());
            if (merchant == null) {
                CastException.cast("登录失败");
            }
            String token = TokenUtils.generateToken(merchant.getMerchantId());
            AuthToken authToken = new AuthToken();
            authToken.setAccessToken(token);
            return authToken;
        } else {
            CastException.cast("登录类型错误");
            return null;
        }
    }

    @Override
    public UserInfo getById(String userId) {
        return userInfoMapper.selectOne(new QueryWrapper<UserInfo>()
                .eq("user_id", userId)
        );
    }
}
