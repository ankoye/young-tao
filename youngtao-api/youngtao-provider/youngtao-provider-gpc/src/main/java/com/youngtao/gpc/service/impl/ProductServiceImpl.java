package com.youngtao.gpc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.youngtao.core.context.AuthContext;
import com.youngtao.core.exception.CastException;
import com.youngtao.core.result.RpcResult;
import com.youngtao.core.util.RpcUtils;
import com.youngtao.gmc.api.model.dto.ProductDTO;
import com.youngtao.gmc.api.model.dto.SpuDTO;
import com.youngtao.gmc.api.service.ProductFeign;
import com.youngtao.gmc.api.service.SpuFeign;
import com.youngtao.gpc.common.constant.CacheKey;
import com.youngtao.gpc.common.constant.RedisKey;
import com.youngtao.gpc.common.util.DateUtils;
import com.youngtao.gpc.manager.ProductManager;
import com.youngtao.gpc.mapper.SkuMapper;
import com.youngtao.gpc.model.convert.ProductConvert;
import com.youngtao.gpc.model.convert.SkuConvert;
import com.youngtao.gpc.model.data.ProductData;
import com.youngtao.gpc.model.data.SkuData;
import com.youngtao.gpc.model.domain.SkuDO;
import com.youngtao.gpc.model.req.ConfirmOrderReq;
import com.youngtao.gpc.model.req.GetMerchantProductReq;
import com.youngtao.gpc.model.res.ConfirmOrderRes;
import com.youngtao.gpc.model.res.GetSeckillPageRes;
import com.youngtao.gpc.service.ProductService;
import com.youngtao.web.cache.DCacheManager;
import com.youngtao.web.cache.RedisManager;
import com.youngtao.web.page.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author ankoye@qq.com
 * @date 2020/12/20
 */
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private RedisManager<String> redisManager;
    @Autowired
    private DCacheManager<String> dCacheManager;
    @Autowired
    private ProductFeign productFeign;
    @Autowired
    private SpuFeign spuFeign;
    @Autowired
    private ProductManager productManager;
    @Autowired
    private ProductConvert productConvert;
    @Autowired
    private SkuConvert skuConvert;

    @Resource
    private SkuMapper skuMapper;

    @Override
    public GetSeckillPageRes getSeckillPage() {
        GetSeckillPageRes response = new GetSeckillPageRes();
        response.setCurrentTime(new Date());
        Set<SkuData> skuDataSet = listByTime(DateUtils.currentMenu(), 1, 10);
        response.setSkuList(skuDataSet);
        return response;
    }

    @Override
    public Set<SkuData> listByTime(String time, Integer page, Integer size) {
        long start = (page - 1) * size;
        long end = page * size;
        return redisManager.zrange(RedisKey.SKU_SET_KEY.format(time), start, end);
    }

    @Override
    public ProductData detail(String time, String skuId) {
        Integer count = redisManager.getNum(RedisKey.SKU_COUNT_KEY.format(time, skuId));
        if (count == null) {
            return null;
        }
        ProductData data = dCacheManager.get(CacheKey.PRODUCT_KEY.format(time, skuId), v -> {
            SkuData skuData = productManager.getSkuData(time, skuId);
            RpcResult<ProductDTO> productResult = productFeign.getBySpuId(skuData.getSkuId());
            RpcUtils.checkNotNull(productResult);
//            ProductData productData = productConvert.toProductData(productResult.getData());
//            productData.getSkuList().removeIf(sku -> sku.getSkuId().equals(skuId));
//            productData.getSkuList().add(skuConvert.toProductSku(skuData));
//            return productData;
            return null;
        }, true);
        for (ProductData.Sku sku : data.getSkuList()) {
            if (sku.getSkuId().equals(skuId)) {
                sku.setResidue(count);
            }
        }
        return data;
    }

    @Override
    public ConfirmOrderRes confirmOrder(ConfirmOrderReq request) {
        String menu = DateUtils.currentMenu();
        String skuId = request.getSkuId();
        // 判断是否还有库存
        Integer count = redisManager.getNum(RedisKey.SKU_COUNT_KEY.format(menu, skuId));
        if (count == null || count < 1) {
            CastException.cast("商品库存不足");
        }

        SpuDTO spuDTO = dCacheManager.get(CacheKey.SPU_KEY.format(menu, skuId), v -> {
            RpcResult<SpuDTO> spuDTOResult = spuFeign.getBySkuId(skuId);
            RpcUtils.checkNotNull(spuDTOResult);
            return spuDTOResult.getData();
        }, true);

        ConfirmOrderRes response = new ConfirmOrderRes();
        response.setMerchantId(spuDTO.getMerchantId());
        response.setShopName(spuDTO.getShopName());
        response.setPostage(BigDecimal.ZERO);
        // 获取sku
        SkuData skuData = redisManager.get(RedisKey.SKU_INFO_KEY.format(menu, skuId));
        ConfirmOrderRes.Sku sku = new ConfirmOrderRes.Sku();
        sku.setSkuId(skuId);
        sku.setSpu(spuDTO.getSpu());
        sku.setSku(skuData.getSku());
        sku.setPrice(skuData.getPrice());
        sku.setImage(skuData.getImage());
        sku.setServe(spuDTO.getServe());
        sku.setCount(1);
        response.setSkuList(Lists.newArrayList(sku));
        return response;
    }

    @Override
    public PageInfo<ProductData> getMerchantProduct(GetMerchantProductReq request) {
        // 获取数据
        PageHelper.startPage(request.getPage(), request.getSize());
        List<String> spuIds = skuMapper.getAllSpuIdByMerchant(AuthContext.get().getMerchantId());
        List<ProductData> productData = Lists.newArrayList();

        if (!CollectionUtils.isEmpty(spuIds)) {
            RpcResult<List<SpuDTO>> spuResult = spuFeign.listBySpuIds(spuIds);
            RpcUtils.checkNotNull(spuResult);

            List<SpuDTO> spuDTOList = spuResult.getData();
            List<SkuDO> skuList = skuMapper.listBySpuIds(spuIds);
            Map<String, List<SkuDO>> skuMap = Maps.newHashMap();
            for (SkuDO skuDO : skuList) {
                List<SkuDO> list = skuMap.getOrDefault(skuDO.getSpuId(), Lists.newArrayList());
                list.add(skuDO);
                skuMap.put(skuDO.getSpuId(), list);
            }
            // 整理数据
            for (SpuDTO spuDTO : spuDTOList) {
                ProductData data = productConvert.toProductData(spuDTO, skuMap.get(spuDTO.getSpuId()));
                productData.add(data);
            }
        }
        return PageUtils.convert(PageInfo.of(spuIds), productData);
    }
}
