package com.meli.couponmanager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CouponRequestV2 {
    @JsonProperty("item_ids")
    private List<String> itemIds;
    private Float amount;
}
