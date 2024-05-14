package com.nm.javaweb3j.controller;

import com.nm.javaweb3j.entity.RequestBodies.*;
import com.nm.javaweb3j.entity.Response;
import com.nm.javaweb3j.entity.Response.SUCCESS_STATUS;
import com.nm.javaweb3j.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("/contract")
public class ERC20ContractController {

    @Autowired
    private ContractService contractService;

    @GetMapping("/get-details")
    public ResponseEntity<Response> getContractDetails() {
        try {
            String contractName = contractService.getContractName();
            String contractSymbol = contractService.getContractSymbol();
            BigInteger contractTotalSupply = contractService.getTotalSupply();

            Response resp = new Response(SUCCESS_STATUS.TRUE,
                    "Contract information" +
                            " : Address : " + contractService.getContractAddress() +
                            " , Name : " + contractName +
                            " , Symbol : " + contractSymbol +
                            " , TotalSupply : " + contractTotalSupply
            );
            return new ResponseEntity<Response>(resp, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error occurred in ERC20ContractController.getContractDetails : " + e);
            Response resp = new Response(SUCCESS_STATUS.FALSE, "Error occurred in ERC20ContractController.getContractDetails");
            return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-balance-of/{waddr}")
    public ResponseEntity<Response> balanceOfAddress(@PathVariable String waddr) throws Exception {
        try {
            BigInteger balance = contractService.getBalanceOf(waddr);

            Response resp = new Response(SUCCESS_STATUS.TRUE, "Address : " + waddr + " - has the balance of : " + balance);
            return new ResponseEntity<Response>(resp, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error occurred in ERC20ContractController.balanceOfAddress : " + e);
            Response resp = new Response(SUCCESS_STATUS.FALSE, "Error occurred in ERC20ContractController.balanceOfAddress.");
            return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/transfer-tokens")
    public ResponseEntity<Response> transferTokens(@RequestBody TransferTokensRequest transferTokensRequest) throws Exception {
        try {
            String txHash = contractService.transferTokens(transferTokensRequest.getToAddr(), transferTokensRequest.getTokens());
            Response resp = new Response(SUCCESS_STATUS.TRUE, "Transfer success. TxHash : " + txHash);
            return new ResponseEntity<Response>(resp, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error occurred in ERC20ContractController.transferTokens : " + e);
            Response resp = new Response(SUCCESS_STATUS.FALSE, "Error occurred in ERC20ContractController.transferTokens.");
            return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
