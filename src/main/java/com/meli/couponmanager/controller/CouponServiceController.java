package com.meli.couponmanager.controller;

import com.meli.couponmanager.dto.CouponRequest;
import com.meli.couponmanager.service.v1.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/v1")
@Slf4j

public class CouponServiceController {

    @Autowired
    private CouponService couponService;

    @Tag(name = "Coupon Version 1", description = "Operations related to calculating the maximum quantity of items")
    @Operation(
            summary = "Get maximum number of ItemId",
            description = "Gets the highest number of itemId to purchase without exceeding the amount supplied")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content())
    })

    @GetMapping("/coupon")
    public ResponseEntity<List<String>> itemsToPurchase(@RequestBody CouponRequest couponRequest) {
        try {
            log.info("Executing service getItemsToPurchase");
            List<String> itemIdList = couponService.calculate(couponRequest.getItemIds(), couponRequest.getAmount());
            return new ResponseEntity<>(itemIdList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error calculating coupon purchase to buy. Error -> {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}