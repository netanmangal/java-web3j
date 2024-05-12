package com.nm.javaweb3j.controller;

import com.nm.javaweb3j.entity.Response;
import com.nm.javaweb3j.entity.Response.SUCCESS_STATUS;
import com.nm.javaweb3j.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Objects;

@RestController
@RequestMapping("/wallet")
public class GenWalletController {

    @Autowired
    private WalletService walletService;

    @GetMapping("/check-valid-wallet-address/{waddr}")
    public ResponseEntity<Response> checkValidWalletAddress(@PathVariable String waddr) throws Exception {
        try {
            String code = walletService.checkValidAddress(waddr);

            Response resp = new Response(SUCCESS_STATUS.TRUE, "");
            if (code != null && code.length() > 10) {
                resp.setMsg("Valid smart contract address : " + waddr);
            } else if (code != null && Objects.equals(code, "0x") || Objects.equals(code, "0x0")) {
                resp.setMsg("Valid EOA : " + waddr);
            } else {
                resp.setMsg("Not a valid address : " + waddr);
            }

            return new ResponseEntity<Response>(resp, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error occurred in WalletEndpointController.checkValidWalletAddress : " + e);
            Response resp = new Response(SUCCESS_STATUS.FALSE, "Error occurred in WalletEndpointController.checkValidWalletAddress.");
            return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fetch-balance/{waddr}")
    public ResponseEntity<Response> checkWalletBalance(@PathVariable String waddr) throws Exception {
        try {
            BigDecimal scaled_balance = walletService.getBalance(waddr);
            Response resp = new Response(SUCCESS_STATUS.TRUE, "Balance of address : " + waddr + " : is : " + scaled_balance);
            return new ResponseEntity<Response>(resp, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error occurred in WalletEndpointController.checkWalletBalance : " + e);
            Response resp = new Response(SUCCESS_STATUS.FALSE, "Error occurred in WalletEndpointController.checkWalletBalance : " + e.getMessage());
            return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/template")
    public ResponseEntity<Response> template() throws Exception {
        try {
            Response resp = new Response(SUCCESS_STATUS.TRUE, "Hi");
            return new ResponseEntity<Response>(resp, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("Error occurred in WalletEndpointController.checkValidWalletAddress : " + e);
            Response resp = new Response(SUCCESS_STATUS.FALSE, "Error occurred in WalletEndpointController.checkValidWalletAddress.");
            return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
