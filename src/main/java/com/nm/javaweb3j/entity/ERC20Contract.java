package com.nm.javaweb3j.entity;

import com.nm.javaweb3j.config.Web3JBuild;
import com.nm.javaweb3j.contracts.ERC20Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

@Component
public class ERC20Contract {

    private ERC20Key erc20KeyContract;
    @Value("${web3.erc20.contractAddress:null}") private String erc20Address;

    @Autowired
    private WalletCreds creds;

    @Autowired
    private Web3JBuild web3JBuild;

    public ERC20Key getContractInstance() throws Exception {
        try {
            Web3j web3j = web3JBuild.getWeb3j();
            Credentials credentials = creds.getCredentials();
            TransactionManager transactionManager = new RawTransactionManager(
                    web3j, credentials, 80085L
            );
            ContractGasProvider contractGasProvider = new DefaultGasProvider();
//            ContractGasProvider contractGasProvider = new StaticGasProvider(
//                    BigInteger.valueOf(20000000000L),
//                    BigInteger.valueOf(6721975L)
//            );

            if (erc20KeyContract == null) {
                if (erc20Address != null && !erc20Address.equals("null")) {
                    System.out.println("ERC20Contract.getContractInstance : contractAddress found in properties : " + erc20Address);

                    this.erc20KeyContract = ERC20Key
                            .load(erc20Address, web3j, transactionManager, contractGasProvider);

                    System.out.println("ERC20Contract.getContractInstance : contract loaded.");
                }
                else {
                    System.out.println("ERC20Contract.getContractInstance : Deploying the contract...");

                    erc20KeyContract = ERC20Key
                            .deploy(web3j, transactionManager, contractGasProvider, "NMToken", "NMT")
                            .sendAsync()
                            .get(20, TimeUnit.SECONDS);

                    System.out.println("Contract deployed at address : " + erc20KeyContract.getContractAddress());
                }
            }

            return erc20KeyContract;
        } catch (Exception e) {
            System.out.println("Error occurred in ERC20Contract.getContractInstance : " + e);
            throw new Error("Error occurred in ERC20Contract.getContractInstance.");
        }
    }

}
