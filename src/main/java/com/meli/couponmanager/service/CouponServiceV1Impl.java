package com.meli.couponmanager.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class CouponServiceV1Impl implements CouponServiceV1 {

    @Override
    public List<String> calculate(Map<String, Float> items, Float amount) {
        return getItemsToPurchase(items, amount);
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
}
