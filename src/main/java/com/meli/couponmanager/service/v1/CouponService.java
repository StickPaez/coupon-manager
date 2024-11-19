package com.meli.couponmanager.service.v1;

import java.util.List;
import java.util.Map;

public interface CouponService {
    List<String> calculate(Map<String, Float> items, Float amount);
}
