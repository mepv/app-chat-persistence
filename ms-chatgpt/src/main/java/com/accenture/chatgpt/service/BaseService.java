package com.accenture.chatgpt.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BaseService {

    @Autowired
    ObjectMapper objectMapper;

    /**
     * Return a map from token string
     * @param token
     * @return
     */
    public Map<String, String> getTokeMap(String token) {
        try {
            token = token.replace("Bearer ", "");
            String[] chunks = token.split("\\.");
            Base64.Decoder decoder = Base64.getUrlDecoder();
            String payload = new String(decoder.decode(chunks[1]));
            return this.objectMapper.readValue(payload, Map.class);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Return name user from string token
     * @param token
     * @return
     */
    public String getNameUserFromToken(String token) {
        Map<String, String> mapToken= this.getTokeMap(token);
        if (Objects.isNull(mapToken)){
            return "S/N";
        }
        return mapToken.get("sub");
    }
}
