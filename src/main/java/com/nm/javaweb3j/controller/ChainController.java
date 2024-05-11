package com.nm.javaweb3j.controller;

import com.nm.javaweb3j.config.Web3JBuild;
import com.nm.javaweb3j.entity.Response;
import com.nm.javaweb3j.entity.Response.SUCCESS_STATUS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthChainId;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/chain")
public class ChainController {

    @Autowired
    private Web3JBuild web3JBuild;

    @GetMapping("/getId")
    public ResponseEntity<Response> getChainId() {
        try {
            Web3j web3j = web3JBuild.getWeb3j();
            EthChainId ethChainId = web3j.ethChainId()
                    .sendAsync()
                    .get(20, TimeUnit.SECONDS);
            BigInteger chainId = ethChainId.getChainId();

            Response resp = new Response(SUCCESS_STATUS.TRUE, chainId);
            return new ResponseEntity<Response>(resp, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error occurred in ChainController.getChainId : " +  e);
            Response resp = new Response(SUCCESS_STATUS.TRUE, "Error occurred in ChainController.getChainId");
            return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
