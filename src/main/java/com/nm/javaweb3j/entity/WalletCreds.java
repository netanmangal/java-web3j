package com.nm.javaweb3j.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;

@Component
public class WalletCreds {

    private Credentials credentials;
    private @Value("${web3.wallet.privateKey}") String privateKey;

    public Credentials getCredentials() {
        if (credentials == null) {
            System.out.println("Initializing wallet...");
            this.credentials = Credentials.create(privateKey);
            System.out.println("Wallet initialized : address : " + credentials.getAddress());
        }

        return this.credentials;
    }

}
