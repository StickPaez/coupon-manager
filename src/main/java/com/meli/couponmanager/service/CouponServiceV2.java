package com.meli.couponmanager.service;

import com.meli.couponmanager.dto.CouponRequest;
import com.meli.couponmanager.dto.CouponResponse;

public interface CouponServiceV2 {
    CouponResponse calculate(CouponRequest couponRequest);
}