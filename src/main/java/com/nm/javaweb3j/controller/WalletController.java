package com.nm.javaweb3j.controller;

import com.nm.javaweb3j.entity.Response;
import com.nm.javaweb3j.entity.WalletCreds;
import com.nm.javaweb3j.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.Credentials;

import java.math.BigDecimal;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletCreds walletCreds;

    @Autowired
    private WalletService walletService;

    @GetMapping("/load-credentials")
    public ResponseEntity<Response> loadCredentials() {
        try {
            Credentials credentials = walletCreds.getCredentials();

            Response resp = new Response(Response.SUCCESS_STATUS.TRUE, credentials);
            return new ResponseEntity<Response>(resp, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error occurred in WalletController.loadCredentials : " + e);
            Response resp = new Response(Response.SUCCESS_STATUS.FALSE, "Error occurred in WalletController.loadCredentials.");
            return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fetch-balance")
    public ResponseEntity<Response> fetchBalance() {
        try {
            Credentials credentials = walletCreds.getCredentials();

            if (credentials == null || credentials.getAddress() == null) {
                Response resp = new Response(Response.SUCCESS_STATUS.FALSE, "Error occurred in WalletController.fetchBalance. - Credentials not loaded. Hit - http://localhost:{{port}}/wallet/load-credentials");
                return new ResponseEntity<Response>(resp, HttpStatus.BAD_REQUEST);
            }

            BigDecimal scaled_balance = walletService.getBalance(credentials.getAddress());
            Response resp = new Response(Response.SUCCESS_STATUS.TRUE, scaled_balance);
            return new ResponseEntity<Response>(resp, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error occurred in WalletController.fetchBalance : " + e);
            Response resp = new Response(Response.SUCCESS_STATUS.FALSE, "Error occurred in WalletController.fetchBalance.");
            return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
