package com.meli.couponmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CouponManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CouponManagerApplication.class, args);
	}

}
