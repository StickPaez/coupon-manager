package com.meli.couponmanager.controller;

import com.meli.couponmanager.dto.CouponRequestV2;
import com.meli.couponmanager.dto.CouponResponse;
import com.meli.couponmanager.service.v2.CouponServiceV2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2")
@Slf4j

public class CouponServiceV2Controller {

    @Autowired
    private CouponServiceV2 couponServiceV2;

    @PostMapping("/coupon")
    public ResponseEntity<CouponResponse> itemsToPurchaseAndTotal(@RequestBody CouponRequestV2 couponRequest) {
        log.info("Executing service itemsToPurchaseAndTotal");
        CouponResponse couponResponse = couponServiceV2.itemsToPurchaseAndTotal(couponRequest);
        log.info("Service itemsToPurchaseAndTotal completed");
        return new ResponseEntity<>(couponResponse, !couponResponse.getItemIds().isEmpty() ?
                HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}