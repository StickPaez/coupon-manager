package com.meli.couponmanager.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class CalculateServiceImpl implements CalculateService {

    @Override
    public List<String> calculate(Map<String, Float> items, Float amount) {
        List<String> itemsToBuy = new ArrayList<>();

        List<Map.Entry<String, Float>> sortedItemsEntry = new ArrayList<>(items.entrySet());
        sortedItemsEntry.sort(Map.Entry.comparingByValue());
        AtomicReference<Float> sumItems = new AtomicReference<>(0f);

        sortedItemsEntry.forEach(item -> {
            if (sumItems.get() + item.getValue() <= amount) return; {
                sumItems.updateAndGet(value -> value + item.getValue());
                itemsToBuy.add(item.getKey());
            }
        });

        return itemsToBuy;
    }
}
