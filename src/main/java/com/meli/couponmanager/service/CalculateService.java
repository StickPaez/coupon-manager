package com.meli.couponmanager.service;

import java.util.List;
import java.util.Map;

public interface CalculateService {
    List<String> calculate(Map<String, Float> items, Float amount);
}
