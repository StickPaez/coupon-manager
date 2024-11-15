package com.meli.couponmanager.dto;

import lombok.Data;

import java.util.Map;

@Data
public class CouponRequest {
    private Map<String, Float> items;
    private Float amount;
}
