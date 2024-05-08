package com.example.gaenari.util;

public class AccessToken {

    private String accessToken;
    private static AccessToken instance;

    private AccessToken() {
    }

    public static AccessToken getInstance() {
        if (instance == null) {
            // synchronized block to remove overhead
            synchronized (AccessToken.class) {
                if (instance == null) {
                    instance = new AccessToken();
                }
            }
        }
        return instance;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
