package com.meli.couponmanager.integration.feign;


import com.meli.couponmanager.dto.integration.mercadolibre.ItemResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "mercado-libre-service", url = "${integration.services.mercado-libre.url}")
public interface MercadoLibreConsumer {
    @GetMapping(path = "/items/{itemId}")
    ItemResponse items(@PathVariable String itemId);
}