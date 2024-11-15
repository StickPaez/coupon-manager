package com.meli.couponmanager.controller;

import com.meli.couponmanager.dto.CouponRequest;
import com.meli.couponmanager.service.CalculateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("coupon-api/v1")
@Slf4j

public class CouponController {

    @Autowired
    private CalculateService calculateService;

    @GetMapping("/coupon-purchase-maximization")
    ResponseEntity<List<String>> calculate(@RequestBody CouponRequest couponRequest) {
        try {
            return new ResponseEntity<>(calculateService.calculate(couponRequest.getItems(), couponRequest.getAmount()), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error calculating coupon purchase to buy. Error -> {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}