///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.accenture.auth.config;
//
//import com.accenture.auth.model.CustomUser;
//import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.security.oauth2.provider.token.TokenEnhancer;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author rafa22
// */
//public class CustomTokenEnhancer implements TokenEnhancer {
//
//
//    /**
//     * Enhance token, with extra data
//     *
//     * @param accessToken
//     * @param authentication
//     * @return
//     */
//    @Override
//    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
//        CustomUser user = (CustomUser) authentication.getPrincipal();
//        Map<String, Object> additionalInfo = new HashMap<>();
//        additionalInfo.put("id_user", user.getId());
//        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(
//                additionalInfo);
//        return accessToken;
//    }
//
//}
