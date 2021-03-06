package com.youngtao.uac.controller.feign;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.youngtao.core.context.AuthInfo;
import com.youngtao.core.result.RpcResult;
import com.youngtao.core.util.BeanUtils;
import com.youngtao.uac.api.service.UserInfoFeign;
import com.youngtao.uac.mapper.MerchantInfoMapper;
import com.youngtao.uac.mapper.UserInfoMapper;
import com.youngtao.uac.model.domain.MerchantInfoDO;
import com.youngtao.uac.model.domain.UserInfoDO;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ankoye@qq.com
 * @date 2021/04/01
 */
@RestController
public class UserInfoFeignClient implements UserInfoFeign {

    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private MerchantInfoMapper merchantInfoMapper;

    @Override
    public RpcResult<AuthInfo> getById(String userId) {
        // TODO 修改成 AuthInfoFeign

        AuthInfo authInfo = null;
        if (userId.startsWith("user")) {
            UserInfoDO userInfoDO = userInfoMapper.selectOne(new QueryWrapper<UserInfoDO>()
                    .eq("user_id", userId)
            );
            authInfo = BeanUtils.copy(userInfoDO, AuthInfo.class);
        } else {
            MerchantInfoDO userInfo = merchantInfoMapper.selectOne(new QueryWrapper<MerchantInfoDO>()
                    .eq("merchant_id", userId)
            );
            authInfo = BeanUtils.copy(userInfo, AuthInfo.class);
        }
        return RpcResult.success(authInfo);
    }
}
