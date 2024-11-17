package com.meli.couponmanager.service;

import com.meli.couponmanager.dto.CouponRequest;
import com.meli.couponmanager.dto.CouponResponse;

import java.util.List;
import java.util.Map;

public interface CouponServiceV1 {
    List<String> calculate(Map<String, Float> items, Float amount);
}
