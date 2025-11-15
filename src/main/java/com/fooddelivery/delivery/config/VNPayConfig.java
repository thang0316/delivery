package com.fooddelivery.delivery.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VNPayConfig {
    
    @Value("${vnpay.vnp-tmn-code}")
    private String vnpTmnCode;
    
    @Value("${vnpay.vnp-hash-secret}")
    private String vnpHashSecret;
    
    @Value("${vnpay.vnp-url}")
    private String vnpUrl;
    
    @Value("${vnpay.vnp-return-url}")
    private String vnpReturnUrl;

    public String getVnpTmnCode() {
        return vnpTmnCode;
    }

    public String getVnpHashSecret() {
        return vnpHashSecret;
    }

    public String getVnpUrl() {
        return vnpUrl;
    }

    public String getVnpReturnUrl() {
        return vnpReturnUrl;
    }
}
