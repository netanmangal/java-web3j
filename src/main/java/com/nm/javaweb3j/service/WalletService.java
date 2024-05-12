package com.nm.javaweb3j.service;

import com.nm.javaweb3j.config.Web3JBuild;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetCode;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
public class WalletService {

    @Autowired
    private Web3JBuild web3JBuild;

    public String checkValidAddress(String waddr) throws ExecutionException, InterruptedException, TimeoutException {
        if (waddr == null) {
            throw new Error("Wallet address not provided.");
        }

        Web3j web3j = web3JBuild.getWeb3j();
        EthGetCode ethGetCode = web3j
                .ethGetCode(waddr, DefaultBlockParameter.valueOf("latest"))
                .sendAsync()
                .get(20, TimeUnit.SECONDS);
        return ethGetCode.getCode();
    }

    public BigDecimal getBalance(String waddr) throws ExecutionException, InterruptedException, TimeoutException {
        if (waddr == null) {
            throw new Error("Wallet address not provided.");
        }

        Web3j web3j = web3JBuild.getWeb3j();
        EthGetBalance ethGetbalance = web3j
                .ethGetBalance(waddr, DefaultBlockParameter.valueOf("latest"))
                .sendAsync()
                .get(20, TimeUnit.SECONDS);
        BigInteger balance = ethGetbalance.getBalance();
        BigDecimal scaled_balance = new BigDecimal(balance).divide(BigDecimal.valueOf(Math.pow(10, 18)));

        return scaled_balance;
    }

}
