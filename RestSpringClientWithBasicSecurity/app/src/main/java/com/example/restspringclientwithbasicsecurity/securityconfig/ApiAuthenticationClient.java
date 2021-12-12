package com.example.restspringclientwithbasicsecurity.securityconfig;

import android.app.Application;
import android.content.Context;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Collections;

public class ApiAuthenticationClient {

    private HttpHeaders requestHeaders;

    private String baseUrl;
    private String username;
    private String password;

    private static ApiAuthenticationClient ourInstance;

    public static synchronized ApiAuthenticationClient getInstance() {
        if (ourInstance==null) {
            ourInstance = new ApiAuthenticationClient();
        }
        return ourInstance;
    }


    public HttpHeaders getRequestHeaders(){
        HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);

        requestHeaders = new HttpHeaders();
        requestHeaders.setAuthorization(authHeader);
        requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return  requestHeaders;
    }
//    public ApiAuthenticationClient(String  baseUrl, String username, String password) {
//        setBaseUrl(baseUrl);
//        this.username = username;
//        this.password = password;
//
//        HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
//
//        requestHeaders = new HttpHeaders();
//        requestHeaders.setAuthorization(authHeader);
//        requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//    }

    public ApiAuthenticationClient setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        if (!baseUrl.substring(baseUrl.length() - 1).equals("/")) {
            this.baseUrl += "/";
        }
        return this;
    }



    public void setRequestHeaders(HttpHeaders requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
