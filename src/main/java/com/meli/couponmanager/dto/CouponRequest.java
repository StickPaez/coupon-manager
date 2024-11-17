package com.meli.couponmanager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class CouponRequest {
    @JsonProperty("item_ids")
    private Map<String, Float> itemIds;
    private Float amount;
}
