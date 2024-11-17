package com.meli.couponmanager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CouponResponse {
    @JsonProperty("item_ids")
    private List<String> itemIds;
    private Integer total;
}
