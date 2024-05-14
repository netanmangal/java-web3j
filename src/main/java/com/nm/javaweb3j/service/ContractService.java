package com.nm.javaweb3j.service;

import com.nm.javaweb3j.contracts.ERC20Key;
import com.nm.javaweb3j.entity.ERC20Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;

@Component
public class ContractService {

    @Autowired
    private ERC20Contract erc20Contract;

    public String getContractName() throws Exception {
        ERC20Key contract = erc20Contract.getContractInstance();
        return contract.name().send();
    }

    public String getContractSymbol() throws Exception {
        ERC20Key contract = erc20Contract.getContractInstance();
        return contract.symbol().send();
    }

    public BigInteger getTotalSupply() throws Exception {
        ERC20Key contract = erc20Contract.getContractInstance();
        BigInteger totalSupply = contract.totalSupply().send();
        return totalSupply;
//        return new BigDecimal(totalSupply).divide(BigDecimal.valueOf(Math.pow(10, 18)));
    }

    public String getContractAddress() throws Exception {
        ERC20Key contract = erc20Contract.getContractInstance();
        return contract.getContractAddress();
    }

    public BigInteger getBalanceOf(String waddr) throws Exception {
        ERC20Key contract = erc20Contract.getContractInstance();
        return contract.balanceOf(waddr).send();
    }

    public String transferTokens(String waddr, BigInteger tokens) throws Exception {
        ERC20Key contract = erc20Contract.getContractInstance();
        TransactionReceipt transactionReceipt = contract.transfer(waddr, tokens).send();

        return transactionReceipt.getTransactionHash();
    }

}
