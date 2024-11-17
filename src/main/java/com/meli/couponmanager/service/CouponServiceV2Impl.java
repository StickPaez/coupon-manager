package com.meli.couponmanager.service;

import com.meli.couponmanager.dto.CouponRequest;
import com.meli.couponmanager.dto.CouponResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class CouponServiceV2Impl implements CouponServiceV2 {

    @Override
    public CouponResponse calculate(CouponRequest couponRequest) {
        CouponResponse response = new CouponResponse();
        List<String> itemsToPurchase = getItemsToPurchase(couponRequest.getItemIds(), couponRequest.getAmount());
        Integer totalAmount = getTotalAmount(itemsToPurchase, couponRequest.getItemIds());
        response.setItemIds(itemsToPurchase);
        response.setTotal(totalAmount);

        return response;
    }

    private List<String> getItemsToPurchase(Map<String, Float> items, Float amount) {
        List<String> itemsToPurchase = new ArrayList<>();

        List<Map.Entry<String, Float>> sortedItemsEntry = new ArrayList<>(items.entrySet());
        log.info("Sorting items by price in ascending order");
        sortedItemsEntry.sort(Map.Entry.comparingByValue());
        log.info("Sorted items -> {}", sortedItemsEntry);
        AtomicReference<Float> itemsAmount = new AtomicReference<>(0f);

        log.info("Adding items to purchase");
        sortedItemsEntry.forEach(item -> {
            if (itemsAmount.get() + item.getValue() <= amount) {
                itemsAmount.updateAndGet(value -> value + item.getValue());
                itemsToPurchase.add(item.getKey());
                log.info("Item {} added", item.getKey());
            }
        });

        return itemsToPurchase;
    }

    private Integer getTotalAmount(List<String> itemsToPurchase, Map<String, Float> itemIds) {
        AtomicInteger totalAmount = new AtomicInteger(0);
        itemsToPurchase.forEach(itemId -> {
            if(itemIds.containsKey(itemId)) {
                totalAmount.updateAndGet(value -> value + itemIds.get(itemId).intValue());
            }
        });
        return totalAmount.get();
    }
}
