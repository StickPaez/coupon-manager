package com.meli.couponmanager.dto.integration.mercadolibre;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ItemResponse {
    private String id;
    private String siteId;
    private String title;

    @JsonProperty("seller_id")
    private Long sellerId;

    @JsonProperty("category_id")
    private String categoryId;

    @JsonProperty("official_store_id")
    private Integer officialStoreId;

    private Float price;

    @JsonProperty("base_price")
    private Integer basePrice;

    @JsonProperty("original_price")
    private Integer originalPrice;

    @JsonProperty("currency_id")
    private String currencyId;

    @JsonProperty("initial_quantity")
    private Integer initialQuantity;
}