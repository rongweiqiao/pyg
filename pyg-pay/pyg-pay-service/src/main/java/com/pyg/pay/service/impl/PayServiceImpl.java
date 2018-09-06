package com.pyg.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.wxpay.sdk.WXPayUtil;
import com.pyg.mapper.TbOrderMapper;
import com.pyg.pay.service.PayService;
import com.pyg.pojo.TbOrder;
import com.pyg.pojo.TbOrderExample;
import com.pyg.utils.HttpClient;
import com.pyg.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PayServiceImpl implements PayService {
    //商户ID
    @Value("${appid}")
    private String appid;
    //商户号
    @Value("${partner}")
    private String partner;

    @Value("${partnerkey}")
    private String partnerkey;

    @Value("${payUrl}")
    private String payUrl;

    @Value("${notifyurl}")
    private String notifyurl;

    @Autowired
    private IdWorker idWorker;


    @Autowired
    private TbOrderMapper orderMapper;
    @Override
    public Map createNative(String userId) {
        try {
            TbOrderExample example = new TbOrderExample();
            TbOrderExample.Criteria criteria = example.createCriteria();
            criteria.andUserIdEqualTo(userId);
            List<TbOrder> orderList = orderMapper.selectByExample(example);
            double totalFee=0;
            for (TbOrder tbOrder : orderList) {
                totalFee+=tbOrder.getPayment().doubleValue();
            }
            long orderId = idWorker.nextId();
            Map<String,String> map = new HashMap<>();
            map.put("appid",appid);
            map.put("mch_id",partner);
            map.put("nonce_str", WXPayUtil.generateNonceStr());
            map.put("body","品优购");
            map.put("out_trade_no",orderId+"");
            //支付金额
            map.put("total_fee","1");
            map.put("spbill_create_ip","127.0.0.1");
            map.put("notify_url",notifyurl);
            map.put("trade_type","NATIVE");

            String xmParam = WXPayUtil.generateSignedXml(map, partnerkey);
            HttpClient httpClient = new HttpClient(payUrl);
            httpClient.setHttps(true);
            httpClient.setXmlParam(xmParam);
            httpClient.post();
            String content = httpClient.getContent();
            Map<String, String> map1 = WXPayUtil.xmlToMap(content);
            map1.put("totalFee",totalFee*100+"");
            map1.put("orderNum",orderId+"");
            return map1;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map queryOrderStatus(String orderNum) {
        try {
            Map<String,String> map = new HashMap<>();
            map.put("appid",appid);
            map.put("mch_id",partner);
            map.put("out_trade_no",orderNum);
            map.put("nonce_str",WXPayUtil.generateNonceStr());
            String xml = WXPayUtil.generateSignedXml(map, partnerkey);
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            httpClient.setXmlParam(xml);
            httpClient.setHttps(true);
            httpClient.post();
            String content = httpClient.getContent();
            Map<String, String> map1 = WXPayUtil.xmlToMap(content);
            return map1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
