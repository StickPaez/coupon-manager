package com.meli.couponmanager;

import com.meli.couponmanager.dto.CouponRequest;
import com.meli.couponmanager.service.v1.CouponServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CouponManagerLevel1Test {

	@Test
	void contextLoads() {
	}

	@InjectMocks
	private CouponServiceImpl couponService;

	CouponRequest couponRequest;
	List<String> itemsResponse;

	@BeforeEach
	void setup() {
		couponRequest = new CouponRequest();
		couponRequest.setItemIds(new HashMap<>());
		couponRequest.setAmount(0f);
		itemsResponse = new ArrayList<>();
	}

	@Test
	public void testCalculateWithEnoughAmount() {
		couponRequest.getItemIds().put("MLA1", 1000.0f);
		couponRequest.getItemIds().put("MLA2", 3000.0f);
		couponRequest.getItemIds().put("MLA3", 6000.0f);
		couponRequest.getItemIds().put("MLA4", 7000.0f);
		couponRequest.getItemIds().put("MLA5", 4000.0f);

		couponRequest.setAmount(20000.0f);
		itemsResponse.add("MLA1");
		itemsResponse.add("MLA2");
		itemsResponse.add("MLA5");
		itemsResponse.add("MLA3");

		List<String> result = couponService.calculate(couponRequest.getItemIds(), couponRequest.getAmount());
		assertEquals(itemsResponse, result);
	}

	@Test
	public void testCalculateWithOutEnoughAmount() {
		couponRequest.setAmount(500.0f);
		List<String> result = couponService.calculate(couponRequest.getItemIds(), couponRequest.getAmount());
		assertEquals(itemsResponse, result);
	}

	@Test
	public void testCalculateWithOutItems() {
		couponRequest.setAmount(2000.0f);
		List<String> result = couponService.calculate(couponRequest.getItemIds(), couponRequest.getAmount());
		assertEquals(itemsResponse, result);
	}
}
