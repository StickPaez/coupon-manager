package com.meli.couponmanager.service;

import com.meli.couponmanager.dto.CouponRequestV2;
import com.meli.couponmanager.dto.CouponResponse;

public interface CouponServiceV2 {
    CouponResponse calculate(CouponRequestV2 couponRequest);
}