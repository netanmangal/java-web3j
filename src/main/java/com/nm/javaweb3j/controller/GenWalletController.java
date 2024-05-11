package com.nm.javaweb3j.controller;

import com.nm.javaweb3j.config.Web3JBuild;
import com.nm.javaweb3j.entity.Response;
import com.nm.javaweb3j.entity.Response.SUCCESS_STATUS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthGetBalance;

import org.springframework.http.HttpStatus;
import org.web3j.protocol.core.methods.response.EthGetCode;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/wallet")
public class GenWalletController {

    @Autowired
    private Web3JBuild web3JBuild;

    @GetMapping("/check-valid-wallet-address/{waddr}")
    public ResponseEntity<Response> checkValidWalletAddress(@PathVariable String waddr) throws Exception {
        try {
            Web3j web3j = web3JBuild.getWeb3j();
            EthGetCode ethGetCode = web3j
                    .ethGetCode(waddr, DefaultBlockParameter.valueOf("latest"))
                    .sendAsync()
                    .get(20, TimeUnit.SECONDS);
            String code = ethGetCode.getCode();

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
            Web3j web3j = web3JBuild.getWeb3j();
            EthGetBalance ethGetbalance = web3j
                    .ethGetBalance(waddr, DefaultBlockParameter.valueOf("latest"))
                    .sendAsync()
                    .get(20, TimeUnit.SECONDS);
            BigInteger balance = ethGetbalance.getBalance();
            BigDecimal scaled_balance = new BigDecimal(balance).divide(BigDecimal.valueOf(Math.pow(10, 18)));

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
