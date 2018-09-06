package com.pyg.pay.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.pay.service.PayService;
import com.pyg.utils.PygResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/pay")
public class PayController {

    @Reference(timeout = 1000000)
    private PayService payService;

    @RequestMapping("/createNative")
    public Map createNative(HttpServletRequest request){
        String userId = request.getRemoteUser();
        Map map =payService.createNative(userId);
        return map;
    }

    @RequestMapping("/queryOrderStatus")
    public PygResult queryOrderStatus(String orderNum){
        PygResult pygResult = null;
        int i = 0 ;
        try {
            while (true) {
                Map map = payService.queryOrderStatus(orderNum);
                if(map==null){
                    pygResult=new PygResult(false,"支付出错");
                    break;
                }
                if("SUCCESS".equals(map.get("trade_state"))){
                    pygResult = new PygResult(true,"支付成功");
                    break;
                }
                Thread.sleep(3000L);
                i++;
                if(i==10){
                    pygResult = new PygResult(false,"timeOut");
                    break;
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pygResult;

    }
}
