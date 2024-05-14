package com.nm.javaweb3j.entity;

import java.math.BigInteger;

public class RequestBodies {

    public static class TransferTokensRequest {
        private String toAddr;
        private BigInteger tokens;

        public BigInteger getTokens() {
            return tokens;
        }

        public void setTokens(BigInteger tokens) {
            this.tokens = tokens;
        }

        public String getToAddr() {
            return toAddr;
        }

        public void setToAddr(String toAddr) {
            this.toAddr = toAddr;
        }

    }

}
