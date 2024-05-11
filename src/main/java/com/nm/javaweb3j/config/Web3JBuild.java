package com.nm.javaweb3j.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Component
public class Web3JBuild {

    private Web3j web3j;
    private @Value("${web3.client.rpcURL}") String rpcURL;

    public Web3j getWeb3j() {
        if (this.web3j == null) {
            System.out.println("Web3Build.getWeb3j is executing. RPCURL from application.properties is : " + rpcURL);
            this.web3j = Web3j.build(new HttpService(rpcURL));
        }
        return web3j;
    }

    public void setWeb3j(Web3j web3j) {
        this.web3j = web3j;
    }
}
