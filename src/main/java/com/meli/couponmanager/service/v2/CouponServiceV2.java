package com.meli.couponmanager.service.v2;

import com.meli.couponmanager.dto.CouponRequestV2;
import com.meli.couponmanager.dto.CouponResponse;

public interface CouponServiceV2 {
    CouponResponse itemsToPurchaseAndTotal(CouponRequestV2 couponRequest);
}