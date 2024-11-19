package com.meli.couponmanager.service.v2;

import com.meli.couponmanager.dto.CouponRequestV2;
import com.meli.couponmanager.dto.CouponResponse;
import com.meli.couponmanager.dto.integration.mercadolibre.ItemResponse;
import com.meli.couponmanager.integration.feign.MercadoLibreConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class CouponServiceV2Impl implements CouponServiceV2 {

    @Autowired
    MercadoLibreConsumer mercadoLibreConsumer;

    @Override
    public CouponResponse itemsToPurchaseAndTotal(CouponRequestV2 couponRequest) {
        CouponResponse response = new CouponResponse();

        Map<String, Float> itemsWithPrice = searchPricesByItems(couponRequest);
        List<String> itemsToPurchase = getItemsToPurchase(itemsWithPrice, couponRequest.getAmount());
        Float totalAmount = getTotalAmount(itemsToPurchase, itemsWithPrice);

        response.setItemIds(itemsToPurchase);
        response.setTotal(totalAmount);

        return response;
    }

    private List<String> getItemsToPurchase(Map<String, Float> items, Float amount) {

        List<String> itemsToPurchase = new ArrayList<>();

        if (!items.isEmpty()) {
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
        }

        return itemsToPurchase;
    }

    private Float getTotalAmount(List<String> itemsToPurchase, Map<String, Float> itemIds) {
        AtomicReference<Float> totalAmount = new AtomicReference<>(0f);

        if (!itemsToPurchase.isEmpty()) {
            log.info("Calculating total amount");
            itemsToPurchase.parallelStream().forEach(itemId -> {
                if (itemIds.containsKey(itemId)) {
                    totalAmount.updateAndGet(value -> value + itemIds.get(itemId).intValue());
                }
            });
            log.info("The total amount is {}", totalAmount.get());
        }
        return totalAmount.get();
    }

    private Map<String, Float> searchPricesByItems(CouponRequestV2 couponRequest) {
        Map<String, Float> itemsWithPrice = new HashMap<>();
        log.info("Searching price for items");
            couponRequest.getItemIds().parallelStream().forEach(itemId -> {
                try {
                    ItemResponse itemResponse = mercadoLibreConsumer.getItemData(itemId);
                    itemsWithPrice.put(itemResponse.getId(), itemResponse.getPrice());
                }catch (Exception e) {
                    log.error("Error getting data from itemId {}. Error -> {}", itemId, e.getMessage());
                }
            });
        log.info("Searching price for items completed");
        return itemsWithPrice;
    }
}
