package com.meli.couponmanager.controller;

import com.meli.couponmanager.dto.CouponRequestV2;
import com.meli.couponmanager.dto.CouponResponse;
import com.meli.couponmanager.exceptions.ItemNotFoundException;
import com.meli.couponmanager.service.v2.CouponServiceV2;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Tag(name = "Coupon version 2", description = "Operations related to get the maximum quantity of items to purchase")
    @Operation(
            summary = "Get maximum number of ItemId",
            description = "Gets the highest number of itemId to purchase without exceeding the amount supplied. " +
                            "It also gets the total amount of the item Ids")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Insufficient amount to purchase any item", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content())
    })

    @PostMapping("/coupon")
    public ResponseEntity<CouponResponse> itemsToPurchaseAndTotal(@RequestBody CouponRequestV2 couponRequest) {
        try {
            log.info("Executing service itemsToPurchaseAndTotal");
            CouponResponse couponResponse = couponServiceV2.itemsToPurchaseAndTotal(couponRequest);
            log.info("Service itemsToPurchaseAndTotal completed");
            return new ResponseEntity<>(couponResponse, HttpStatus.OK);
        } catch (ItemNotFoundException notFoundException) {
            log.error(notFoundException.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error calculating coupon purchase to buy. Error -> {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}