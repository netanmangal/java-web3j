package com.nm.javaweb3j.services;

import com.nm.javaweb3j.service.WalletService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WalletServiceTests {

    @Autowired
    private WalletService walletService;

    @Test
    public void testGetBalance() {
        try {
            BigDecimal bal = walletService.getBalance("0x86391F4f9AE79B11eB37B7a3F54f4725EF4e9441");
            assertNotEquals(String.valueOf(bal), String.valueOf(0));
        } catch (Exception e) {
            fail("Error occurred in calling ");
        }
    }

}
