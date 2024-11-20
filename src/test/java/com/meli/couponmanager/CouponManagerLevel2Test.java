package com.meli.couponmanager;

import com.meli.couponmanager.dto.CouponRequestV2;
import com.meli.couponmanager.dto.CouponResponse;
import com.meli.couponmanager.dto.integration.mercadolibre.ItemResponse;
import com.meli.couponmanager.integration.feign.MercadoLibreConsumer;
import com.meli.couponmanager.service.v2.CouponServiceV2Impl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class CouponManagerLevel2Test {

	@Test
	void contextLoads() {
	}

	@Mock
	MercadoLibreConsumer mercadoLibreConsumer;

	@InjectMocks
	private CouponServiceV2Impl couponServiceV2;


	CouponRequestV2 couponRequestV2;
	CouponResponse couponResponse;
	ItemResponse mockItemResponse;

	@BeforeEach
	void setup() {
		couponRequestV2 = new CouponRequestV2();
		couponRequestV2.setItemIds(new ArrayList<>());
		couponRequestV2.getItemIds().add("MLA1");
		couponRequestV2.getItemIds().add("MLA2");
		couponRequestV2.getItemIds().add("MLA3");
		couponRequestV2.getItemIds().add("MLA4");
		couponRequestV2.getItemIds().add("MLA5");
		couponRequestV2.setAmount(20000f);

		couponResponse = new CouponResponse();
		couponResponse.setItemIds(new ArrayList<>());
		couponResponse.setTotal(0F);

		mockItemResponse = new ItemResponse();
		mockItemResponse.setId("MLA3");
		mockItemResponse.setSiteId("MLA3");
		mockItemResponse.setTitle("Luces de colores");
		mockItemResponse.setSellerId(1234567L);
		mockItemResponse.setCategoryId("MLA1234567");
		mockItemResponse.setOfficialStoreId(2345);
		mockItemResponse.setPrice(20000F);
		mockItemResponse.setOriginalPrice(20000F);
		mockItemResponse.setBasePrice(20000F);
		mockItemResponse.setOriginalPrice(null);
		mockItemResponse.setCurrencyId("COP");
		mockItemResponse.setInitialQuantity(2691F);
	}

	@Test
	public void testCalculateWithEnoughAmount() {
		couponResponse.getItemIds().add("MLA3");
		couponResponse.setTotal(20000F);
		when(mercadoLibreConsumer.getItemData(anyString())).thenReturn(mockItemResponse);
		CouponResponse result = couponServiceV2.itemsToPurchaseAndTotal(couponRequestV2);
		assertEquals(couponResponse, result);
	}

	@Test
	public void testCalculateWithOutEnoughAmount() {
		couponRequestV2.setAmount(5000f);
		when(mercadoLibreConsumer.getItemData(anyString())).thenReturn(mockItemResponse);
		CouponResponse result = couponServiceV2.itemsToPurchaseAndTotal(couponRequestV2);
		assertEquals(couponResponse, result);
	}

	@Test
	public void testCalculateWithOutItems() {
		couponRequestV2.setAmount(2000.0f);
		CouponResponse result = couponServiceV2.itemsToPurchaseAndTotal(couponRequestV2);
		assertEquals(couponResponse, result);
	}
}
