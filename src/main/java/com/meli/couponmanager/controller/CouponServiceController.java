package com.meli.couponmanager.controller;

import com.meli.couponmanager.dto.CouponRequest;
import com.meli.couponmanager.service.v1.CouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j

public class CouponServiceController {

    @Autowired
    private CouponService couponService;

    @GetMapping("/coupon")
    public ResponseEntity<List<String>> itemsToPurchase(@RequestBody CouponRequest couponRequest) {
        try {
            log.info("Executing service getItemsToPurchase");
            return new ResponseEntity<>(couponService.calculate(
                    couponRequest.getItemIds(), couponRequest.getAmount()), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error calculating coupon purchase to buy. Error -> {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}