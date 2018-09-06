package com.pyg.pay.service;

import java.util.Map;

public interface PayService {
    Map createNative(String userId);

    Map queryOrderStatus(String orderNum);
}
